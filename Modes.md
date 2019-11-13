#####Float Tetris (or Newtonian Tetris)
In normal Tetris ("integer" Tetris?), executing an action causes the tetromino to perform that action in its entirety and then (if we imagine that action as motion) come to a complete stop. 

In Float/Newtonian Tetris, this is no longer true. Actions are now gradual and partial. When a piece drops, it doesn't drop by a full row and then stop. It will drop by, say, 0.1 of a row. When it rotates, it won't rotate a full 90 degrees and then stop. It will rotate, say, 1 degree.

To make things worse, actions don't directly move the piece. They apply force to the piece, adding momentum. That momentum results in directional movement or rotation. The longer the player performs the action, the more momentum the piece gains. Moreover, a tetromino in motion will remain in motion unless acted upon by an outside force. Thus, in order to halt an action, the player must apply force in the opposite direction.

Pieces will fall automatically, as they do in regular Tetris. This, too, will add momentum, so that pieces fall faster and faster as they reach the bottom of the playfield. The player will be able to apply upward force to the piece, slowing but not halting its descent. The higher the level, the higher the minimum speed of descent.

Collisions will cause bounce, potentially deforming the tetromino, the blocks already settled at the bottom of the playfield, or both. So "smashing" a piece against a wall or the bottom row becomes a viable strategy.

####Tank Tetris
In Tank Tetris, pieces move like tanks: they can rotate and move forward or backward, but the direction of "forward" and "backward" depends on the direction the piece is facing. This should be challenging for experienced Tetris players, especially when combined with float/newtonian mode.

####Asteroids/Geometry Wars Mode
Evade enemies/obstacles. If one collides with the pieces the that player is controlling, it (and the player) suffer damage. I think this would pair well with a competitive mode that allows players to add or transfer enemies and obstacles to the opponents' playfield.

####Capablanca Mode
Settled pieces are invisible, so the board always appears to be empty. Good luck memorizing the playfield!

####Drunken-Master Mode
Double vision, wavy vision, a slowly spinning playfield, and controls that are less accurate and sometimes inverted. May cause dizziness or motion sickness. Safe with most medications.