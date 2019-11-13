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

This project is a full implementation of all official Tetris rules, guidelines, and mechanics. The basic goals of the project are complete; apart from the scoring system, I've implemented everything required for the game to be counted as an implementation of "official" Tetris. I've also used unit tests to confirm that the game behaves as expected.

The GUI is still rough and obviously a work in progress, but the game is playable. The easiest way to play will be to open the tetris.jar file included in this repository. On Linux and Apple machines you'll need to change the file's permissions in order to make it executable.

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

#### Source code<a name="code"></a>


#### Plans for the future

In the immediate future, I'll clean up the interface, add a couple more tests, and compute score and level.

I expect to continue working on this into the foreseeable future, but for ease of development I'll likely switch to C# or C++ so that I can use the Unity and/or Unreal engines.

The roadmap for more distant additions/changes includes the following:
* 3d interface/engine
* Competitive, cross-platform multiplayer for desktop, iOS, and Android
* User accounts
* Leaderboards
* Different game modes meant to be funny and infuriating, described [here](https://github.com/smk291/tetris/blob/master/Modes.mdhttps://github.com/smk291/tetris/blob/master/Revision%)
