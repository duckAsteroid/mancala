package com.cs.games.mancala.ai;

import com.cs.games.mancala.model.Board;

/**
 * An object that encapsulates simulated board state after a move has been made.
 * The information in the state is calculated from a single player's perspective.
 * The outcomes are Comparable and can be ordered by that.
 * @author <A HREF="mailto:chris.senior@teradyne.com?subject=com.cs.games.mancala.model.ai.MoveOutcome">Chris Senior</A>
 */
public class MoveOutcome implements Comparable
{
  /**
   * Does this outcome equate to another go
   */
  private boolean anotherGo;
  /**
   * Does this outcome mean the player wins
   */
  private boolean win;
  /**
   * The lead the player has after this move (negative means losing)
   */
  private int lead;
  /**
   * The player
   */
  private int player;
  /**
   * The move on the current board that was simulated to produce this outcome
   */
  private int move;
  /**
   * The board after the move has been made
   */
  private Board board;
  
  /**
   * Create a move outcome object from a given board for a given player
   * based on simulating the given move.
   * @param current The current status of the board
   * @param player The player from whom's perspective to calculate the outcome
   * @param move The move to simulate the outcome of
   */
  public MoveOutcome(Board current, int player, int move, boolean update)
  {
    this.board = (Board)current.clone(); // clone the board first
    this.anotherGo = board.makeMove(player, move, update); // make the move
    
    this.win = board.isWinner(player, false);
    this.lead = board.lead(player);
    this.player = player;
    this.move = move;
  }
 
  /**
   * The simulated board state after the move is made - from which the outcome
   * is calculated. 
   */
  public Board getOutcome()
  {
    return board;
  }
  /**
   * Does this outcome result in another go for the player
   */
  public boolean isAnotherGo()
  {
    return anotherGo;
  }

  /**
   * The lead in this outcome
   */
  public int getLead()
  {
    return lead;
  }
  
  /**
   * The move that was made to reach this outcome
   */
  public int getMove()
  {
    return move;
  }

  /**
   * The player which this outcome is in the perspective of
   */
  public int getPlayer()
  {
    return player;
  }

  /**
   * Does this outcome result in the given player winning
   */
  public boolean isWin()
  {
    return win;
  }

  /**
   * Give an integer representing the order of this outcome and another.
   * In this context less than means "worse than" - hence a sorted collection 
   * of outcomes will contain the "worst first".
   * This outcome is more than another if it wins and the other doesn't. 
   * However if both outcomes win or lose - this outcome is more than another if 
   * it's lead is greater. Lastly if the two outcomes are still considered equal
   * any outcome that results in another go will be considered greater than. If
   * both result in another go or both do not result in another go - the two
   * outcomes are identical and 0 is returned. 
   * @return a negative integer, zero, or a positive integer as this object
   *         is less than, equal to, or greater than the specified object.
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Object o)
  {
    MoveOutcome other = (MoveOutcome)o;
    int result = winCompare(other);
    if (result == 0) // if win doesn't order it try lead
    {
      result = leadCompare(other);
      if (result == 0) // if lead still doesn't try another go
      {
        result = anotherGoCompare(other); 
      }
    }
    return result;
  }
  /**
   * Give order (as in Comparable) based solely on comparing "anotherGO"
   * @param other The other outcome
   * @return Comparison as per Comparable
   */
  public int anotherGoCompare(MoveOutcome other)
  {
    if (this.anotherGo && !other.anotherGo)
    {
      return 1; // greater than - this another go
    }
    else if (!this.anotherGo && other.anotherGo)
    {
      return -1; // less than - other another go
    }
    else
      return 0; // equal - both same
  }
  /**
   * Give order (as in Comparable) based solely on comparing "lead". The outcome
   * with the biggest lead is "after" the other.
   * @param other The other outcome
   * @return Comparison as per Comparable
   */
  public int leadCompare(MoveOutcome other)
  {
    return this.lead - other.lead; // if this is a bigger lead we get a positive number
  }
  /**
   * Give order (as in Comparable) based solely on comparing "win". An outcome 
   * that wins is "after" of one that does not
   * @param other The other outcome
   * @return Comparison as per Comparable
   */
  public int winCompare(MoveOutcome other)
  {
    if (this.win && !other.win)
    {
      return 1; // greater than - this wins
    }
    else if (!this.win && other.win)
    {
      return -1; // less than - other wins
    }
    else
      return 0; // equal - both lose/win
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object obj)
  {
    return compareTo(obj) == 0;
  }

  

}
