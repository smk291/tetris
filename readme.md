<style>
table {
  width: 250px;
}
</style>


# Java Tetris

#####Contents
1. [Description](#description)
1. [Tetris-related reading material](#tetris)
1. [Discussion of source code](#code)
1. [Plans for the future](#plans)

#### Description <a name="description"></a> 

This project is a full implementation of all official Tetris rules, guidelines, and mechanics. The basic goals of the project are complete; apart from the scoring system, I've implemented nearly everything required for the game to be counted as an implementation of "official" Tetris. I use unit tests (JUnit, contained in `com.tetrisrevision.unittests` and integration tests (`accessible in-game via keystrokes`) to confirm that the game behaves correctly and "officially."

The GUI is still a rough work in progress, but the game is playable. The easiest way to play will be to open the tetris.jar file included in this repository. On Linux and Apple machines you'll need to change the file's permissions in order to make it executable.

The game should build successfully in Eclipse or IntelliJ. These IDEs also allow the users to run the full suite of unit tests.

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
Up one row |up arrow (*Can be enabled in TetrisGUI.java*)

#### Tetris-related reading material<a name="tetris"></a>

See [this article](https://en.wikipedia.org/wiki/Tetris) for an introduction to the game and [this article](https://tetris.wiki/Tetris_Guideline) for the official Tetris guidelines.

Key concepts:

* The play controls a [**tetromino**](https://tetris.fandom.com/wiki/Tetromino) as it falls.
* A tetromino is a geometric shape consisting of four square **blocks** whose edges are connected.
* There are seven different shapes, and thus seven different tetrominoes. In the game they have no names; in the outside world, each is named after the letter in the alphabet that it most resembles: I piece, j piece, l piece, o piece, s piece, t piece, and z piece. 
* The tetromino that the player currently controls is what I call the **active block**.
* The game takes place, and pieces spawn, on the **playfield**.
* The playfield is a grid 10 **cells** wide and 20 cells high.
* Within this space the player can **rotate** or **move**(/**translate**/**shift**) the active piece. Lowering a piece is a **soft drop**. Lowering a piece instantly to the lowest row and inserting it into the playfield is a **hard drop**. 
* With the exception of the o piece, all pieces have four permutations -- four possible **rotations** -- achieved by rotating the piece 90 degrees around its center.
* When the player attempts a rotation that would produce an invalid position (i.e. the piece would be out of bounds or overlap with blocks already present on the playfield), the game attempts a **kick**. 
* When attempting a kick, the game moves the center of the piece and checks the resulting position for validity. It tests four such positions. If any of those is valid, testing stops, the rotation is applied, and the center of the piece moves to that position. If the kick fails, the piece neither rotates nor moves.
* Two factors determine the sequence of kicks that the game tests: the intial and final rotation. If a piece's rotation is equivs `2` (180 degrees) and the player attempts to turn the piece clockwise, to rotation `3` (270 degrees), the game will attempt one sequence of four kicks. If the user attempts to turn the piece counterclockwise, to rotation `1` (90 degrees), the game will attempt a different sequence. There are multiple ways of computing kick values
* The I piece uses its own rotation and kick sequences.
* The o piece does not rotate.
* When a kick fills one or more rows, the deleting is scored differently. Using a kick to move a piece in to an otherwise inaccessible position is called [**twisting**](https://tetris.fandom.com/wiki/List_of_twists)

* When the active piece comes into contact with the lowest row, or a square connected (by other blocks) to the lowest row, a timer starts. This is the **lock delay**, the time within which the player can still move or rotate the piece before it **settles** in place.
* Once a piece settles, another piece spawns at the top of the playfield, on the twentieth row. becoming fixed in place and no longer manipulable. There are separate lock delays for rotation and movement. Rotating a piece resets its rotational lock delay. Moving it does *not* reset the lock delay. Attempting a soft drop 

#### Source code<a name="code"></a>
The game is currently written in Java using no outside libraries. The interface is written from scratch with JavaFX and Swing. The overriding goal of the project has been to practice writing effective, clear, readable, maintainable, well-tested object-oriented code.

`Tetris` contains `main`. `TetrisGUI` creates the user interface. `RunTetris` encapsulates game logic. 

`Constants` and `RelativeCoords` contain all constant values. `Constants.topRow` is is clearer and more maintainable than `19`. I included some methods in the list of constants, just because, effectively, the results of those methods serve the same function as a constant: `Constants.fromLeft(4)` and `Constants.fromRight(4)` should provide the `x` value fourth from the left and right edges, respectively, of the playfield.

Here are the important classes that refer to 'things' in the program:

* `Block`
* `Row`
* `RowList`
* `Tetromino`
* `TetrisPiece`
* `TetrominoQueue`

Here are the actions, ordered roughly from simplest to most complex, that the program uses: 
* `BoundsTester` && `PlacementTester`: These test whether a set of coordinates is in bounds or already contains a square 
* `Rotator` && `Translater` change the position that the `TetrisPiece`'s tetromino occupies, using `BoundsTester` and `PlacementTester` to check for validity. Rotation and translation work similarly. Without testing for validity, the program moves or rotates the piece. If the resulting change produce a valid configuration of blocks (none fall out of bounds or overlap with any others), the function returns `true` (for translation/movement) or an integer greater than `-1` (for rotation). If the resulting configuration is invalid, the program undoes the action it just performed.
* `WallKicker`: when a rotation creates an invalid configuration, the piece attempts four 'kicks,' moving the piece by a certain number of cells in some direction. `WallKicker` works the way `Rotator` and `Translater` do -- i.e. by performing an action, testing its validity, and undoing it if it's invalid.
* `RowDeleter` deletes rows from the playfield (or from any `RowList`, but it's only used with the `RowList` that represents the playfield). Because row deletion happens only after one `RowList`'s blocks/`Row`s are added to another, the inserted `RowList`'s upper and lower `y` values (that is, the highest and lowest `Row`) also represent the highest and lowest `y` values where a `Row` may be full.
* `SinkingBlockFinder`: row deletion sometimes produces what I call "sinking blocks" or "floating blocks." These are blocks that aren't tetrominos, aren't controllable by the player, and aren't connected to the bottom row. They appear to be floating in the air. Thus they sink. Not all versions of Tetris implement sinking behavior. (It's absent from the original release of Tetris.) Sinking is potentially recursive pattern: it's possible for row deletion to produce a sinking block that, when it sinks, fills another row, causing another deletion, etc. In game, type "c," lower case to run an integration test that models this behavior.

#### Plans for the future

In the immediate future, I'll clean up the interface, add more tests, and compute score and level.

I expect to continue working on this into the foreseeable future, but for ease of development I'll likely switch to C# or C++ so that I can use the Unity or Unreal engine.

The roadmap for more distant additions/changes includes the following:
* 3d interface/engine
* Competitive, cross-platform multiplayer for desktop, iOS, and Android
* User accounts
* Leaderboards
* Different game modes meant to be funny and infuriating, described [here](https://github.com/smk291/tetris/blob/master/Modes.mdhttps://github.com/smk291/tetris/blob/master/Revision%)
