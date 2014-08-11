package com.cs.games.mancala.model;

import com.cs.games.mancala.ai.RandomPlayer;
import com.cs.games.mancala.ui.BoardDisplayer;

/**
 * Entry point for mancala game
 * @author SeniorC
 */
public class Main
{
  public static void main( String[] args)
  {
    // use arguments to determine what kind of game UI(s) to use...
    // UI(s) are responsible for picking players and starting game
    //CommandLineSetup cls = new CommandLineSetup();
    Game g = new Game(new RandomPlayer(0), new RandomPlayer(1));
    BoardDisplayer display = new BoardDisplayer(g.getBoard());
    g.getBoard().addBoardUpdateListener(display);
    while(g.getBoard().isGameOver())
    {
      try
      {
        Thread.sleep(10000);
      }
      catch (InterruptedException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
