package com.cs.games.mancala.model;

/**
 * @author <A HREF="mailto:chris.senior@teradyne.com?subject=com.cs.games.mancala.model.Player">Chris Senior</A>
 */
public abstract class Player
{
  /**
   * Player number
   */
  protected int number;
  /**
   * Create a numbered player
   * @param number
   */
  public Player(int number)
  {
    this.number = number;
  }
  /**
   * A name to use to identify the player for humans
   * @return
   */
  public abstract String getDisplayName();
  /**
   * Player's need to decide what move to make next
   * @param board
   * @return
   */
  public abstract Move chooseMove(Board board);
  /**
   * The string form of the player is the name
   * @see java.lang.Object#toString()
   */
  public String toString()
  {
    return getDisplayName();
  }
}
