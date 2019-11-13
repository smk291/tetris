# Java Tetris

##### Contents
1. [Description](#description)
1. [Tetris-related reading material](#tetris)
1. [Discussion of source code](#code)
1. [Key Concepts](#keyconcepts)
1. [Plans for the future](#plans)

#### Description <a name="description"></a> 

This is project is an implementation of Tetris. More speciically, it is a full implementation of the official Tetris Guidline (using, for instance, the [Super Rotation System or SRS](https://tetris.wiki/SRS), not the [Arika Rotation System or ARS](https://harddrop.com/wiki/ARS). The basic goals of the project are nearly complete; apart from the scoring system, I've implemented nearly everything required for the game to be counted as an implementation of "official" Tetris (and it should be obvious this isn't an official release. I have no connection to the copyright owners.). I use unit tests (JUnit, contained in `com.tetris.unittests` and integration tests (`accessible in game via keystrokes`) to confirm that the game behaves correctly and "officially."

The GUI is a rough work in progress, but the game is playable. The easiest way to play will be to open the tetris.jar file included in this repository. On Linux and Apple machines you'll need to change the file's permissions in order to make it executable.

The game should build successfully in Eclipse or IntelliJ. These IDEs also allow you to run the full suite of unit tests.

#### Controls
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

* The play controls a [**tetromino**](https://tetris.fandom.com/wiki/Tetromino) as it falls.
* A tetromino is a geometric shape consisting of four colored **squares** connected by their edges.
* There are seven different shapes, and thus seven different tetrominoes. Each is named after the letter in the alphabet that it most resembles: I block, j block, l block, o block, s block, t block, and z block. 
* The tetromino that the player currently controls is what I call the **active block**.
* The game takes place on the [**playfield**](https://tetris.fandom.com/wiki/Playfield).
* The playfield is a grid 10 **cells** wide and 20 cells high. The active block spawns on the 20th row from thej bottom. (Rows and cells are typically counted like a Cartesian coordinate system, with x increasing from left to right and y increasing from bottom to top.)
* Within the playfield the player can [**rotate**](https://tetris.fandom.com/wiki/SRS) or **move**(/**translate**/**shift**) the active block. Lowering a block is a [**soft drop**](https://harddrop.com/wiki/Drop). Lowering a block instantly to the lowest row and inserting it into the playfield is a [**hard drop**](https://harddrop.com/wiki/Drop). The player cannot raise a block.
* When the active block comes into contact with the lowest row, or a square connected (by other blocks) to the lowest row, a timer starts. This is the [**lock delay**](https://tetris.fandom.com/wiki/Lock_delay), the time within which the player can continue to move or rotate the block before it **settles** in place.
* There are separate lock delays for rotation and movement. Rotating a block resets its rotational lock delay. Moving it does *not* reset the movement lock delay.
* When the active block is adjacent to a settled square or on the bottom row, and the lock delay hasn't elapse, a soft drop will immediately settle the block.
* Once a block settles, another block spawns at the top of the playfield, on the twentieth row. 
* When all cells in a row are full, that row is [**cleared**](https://tetris.fandom.com/wiki/Line_clear) and the player's score increases.
* Clearing multiple contiguous rows adds a significantly greater number of points.
* The next active block is drawn from a randomized **queue**, also called the **bag**. There is an official [Random Generator](https://harddrop.com/wiki/Bag) algorithm. In this algorithm, a block can appear twice but not three times in a row, and there can be up to 12 blocks in the queue between one instance of a particular block and a second instance.  
* When the player deletes the maximum number of rows possible, clearing four contiguous rows (ordinarily achievable only by inserting an i block vertically), the player is said to have earne a **tetris**.
* With the exception of the o block, all blocks have four permutations -- four possible [**rotations**](https://tetris.fandom.com/wiki/SRS) -- achieved by rotating the block 90 degrees around its center.
* When the player attempts a rotation that would produce an invalid position (i.e. the block would be out of bounds or overlap with squares already present on the playfield), the game attempts a [**kick**](https://harddrop.com/wiki/SRS#How_Guideline_SRS_Really_Works). 
* When attempting a kick, the game moves the center of the block and checks the resulting position for validity. It tests four such positions. If any of those is valid, testing stops, the rotation is applied, and the center of the block moves to that position. If the kick fails, the block neither rotates nor moves.
* Two factors determine the sequence of kicks that the game tests: the intial and final rotation. If a block's rotation equals `2` (180 degrees) and the player attempts to turn the block clockwise, to rotation `3` (270 degrees), the game will use one sequence of four kicks. If the user attempts to turn the block counterclockwise, to rotation `1` (90 degrees), the game will use a different kick sequence.
* The I block uses its own rotation logic and kick sequences.
* The o block does not rotate and thus does not kick.
* Rotating and kicking a block into an otherwise inaccessible position is [**twisting** or **a twist**](https://harddrop.com/wiki/Twist). Row deletion resulting from a twist counts for a significantly greater number of points than row deletion without a twist. (Some versions of Tetris award a greater number of points for three-row deletion after a [**t-spin**](https://harddrop.com/wiki/T-Spin_Guide) twist than after a tetris.) 
* Tetris has no win condition. The goal is to continue playing for as long as possible. The game ends when the next block cannot spawn without overlapping with a square already present on the playfield.

#### Source code<a name="code"></a>
The game is currently written in Java using no outside libraries. The interface is written from scratch with JavaFX and Swing. The overriding goal of the project has been to practice writing effective, clear, readable, maintainable, well-tested object-oriented code.

`Tetris` contains `main`. `TetrisGUI` creates the user interface. `RunTetris` encapsulates game logic. 

`Constants` and `RelativeCoords` contain all constant values. `Constants.topRow` is is clearer and more maintainable than `19`. I included some methods in the list of constants, just because, effectively, the results of those methods serve the same function as a constant: `Constants.fromLeft(4)` and `Constants.fromRight(4)` should provide the `x` value fourth from the left and right edges, respectively, of the playfield.

Here are the important classes that refer to 'things' in the program:

* `Square`
* `Row`
* `RowList`
* `Tetromino`
* `ActiveBlock`
* `TetrominoQueue`

Here are the actions, ordered roughly from simplest to most complex, that the program uses: 
* `BoundsTester` && `PlacementTester`: These test whether a set of coordinates is in bounds or already contains a square 
* `Rotator` && `Translater` change the position that the `ActiveBlock`'s tetromino occupies, using `BoundsTester` and `PlacementTester` to check for validity. Rotation and translation work similarly. Without testing for validity, the program moves or rotates the block. If the resulting change produce a valid configuration of blocks (none fall out of bounds or overlap with any others), the function returns `true` (for translation/movement) or an integer greater than `-1` (for rotation). If the resulting configuration is invalid, the program undoes the action it just performed.
* `WallKicker`: when a rotation creates an invalid configuration, the game attempts four 'kicks,' moving the block by a certain number of cells in some direction. `WallKicker` works the way `Rotator` and `Translater` do -- i.e. by performing an action, testing its validity, and undoing it if it's invalid.
* `RowDeleter` deletes rows from the playfield (or from any `RowList`, but it's only used with the `RowList` that represents the playfield). Because row deletion happens only after one `RowList`'s blocks/`Row`s are added to another, the inserted `RowList`'s upper and lower `y` values (that is, the highest and lowest `Row`) also represent the highest and lowest `y` values where a `Row` may be full.
* `SinkingBlockFinder`: row deletion sometimes produces what I call "sinking blocks" or "floating blocks." These are blocks that aren't tetrominos, aren't controllable by the player, and aren't connected to the bottom row. They appear to be floating in the air. Thus they sink. Not all versions of Tetris implement sinking behavior. (It's absent from the original Nintendo release of Tetris.) Sinking is potentially recursive pattern: it's possible for row deletion to produce a sinking block that, when it sinks, fills another row, causing another deletion, etc. In game, type "c," lower case to run an integration test that models this behavior.

#### Plans for the future

In the immediate future, I'll clean up the interface, add more tests, compute score and level, add [DAS](https://tetris.fandom.com/wiki/DAS), fix some incorrect delay durations, decided between [20G, 2.36G and 0G](https://harddrop.com/wiki/20G), and fix the lock delay.

I expect to continue working on this into the foreseeable future, but for ease of development I'll likely switch to C# or C++ so that I can use the Unity or Unreal engine.

The roadmap for more distant additions/changes includes the following:
* 3d interface/engine
* Competitive, cross-platform multiplayer for desktop, iOS, and Android
* User accounts
* Leaderboards
* Different game modes meant to be funny and infuriating, described [here](https://github.com/smk291/tetris/blob/master/Modes.mdhttps://github.com/smk291/tetris/blob/master/Revision%)
