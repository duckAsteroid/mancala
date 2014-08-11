package com.cs.games.mancala.model;

import com.cs.games.mancala.ai.MoveOutcome;

/**
 * A command pattern move class. Can be done and undone
 * @author SeniorC
 */
public class Move
{
  private int cup;
  private int player;
  private Board before;
  
  /**
   * @param before The initial board condition
   * @param player The player to make the move
   * @param cup The cup to move
   */
  public Move(Board before, int player, int cup)
  {
    this.cup = cup;
    this.player = player;
    this.before = before;
  }
  public MoveOutcome simulate()
  {
    return before.doMove(player, cup, false);
  }
  /**
   * Actually do the move
   * @return
   */
  public MoveOutcome move()
  {
    return before.doMove(player, cup, true);
  }
  /**
   * Undo the move - return board in original state
   * @return
   */
  public Board undo()
  {
    return before;
  }
}
