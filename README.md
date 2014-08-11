Mancala Game
=======

A Java version of an ancient board game, believed to be more than several thousand years old!

The game has the following starting set up:

Two players
------------

 * A board containing 2 sets of 6 cups for each player laid out in two lines of 6 facing each other, with one larger "end cup" on the left of the players 6 normal cups
 * 4 beads in each of the players normal cups

The game has the following rules:

  * Players take turns to move beads from any of their normal cups.
  * In a move a player places the beads in every cup after the chosen cup, clockwise round the board.
  * In a move a player never places beads in their opponents end cup. (The opponents end cup is skipped)
  * If the move ends in the players end cup - they get another go.
  * If the move ends in a players cup that was empty. They collect that last bead and any beads in the opponents cup opposite the last cup.
  * Play finishes when either player has no more moves to make (no more beads in any normal cups)

Computer game requirements

This computer game version shall support the following ideas/requirements:

  * A graphical user interface depicting the board, cups and beads.
  * Human and/or computer players
  * Complete undo/redo support for moves (back to start of game) - to allow the user to experiment with alternative moves
  * Computer "hints" - indicating the "best" move selected by game AI
  * Computer "hints" - indicating the scoring mechanism used by the AI for each move available - with the ability to drill down into the game "tree" to look ahead 'N' moves (OPTIONAL: Plug-in)
  * Pluggable front ends: The ability to choose command line, Swing, JSP, CLDC, WAP or SWT GUIs... arbitrarily (Game logic must be separate) with possibly two simultaneously?? (e.g. Swing and command line)
  * Ability to challenge a remote player - via TCP/IP sockets - and keep two versions of the game (with potentially different UIs) in synch

Design implications of requirements

  * Use command pattern for moves - to support do, undo, redo
  * Use MVC pattern - Model to support making moves according to underlying rules and identifying "game over"
  * Central model must be event driven - and supply feedback of move acceptance or rejection.
  * Model must be able to be queried for the current allowed moves and the current player
  * Remote versions of the game will act like another source of "moves" from the opponent
  * Moves will be given suitable sequence ID so that remote "loss of moves" can be identified.
  * Game remote communications will be asynchronous to avoid loss of synchronisation between remote games
