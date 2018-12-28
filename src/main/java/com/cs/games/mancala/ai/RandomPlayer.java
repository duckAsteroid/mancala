package com.cs.games.mancala.ai;

import java.util.List;
import com.cs.games.mancala.model.Board;
import com.cs.games.mancala.model.Move;
import com.cs.games.mancala.model.Player;

/**
 * @author <A
 *         HREF="mailto:chris.senior@teradyne.com?subject=com.cs.games.mancala.model.ai.RandomPlayer">Chris
 *         Senior </A>
 */
public class RandomPlayer extends Player
{
  /**
   * @param number
   */
  public RandomPlayer(int number)
  {
    super(number);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.cs.games.mancala.model.Player#chooseMove(com.cs.games.mancala.model.Board)
   */
  public Move chooseMove( Board board)
  {
    List moves = board.moves(this.number);
    int selection = (int)(Math.random() * moves.size());
    try
    {
      Thread.sleep(10000);
    }
    catch (InterruptedException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return (Move)moves.get(selection);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.cs.games.mancala.model.Player#getDisplayName()
   */
  public String getDisplayName()
  {
    return "Random player";
  }
}