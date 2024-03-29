# Java Tetris

##### Contents
1. [Description](#description)
1. [Tetris-related reading material](#tetris)
1. [How to play](#howtoplay)
1. [Key Terms and Concepts](#keyconcepts)
1. [The source code](#code)
1. [Plans for the future](#plans)

#### Description <a name="description"></a> 

This project is an implementation of Tetris, the classic video game first released in 1984. More specifically, the project is a full implementation of the present-day official [Tetris Guidline](https://tetris.fandom.com/wiki/Tetris_Guideline) (using, for instance, the [Super Rotation System or SRS](https://tetris.wiki/SRS), not the [Arika Rotation System or ARS](https://harddrop.com/wiki/ARS)). The basic goals of the project are complete. I've used unit tests (JUnit, contained in `com.tetris.tests.unit`) and integration tests (`com.tetris.tests.integration`) to confirm that the game behaves "officially." The GUI is a work in progress, but the game is playable. 

My implementation is currently written in Java using no outside libraries. The interface is written from scratch with JavaFX and Swing. The overriding goals of the project have been to practice writing effective, clear, readable, maintainable, well-tested, object-oriented code; to test different data structures; to refine my coding process; to write documentation; and to have fun.

Use an IDE like IntelliJ or Eclipse to build the project and run unit tests.

#### Tetris-related reading material<a name="tetris"></a>

See [this article](https://en.wikipedia.org/wiki/Tetris) for an introduction to the game and [this article](https://tetris.wiki/Tetris_Guideline) for the official Tetris Guideline.

#### How to Play<a name="howtoplay"></a>
The easiest way to play will be to open the tetris.jar file included in this repository. In a Linux or macOS environment you'll first need to change the file's permissions in order to make it executable.

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

* This is disabled, but it can be enabled in [/src/com/tetris/gui/input/HandleInput.java](https://github.com/smk291/tetris/blob/master/src/com/tetris/gui/input/HandleInput.java#L52)


#### Key terms and concepts:<a name="keyconcepts"></a>

Important terms and concepts are **highlighted**.

* The player controls a [**tetromino**](https://tetris.fandom.com/wiki/Tetromino), also called a **block** or sometimes a **piece**, as it falls.
* A tetromino is a geometric shape consisting of four colored **squares** connected at their edges.
* There are seven different configurations of squares arranged this way, and thus there are seven different tetrominoes. Each is named after the letter in the alphabet that it most resembles: [i](https://tetris.fandom.com/wiki/I-Block), [j](https://tetris.fandom.com/wiki/J-Block), [l](http://tetris.fandom.com/wiki/L-Block), [o](https://tetris.fandom.com/wiki/O-Block), [s](https://tetris.fandom.com/wiki/S-Block), [t](https://tetris.fandom.com/wiki/T-Block), and [z](https://tetris.fandom.com/wiki/Z-Block). 
* The tetromino that the player currently controls is what I call the **active block**.
* The tetromino falls faster and faster as the game progresses, leaving the player less time to manipulate it.
* The game takes place on the [**playfield**](https://tetris.fandom.com/wiki/Playfield).
* The playfield is a grid 10 **cells** wide and 20 cells high. Rows and cells are typically counted as they are in a Cartesian coordinate system, with x increasing from left to right and y increasing from bottom to top.
* The active block spawns on the 20th row. 
* Within the playfield the player can [**rotate**](https://tetris.fandom.com/wiki/SRS) or **move**(/**translate**/**shift**) the active block. 
* Rotation and movement occur in discrete units. Blocks cannot move less or more than one square's width left, right, or down (but not up), and can rotate only 90 degrees left or right.
* Lowering a block once (i.e. from its current row to the row below) is a [**soft drop**](https://harddrop.com/wiki/Drop). Lowering a block to the lowest possible position instantly is a [**hard drop**](https://harddrop.com/wiki/Drop).
* The player cannot directly raise a block from its current row to the row above (though this is possible indirectly).
* When the active block moves to the lowest row or its base comes into contact with a square connected (by other squares) to the lowest row, a timer starts. This is the [**lock delay**](https://tetris.fandom.com/wiki/Lock_delay), the time within which the player can continue to move or rotate the block before it **settles** in place and becomes an immovable part of the playfield.
* There are separate lock delays for rotation and movement. Rotating a block resets the rotational and movement lock delays. Moving the block does *not* reset the movement lock delay.
* When the base of the active block is on top of a settled square or is on the bottom row, and the lock delay hasn't yet elapsed, a soft drop will immediately settle the block.
* There is no lock delay after a hard drop. The block settles instantly.
* Once a block settles, a new active block spawns at the top of the playfield.
* The next active block is drawn from a randomized **queue**, also called the **bag**. There is an official [Random Generator](https://harddrop.com/wiki/Bag) algorithm. This algorithm starts with a randomly shuffled list of the seven blocks. When the queue drops to a certain length, another set of randomly shuffled blocks is appended to the current queue. 
* To understand the consequences of this algorithm for distribution of blocks, think of the queue as two sets of seven pieces, shuffled separately and joined. Each of the seven blocks will appear twice in this queue: once in the first seven items; once in the second seven items. Thus a given block can appear no more than twice in a row and there can be at most 12 blocks in the queue between its first and second instances.
* When all cells in a row are full, that row is [**cleared**](https://tetris.fandom.com/wiki/Line_clear), i.e. deleted from the playfield. Rows above it sink accordingly and the player's score increases.
* Clearing multiple contiguous rows adds a significantly greater number of points to the player's score.
* Four is the maximum number of contiguous rows that may be deleted after the block settles; this is achievable only by inserting an i block vertically. When this happens, the player is said to have earned a **tetris**.
* Clearing a row will sometimes produce a set of squares that are not connected to the bottom row; they appear to be floating in the air). These are what I call *sinking blocks*. Sinking blocks fall at a constant rate (faster than the rate at which the tetromino falls), settle into position on the playfield according to the same rules as tetrominoes, and become part of the playfield. Like tetrominoes, they can fill and clear rows, resulting in, potentially, yet another sinking block.
* With the exception of the o block, all tetrominoes have four permutations -- four distinct rotational states or [**rotations**](https://tetris.fandom.com/wiki/SRS) -- achieved by turning the block 90 degrees around a central point.
* When the player attempts a rotation that would produce an invalid position (i.e. the block would be out of bounds or overlap with squares already present on the playfield), a [**kick**](https://harddrop.com/wiki/SRS#How_Guideline_SRS_Really_Works) is automatically attempted.<a name="kick"></a>
* When a block is kicked, its center moves 0-2 cells vertically and 0-2 cells horizontally. A kick succeeds when the rotation and movement produce a valid position. The active block will then remain in that new position after rotating in the direction indicated by the player. A kick fails when it results in an invalid position. If the kick fails, the piece will appear not to have moved or rotated.
* When rotation results in an invalid position, the game tests a sequence of four different kicks, stopping when/if one succeeds.
* Two factors determine the sequence of kicks that the game tests: the intial and final rotation. If a block's rotation equals `2` (180 degrees) and the player attempts to a clockwise turn, to rotation `3` (270 degrees), the game will always use a certain sequence of four kicks. If the user instead attempts to turn the block counterclockwise from rotation `2`, to rotation `1` (90 degrees), the game will loop through a different kick sequence. There are eight possible rotation sequences (0->1, 0->3, 1->2, 1->0, etc) but only four kick sequences. Two rotation sequences share each kick sequence.
* The i block uses its own rotation logic and kick sequences.
* The o block does not rotate and thus does not kick.
* Rotating and kicking a block into an otherwise inaccessible position is call [**twisting** or **a twist**](https://harddrop.com/wiki/Twist). Row-clearing resulting from a twist counts for a significantly greater number of points than row deletion without a twist. (Some versions of Tetris award a greater number of points for three-row deletion after a t block twist, also called a [**t-spin**](https://harddrop.com/wiki/T-Spin_Guide), than after a tetris.) 
* Tetris has no win conditions. The goal is to continue playing for as long as possible. The game ends when the next active block spawns on a square already present on the playfield, meaning that at least one cell on the top row already contains a square (i.e. the board is at least partly full).

#### Source code<a name="code"></a>
My implementation of the game is currently written in Java using no outside libraries. The interface is written from scratch with JavaFX and Swing.

The `Tetris` class contains `main`.

`TetrisGUI` creates the user interface.

`RunTetris`, instantiated inside an instance of `TetrisGUI`, encapsulates game state and game logic.

`Constants` and `RelativeCoords` contain all constant values. `Constants` includes methods that I use like constants: `Constants.fromLeft(4)` is four cells from the left bound of the playfield; `Constants.fromRight(4)` is four cells from the right bound.

I've tried to enforce a strict separation between classes that serve as 'things' and classes that serve as 'actions.' Classes for actions are typically abstract and include static methods and no member variables. Classes for things are never abtract and contain no abstract or static methods and no static variables.

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

The only 'thing' class not clearly discussed in [key terms and concepts](#keyconcepts) is the `RowList`. A `RowList` is an abstraction representing a collection of any number of squares (which are always contained in rows): the squares that fill the playfield, the squares that comprise a sinking block, and the squares that comprise the active block are each one `RowList`. Thus the squares the player sees on the screen during gameplay always represent at least two `RowLists`: the active block and the playfield. 

The [key terms and concepts](#keyconcepts) describe every thing the 'action' classes do, but now how they do them. Below I summarize the logic I use for each action:

* `BoundsTester` && `PlacementTester`: These test whether a coordinate or set of coordinates is in bounds and whether already contains a square, respectively.
* `Rotator` && `Translater` change what cells the `ActiveBlock`'s squares occupy, using `BoundsTester` and `PlacementTester` to check for validity. Rotation and translation work similarly: first, without testing for validity, the block moves or rotates  in a given manner. If the resulting change produce a valid configuration of blocks (i.e. none are out of bounds or overlap with other squares), the function returns either `true` (for translation/movement) or an integer greater than `-1` (for rotation). If the resulting configuration is invalid, the program undoes the action it just performed and returns `false` or `-1`.
* `WallKicker`: when a rotation creates an invalid position/configuration, the game loops through an array of possible ['kicks'](#kick) and tests each. `WallKicker` works much like `Rotator` and `Translater`: it performs an action, tests the validity of the position, and undoes the action if necessary.
* `RowDeleter` clears rows from the playfield. Because row deletion happens only after one `RowList`'s blocks/`Row`s are added to the `RowList` representing the playfield, the inserted `RowList`'s upper and lower `y` values (that is, its the highest and lowest row) also bound the rows that may be full. `RowDeleter` tests only those rows to determine whether they're full, removes them if necessary, and lowers other, non-full rows as needed.
* `SinkingBlockFinder`: row deletion sometimes produces what I call "sinking blocks" or "floating blocks." These are blocks that aren't tetrominoes, aren't controllable by the player, and aren't connected to the bottom row. They appear to be floating in the air. Thus they sink, as if affected by gravity. Not all versions of Tetris implement this behavior. (It's absent from the original Nintendo release.) Sinking is a potentially recursive pattern: row deletion can produce a sinking block that, when it sinks, fills another row, causing another deletion, which produces yet another sinking block, etc. To see this behavior in the game, type "c", lower case; this will run the integration test for recursive sinking-piece behavior. Type "d" or "x" to run the other integration tests related to sinking pieces.

#### Plans for the future

A few small tasks remain:
* Clean up the interface
* Add a couple more tests.
* Add tests for scoring and lock delay.
* Add [DAS](https://tetris.fandom.com/wiki/DAS)
* Streamline the interface, changing some of the timing (e.g. add a short pause before clearing a row and then lower rows gradually, not instantly).

I expect to continue working on this well into the foreseeable future, but for ease of development I'll likely switch to C# or C++ so that I can use the Unity or Unreal engine.

The roadmap for additions/changes includes the following:
* 3d interface/engine
* Competitive, cross-platform multiplayer for desktop, iOS, and Android
* User accounts
* Leaderboards

### License

This project is [MIT licensed](./LICENSE). Do with the code as you please. God help you if decide to try to make money off it.
