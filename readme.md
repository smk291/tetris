# Java Tetris

##### Contents
1. [Description](#description)
1. [Controls](#controls)
1. [Tetris-related reading material](#tetris)
1. [Discussion of source code](#code)
1. [Key Concepts](#keyconcepts)
1. [Plans for the future](#plans)

#### Description <a name="description"></a> 

This is project is an implementation of Tetris. More speciically, it is a full implementation of the official [Tetris Guidline](https://tetris.fandom.com/wiki/Tetris_Guideline) (using, for instance, the [Super Rotation System or SRS](https://tetris.wiki/SRS), not the [Arika Rotation System or ARS](https://harddrop.com/wiki/ARS)). The basic goals of the project are nearly complete; apart from the scoring system, I've implemented nearly everything required for the game to be considered an implementation of "official" Tetris (though, obviously, this isn't an official release.). I use unit tests (JUnit, contained in `com.tetris.tests.unit`) and integration tests (`com.tetris.tests.integration`) to confirm that the game behaves as expected, correctly, and "officially."

The GUI is a rough work in progress, but the game is playable. The easiest way to play will be to open the tetris.jar file included in this repository. On Linux and Apple machines you'll need to change the file's permissions in order to make it executable.

The game should build successfully in Eclipse or IntelliJ. These IDEs also allow you to run the full suite of unit tests.

#### Controls<a name="controls"></a>
Rotation||
:---|---|
Clockwise|shift + right
Counterclockwise|shift + left

Movement:||
:---|---|
Left|left arrow|
Right|Right arrow|
Down oner row ("soft drop")| down arrow|
Up one row |up arrow*

*Disabled. Can be enabled in HandleInput.java

#### Tetris-related reading material<a name="tetris"></a>

See [this article](https://en.wikipedia.org/wiki/Tetris) for an introduction to the game and [this article](https://tetris.wiki/Tetris_Guideline) for the official Tetris guidelines.

#### Key concepts:<a name="keyconcepts"></a>

Important terms and concepts are **highlighted**.

* The play controls a [**tetromino**](https://tetris.fandom.com/wiki/Tetromino), also called a **block** or sometimes a **piece**, as it falls.
* A tetromino is a geometric shape consisting of four colored **squares** connected at their edges.
* There are seven different configurations of squares arranged this way, and thus there are seven different tetrominoes. Each is named after the letter in the alphabet that it most resembles: [i](https://tetris.fandom.com/wiki/I-Block), [j](https://tetris.fandom.com/wiki/J-Block), [l](http://tetris.fandom.com/wiki/L-Block), [o](https://tetris.fandom.com/wiki/O-Block), [s](https://tetris.fandom.com/wiki/S-Block), [t](https://tetris.fandom.com/wiki/T-Block), and [z](https://tetris.fandom.com/wiki/Z-Block). 
* The tetromino that the player currently controls is what I call the **active block**.
* The game takes place on the [**playfield**](https://tetris.fandom.com/wiki/Playfield).
* The playfield is a grid 10 **cells** wide and 20 cells high. Rows and cells are typically counted as they are in a Cartesian coordinate system, with x increasing from left to right and y increasing from bottom to top.
* The active block spawns on the 20th row. 
* Within the playfield the player can [**rotate**](https://tetris.fandom.com/wiki/SRS) or **move**(/**translate**/**shift**) the active block. 
* Rotation and movement occur in discrete units. Blocks cannot move less or more than one square's width left, right, or down (but not up), and can rotate only 90 degrees left or right.
* Lowering a block once (i.e. from its current row to the row below), is a [**soft drop**](https://harddrop.com/wiki/Drop). Lowering a block to the lowest possible position instantly is a [**hard drop**](https://harddrop.com/wiki/Drop).
* The player cannot directly raise a block from its current row to the row above (though this is possible indirectly).
* When the active block moves to the lowest row or its base comes into contact with a square connected (by other squares) to the lowest row, a timer starts. This is the [**lock delay**](https://tetris.fandom.com/wiki/Lock_delay), the time within which the player can continue to move or rotate the block before it **settles** in place and becomes an immovable part of the playfield.
* There are separate lock delays for rotation and movement. Rotating a block resets the rotational and movement lock delays. Moving the block does *not* reset the movement lock delay.
* When the base of the active block is on top of a settled square or is on the bottom row, and the lock delay hasn't yet elapsed, a soft drop will immediately settle the block.
* There is no lock delay after a hard drop. The block settles instantly.
* Once a block settles, a new active block spawns at the top of the playfield.
* The next active block is drawn from a randomized **queue**, also called the **bag**. There is an official [Random Generator](https://harddrop.com/wiki/Bag) algorithm. In this algorithm, the order of the seven blocks is shuffled randomly. When the queue drops to a certain length, another set of randomly shuffled blocks is appended to the current queue. To understand the distribution of pieces, think of the queue as two sets of seven pieces, each shuffled separately and then joined: every block will appear twice in the queue -- once in the first seven, once in the second. It can appear no more than twice in a row, and there can be up to but not more than 12 blocks in the queue between its first and second instance.  
* When all cells in a row are full, that row is [**cleared**](https://tetris.fandom.com/wiki/Line_clear), i.e. deleted from the playfield. Rows above it sink accordingly and the player's score increases.
* Clearing multiple contiguous rows adds a significantly greater number of points to the player's score.
* Four is the maximum number of contiguous rows that may be deleted after the block settles; this is achievable only by inserting an i block vertically. When this happens, the player is said to have earned a **tetris**.
* With the exception of the o block, all tetrominos have four permutations -- four distinct rotational states or [**rotations**](https://tetris.fandom.com/wiki/SRS) -- achieved by turning the block 90 degrees around a central point.
* When the player attempts a rotation that would produce an invalid position (i.e. the block would be out of bounds or overlap with squares already present on the playfield), a [**kick**](https://harddrop.com/wiki/SRS#How_Guideline_SRS_Really_Works) is automatically attempted.<a name="kick"></a>
* When a block is kicked, its center moves 0-2 cells vertically and 0-2 cells horizontally. A kick succeeds when the rotation and movement produce a valid position. The active block will then remain in that new position after rotating in the direction indicated by the player. A kick fails when it results in an invalid position. If the kick fails, the piece will appear not to move or rotate.
* When rotation results in an invalid position, the game tests a sequence of four different kicks, stopping when/if one succeeds.
* Two factors determine the sequence of kicks that the game tests: the intial and final rotation. If a block's rotation equals `2` (180 degrees) and the player attempts to a clockwise turn, to rotation `3` (270 degrees), the game will always use a certain sequence of four kicks. If the user attempts to turn the block counterclockwise, to rotation `1` (90 degrees), it will use a different kick sequence. There are eight possible rotation sequences (0->1, 0->3, 1->2, 1->0, etc) but only four kick sequences. Two rotation sequences share each kick sequence.
* The i block uses its own rotation logic and kick sequences.
* The o block does not rotate and thus does not kick.
* Rotating and kicking a block into an otherwise inaccessible position is call [**twisting** or **a twist**](https://harddrop.com/wiki/Twist). Row-clearing resulting from a twist counts for a significantly greater number of points than row deletion without a twist. (In fact, some versions of Tetris award a greater number of points for three-row deletion after a t block twist, also called a [**t-spin**](https://harddrop.com/wiki/T-Spin_Guide), than after a tetris.) 
* Tetris has no win conditions. The goal is to continue playing for as long as possible. The game ends when the next active block spawns on a square already present on the playfield, meaning that at least one cell on the top row contains a square.

#### Source code<a name="code"></a>
My implementation of the game is currently written in Java using no outside libraries. The interface is written from scratch with JavaFX and Swing. The overriding goals of the project has been to practice writing effective, clear, readable, maintainable, well-tested object-oriented code, test different data structures, refine my coding process, practice writing documentation, and have fun.

The `Tetris` class contains `main`.

`TetrisGUI` creates the user interface.

`RunTetris`, instantiated inside an instance of `TetrisGUI`, encapsulates game state and game logic.

`Constants` and `RelativeCoords` contain all constant values. `Constants` includes methods that I used like constants: `Constants.fromLeft(4)` is four cells from the left bound of the playfield; `Constants.fromRight(4)` is four cells from the right bound.

I've tried to enforce a strict separation between classes that serve as 'things' and 'classes' that serve as 'actions.' Classes for actions are typically abstract and include methods but no member variables. Classes for things are never abtract and contain no abstract or static methods and no static variables.

The 'things':

* `Square`
* `Row`
* `RowList`
* `Tetromino`
* `ActiveBlock`
* `TetrominoQueue`

The actions:

* `BoundsTester`
* `PlacementTester`
* `Rotator`
* `Translater`
* `WallKicker`
* `RowDeleter`
* `SinkingBlockFinder`

The only 'thing' class not clearly discussed in [key terms and concepts](#keyconcepts) is the `RowList`. A `RowList` is an abstraction representing any collection of any number of squares (which are always contained in rows): the squares that fill the playfield, the squares that comprise a sinking block, and the squares that comprise the active block are each one `RowList`. Thus the squares the player sees on the screen during gameplay always represent at least two `RowLists`: the active block and the playfield. 

The [key terms and concepts](#keyconcepts) describe every thing the 'action' classes do, but now how they do them. Below I summarize the logic I use for each action:

* `BoundsTester` && `PlacementTester`: These test whether a coordinate or set of coordinates is in bounds and whether already contains a square, respectively.
* `Rotator` && `Translater` change what cells that the `ActiveBlock` occupies, using `BoundsTester` and `PlacementTester` to check for validity. Rotation and translation work similarly: first, without testing for validity, the block moves or rotates  in a given manner. If the resulting change produce a valid configuration of blocks (i.e. none are out of bounds or overlap with others), the function returns `true` (for translation/movement) or an integer greater than `-1` (for rotation). If the resulting configuration is invalid, the program undoes the action it just performed and returns `false` or `-1`.
* `WallKicker`: when a rotation creates an invalid position/configuration, the game tests loops through an array of possible ['kicks'](#kick) and tests each. `WallKicker` works much like `Rotator` and `Translater` -- i.e. by performing an action, testing the validity of the position, and undoing the action if necessary.
* `RowDeleter` deletes rows from the playfield. Because row deletion happens only after one `RowList`'s blocks/`Row`s are added to another, the inserted `RowList`'s upper and lower `y` values (that is, its the highest and lowest row) also bound the rows that may be full. RowDeleter searches through those rows for full rows to delete, and lowers other, non-full rows as needed.
* `SinkingBlockFinder`: row deletion sometimes produces what I call "sinking blocks" or "floating blocks." These are blocks that aren't tetrominos, aren't controllable by the player, and aren't connected to the bottom row. They appear to be floating in the air. Thus they sink, as if affected by gravity. Not all versions of Tetris implement this behavior. (It's absent from the original Nintendo release.) Sinking is a potentially recursive pattern: row deletion can produce a sinking block that, when it sinks, fills another row, causing another deletion, which produces yet another sinking block, etc. To see this behavior in the game, type "c", lower case; this will run the integration test for recursive sinking-piece behavior. Type "d" or "x" to run the other integration tests related to sinking pieces.

#### Plans for the future

A few small tasks remain:
* Clean up the interface
* Add a couple more tests.
* Add tests for scoring and lock delay.
* Add [DAS](https://tetris.fandom.com/wiki/DAS)
* Streamline the interface, changing some of the timing (e.g. add a short pause before clearing a row and then lower rows gradually, not instantly).
* Decide between [20G, 2.36G and 0G](https://harddrop.com/wiki/20G).

I expect to continue working on this into the foreseeable future, but for ease of development I'll likely switch to C# or C++ so that I can use the Unity or Unreal engine.

The roadmap for more distant additions/changes includes the following:
* 3d interface/engine
* Competitive, cross-platform multiplayer for desktop, iOS, and Android
* User accounts
* Leaderboards
* Different game modes meant to be funny and infuriating, described [here](https://github.com/smk291/tetris/blob/master/Modes.md)

### License

This project is [MIT licensed](./LICENSE). God help you if decide to try to make money off it.
