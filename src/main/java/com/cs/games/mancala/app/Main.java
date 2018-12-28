package com.cs.games.mancala.app;

import com.cs.games.mancala.ai.ComputerPlayer;
import com.cs.games.mancala.ai.MoveOutcome;
import com.cs.games.mancala.ai.RandomPlayer;
import com.cs.games.mancala.human.GameInput;
import com.cs.games.mancala.human.HumanPlayer;
import com.cs.games.mancala.model.Game;
import com.cs.games.mancala.model.Move;
import com.cs.games.mancala.ui.BoardDisplayer;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    GameInput input = new GameInput();
    Game g = new Game(new ComputerPlayer(0), new HumanPlayer(1, input));
    BoardDisplayer display = new BoardDisplayer(g.getBoard());
    g.getBoard().addBoardUpdateListener(display);
    JFrame test = new JFrame("Test UI");
    test.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        System.exit(0);
      }
    });
    test.getContentPane().add(display);
    test.setSize(640, 480);
    test.setVisible(true);

    while(!g.getBoard().isGameOver()) {
      Move move = g.getCurrentPlayer().chooseMove(g.getBoard());
      MoveOutcome outcome = move.move();
      if (!outcome.isAnotherGo()) {
        g.switchCurrentPlayer();
      }
      g.setBoard(g.getBoard());
      //display.setBoard(outcome.getOutcome());
    }
  }
}
