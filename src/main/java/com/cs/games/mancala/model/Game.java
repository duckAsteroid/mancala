package com.cs.games.mancala.model;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A game gathers together two players and a board.
 * @author <A HREF="mailto:chris.senior@teradyne.com?subject=com.cs.games.mancala.model.Game">Chris Senior</A>
 */
public class Game
{
  /**
   * Logger for writing log messages (Log4j)
   */
  private static final Logger logger = LoggerFactory.getLogger(Game.class);
  /**
   * The history of moves in the game to date
   */
  private List history = new ArrayList();
  /**
   * The players. Two of them
   */
  private Player[] players = new Player[2];
  /**
   * Who's go is it
   */
  private int current = 0;
  /**
   * The current board state
   */
  private Board board;
  
  public Game(Player p1, Player p2)
  {
    players[0] = p1;
    players[1] = p2;
    board = new Board();
  }

  public Board getBoard()
  {
    return board;
  }
  
  public Player getCurrentPlayer()
  {
    return players[current];
  }
  /**
   * The 
   * @return
   */
  public List getCurrentMoves()
  {
    return board.moves(current);
  }
  
  public List getMovesHistory()
  {
    return history;
  }
  /**
   * Change current player to the other one
   */
  public void switchCurrentPlayer()
  {
    if (current == 0)
      current = 1;
    else
      current = 0;
  }

  public void setBoard(Board board) {
    this.board = board;
  }
}
