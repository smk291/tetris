<style>
table {
  width: 250px;
}
</style>


# Java Tetris



####Description

This project is a full implementation of all official Tetris rules, guidelines, and mechanics. The basic goals of the project are complete; apart from the scoring system, I've implemented everything required for the game to be counted as an implementation of "official" Tetris. I've also used unit tests to confirm that the game behaves as expected.

####How the game works
See [this article](https://en.wikipedia.org/wiki/Tetris) for an introduction to the game and [this article](https://tetris.wiki/Tetris_Guideline) for the official Tetris guidelines

####Controls
The GUI is still very rough, but the game is playable.


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

####Plans for the future
I expect to continue working on this into the foreseeable future, but for ease of development I'll likely switch to C# and a game engine.

The roadmap for future improvements include the following
* 3d interface/engine (probably using Unity or a similar game engine).
* Competitive, cross-platform multiplayer for desktop, iOS, and Android
* User accounts
* Leaderboards
* Two game modes meant to be funny and infuriating: (1) Float/Newtonian tetris; (2) Tank tetris.

#####Float Tetris (or Newtonian Tetris)
In normal Tetris ("integer" Tetris?), executing an action causes the tetromino to perform that action in its entirety and then come to a complete stop. In float/newtonian tetris, this is no longer true.

Actions are now gradual and partial: when a piece drops, it won't drop by a full row and then stop. It will drop by, say, 0.1 of a row; when it rotates, it won't rotate a full 90 degrees and then stop. It will rotate, say, 1 degree.

To make things even worse, all actions add momentum. The longer the player performs the action, the faster the action will be performed. A tetromino in motion will remain in motion unless acted upon by an outside force (Hence "Newtonian). Thus, in order to halt an action, the player must apply force in the opposite direction.

Pieces will fall automaticaly, as they do in regular Tetris. This, too, will add momentum, so that pieces fall faster and faster as they reach the bottom of the playfield. Unlike regular Tetris, though, the player will be able to apply upward force to the piece, slowing but not halting its descent. The higher the level, the higher the minimum speed of descent.

Collisions will cause bounce, and bouncing will potentially change the shape of the tetromino and/or the pieces already settled at the bottom of the playfield.

####Tank Tetris
In Tank Tetris, pieces move like tanks: they can rotate and move forward or backward, but the direction of "forward" and "backward" depends on the direction the piece is facing. This should particularly challenging for experienced Tetris players, especially when it's combined with float/newtonian mode.

####Asteroids/Geometry Wars Mode
Evade enemies/obstacles. One collides with you or manages to hit you, you lose control of your piece and suffer damage. I think this would pair well with a competitive mode that allows players to add or transfer enemies and obstacles to the opponents' playfield.

####Blindfold Mode
Settled pieces become invisible. Good luck memorizing the playfield's contents!