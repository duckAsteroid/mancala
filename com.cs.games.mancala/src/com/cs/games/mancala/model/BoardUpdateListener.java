package com.cs.games.mancala.model;

/**
 * Called by the board when cups change
 * @author <A HREF="mailto:chris.senior@teradyne.com?subject=com.cs.games.mancala.model.BoardUpdateListener">Chris Senior</A>
 */
public interface BoardUpdateListener
{
  /**
   * Called when a cup on the board changes the number of beads
   * @param source The board where the update occurs
   * @param cupIndex The index of the cup that has changed
   */
  public void cupChanged(Board source, int cupIndex);
}
