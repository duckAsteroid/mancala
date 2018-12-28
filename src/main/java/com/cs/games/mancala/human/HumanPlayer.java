package com.cs.games.mancala.human;

import java.util.Iterator;
import java.util.List;
import com.cs.games.mancala.model.Board;
import com.cs.games.mancala.model.Game;
import com.cs.games.mancala.model.Move;
import com.cs.games.mancala.model.Player;

/**
 * @author <A HREF="mailto:chris.senior@teradyne.com?subject=com.cs.games.mancala.model.human.HumanPlayer">Chris Senior</A>
 */
public class HumanPlayer extends Player
{
  private GameInput input;
  private String name;
  public HumanPlayer(int num, GameInput input)
  {
    super(num);
    this.input = input;
    System.out.println("Input player name:");
    name = input.getCommand();
  }
  /**
   * @see com.cs.games.mancala.model.Player#getDisplayName()
   */
  public String getDisplayName()
  {
    return name;
  }

  /**
   * @see com.cs.games.mancala.model.Player#chooseMove(com.cs.games.mancala.model.Board)
   */
  public Move chooseMove(Board board)
  {
    List moves = board.moves(this.number);
    System.out.println("Moves:");
    Iterator iter = moves.iterator();
    while (iter.hasNext())
    {
      Move m = (Move) iter.next();
      System.out.println(m);
    }
    int chosen = -1;
    while(chosen < 0 || chosen > moves.size())
    {
      System.out.println("Please choose a move: ");
      try
      {
        chosen = Integer.parseInt(input.getCommand());
      }
      catch(Exception e)
      {
        System.out.println("Must be a number!");
      }
    }
    return (Move)moves.get(chosen);
  }
  
  
}
