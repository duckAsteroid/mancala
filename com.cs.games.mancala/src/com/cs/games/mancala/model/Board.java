package com.cs.games.mancala.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import com.cs.games.mancala.ai.MoveOutcome;

/**
 * Represents the Mancala board of cups and beads.
 * 
 * <pre>
 * 
 *  
 *   --------------&gt;------------------
 *   |   | 0 | 1 | 2 | 3 | 4 | 5 |   |  Player 1
 *   |13 |-----------------------| 6 |
 *   |   |12 |11 |10 | 9 | 8 | 7 |   |  Player 2
 *   --------------&lt;------------------
 *   
 *  
 * </pre>
 * 
 * @author <A
 *         HREF="mailto:chris.senior@teradyne.com?subject=com.cs.games.mancala.model.Board">Chris
 *         Senior </A>
 */
public class Board implements Cloneable
{
  /**
   * The counts of beads in the cups on the board
   */
  private int[] cups;
  /**
   * listeners for board events
   */
  private Vector listeners = new Vector();
  /**
   * Option to print scores in the debug string
   */
  private boolean scores;
  /**
   * Option to print extra debug info in debug string (cup indexes)
   */
  private boolean verbose;

  /**
   * Create board with initial bead amounts in cups
   */
  public Board()
  {
    cups = new int[7 * 2];
    for (int i = 0; i < cups.length; i++)
    {
      cups[i] = 4;
    }
    cups[6] = cups[13] = 0;
  }

  /**
   * Deep copies the cup array provided (copies contained values not ref to
   * array)
   * 
   * @param cups
   */
  private Board(int[] cups)
  {
    this.cups = new int[14];
    for (int i = 0; i < cups.length; i++)
    {
      this.cups[i] = cups[i];
    }
  }

  /**
   * The available cups to "move" for a player
   * 
   * @param player The player number (1 or 2)
   * @return An array containing the indexes of the cups that can be moved by a
   *         player
   */
  public List moves( int player)
  {
    List moves = new ArrayList();
    for (int i = 0; i < 6; i++)
    {
      if (cupCount(player, i) > 0)
      {
        // add a new move
        moves.add(new Move(this, player, i));
      }
    }
    return moves;
  }

  /**
   * Create an outcome representing a particular move.
   * 
   * @param player The player making the move (0 or 1)
   * @param cup The cup to move (0 to 5)
   * @param update Notify update listeners of changes to the board
   * @return If the player has another go
   */
  public MoveOutcome doMove( int player, int cup, boolean update)
  {
    return new MoveOutcome(this, player, cup, update);
  }

  /**
   * The board index of a players cup
   * 
   * @param player The player (0 or 1)
   * @param cup The cup index (0 to 5)
   * @return The real index of the cup (0 to 13)
   */
  private int boardCupIndex( int player, int cup)
  {
    int realCup = (player * 7) + cup;
    return realCup;
  }

  /**
   * Implementation of the move method - used by other move methods.
   * 
   * @param player The player to make the move (0 or 1)
   * @param cup The board index of the cup to move (0 to 13)
   * @param update Should board update listeners be notified of changes to the
   *          board
   * @return If the player gets another go once the move has been made
   */
  public boolean makeMove( int player, int cup, boolean update) throws IllegalArgumentException
  {
    //System.out.println("Move> player="+player+"; cup="+cup);
    if (!isThisPlayerNormalCup(player, cup))
      throw new IllegalArgumentException("cup=" + cup + " is not a current player cup");
    int beads = cups[cup];
    if (beads == 0)
      throw new IllegalArgumentException("cup=" + cup + " no beads to move");
    clearCup(cup);
    if (update)
      fireUpdate(cup);
    for (; beads > 0; beads--)
    {
      cup++;
      if (cup == otherPlayerEndCupIndex(player))
        cup++; // don't put beads in other players cup
      if (cup >= cups.length)
        cup = cup - cups.length;
      addToCup(cup);
      if (update)
        fireUpdate(cup);
    }
    //System.out.println("Finished move on cup="+cup);
    // finished on live "cup" and cup contains one bead
    // get that bead and beads in cup opposite
    boolean goAgain = false;
    if (isPlayerEndCup(player, cup))
    {
      // another go
      goAgain = true;
    }
    else if (isThisPlayerNormalCup(player, cup) && cups[cup] == 1)
    {
      // grab the last bead and the opposite cups worth
      cups[playerEndCup(player)] += cups[cup];
      clearCup(cup);
      if (update)
      {
        fireUpdate(cup);
        fireUpdate(playerEndCup(player));
      }
      if (cups[cupOpposite(cup)] > 0)
      {
        cups[playerEndCup(player)] += cups[cupOpposite(cup)];
        clearCup(cupOpposite(cup));
        if (update)
        {
          fireUpdate(cupOpposite(cup));
          fireUpdate(playerEndCup(player));
        }
      }
    }
    // swap player?
    return goAgain;
  }

  /**
   * The game is over if on this board either player can make no more moves.
   * That is if either player has any normal cups with beads in the game is
   * not over
   * @return true If the game is over
   */
  public boolean isGameOver()
  {
    for (int i = 0; i < cups.length - 1; i++) // i < 13 (!= 13 which is end cup)
    {
      if (i == (cups.length - 1) / 2) //  == (14 - 1) / 2 == 6 (end cup)
        i++; // skip end cup
      else if (cups[i] > 0)
        return false;
    }
    return true;
  }

  /**
   * Is the player a winner based on scores alone
   * @param player The player to test if they are a winner
   * @param drawIsWin Is a draw considered a winning situation
   * @return true If the given player is a winner
   */
  public boolean isWinner( int player, boolean drawIsWin)
  {
    int diff = lead(player);
    if (diff != 0)
    {
      return diff > 0;
    }
    else
      return drawIsWin;
  }

  /**
   * How much does the current player lead by. Negative implies current player
   * is losing.
   * 
   * @return The number of points ahead of the other player the current player
   *         is
   */
  public int lead( int p)
  {
    int score = score(p);
    int otherScore = score(otherPlayer(p));
    return score - otherScore;
  }

  /**
   * How many beads on a players half of the board
   * 
   * @param player The player number (1 or 2)
   * @return The number of beads
   */
  public int score( int player)
  {
    int count = 0;
    for (int i = 0; i < 7; i++) // up to including 6
    {
      count += cupCount(player, i);
    }
    return count;
  }

  /**
   * Is the specified cup index the player's end cup
   * 
   * @param player The player (0 or 1)
   * @param cup The cup board index ( 0 to 13)
   * @return
   */
  public boolean isPlayerEndCup( int player, int cup)
  {
    return cup == (player * 7) + 6; // e.g. 13 or 6
  }

  /**
   * The number of beads in a cup for a given player
   * 
   * @param player The player number (1 or 2)
   * @param cup The cup index 0 - 6
   * @return The number of beads in the identified cup
   */
  public int cupCount( int player, int cup)
  {
    return cups[(player * 7) + cup];
  }

  /**
   * The number of beads in a cup.
   * 
   * @param cup
   * @return
   */
  public int count( int cup)
  {
    return cups[cup];
  }

  /**
   * Go around and move players pieces into their end cups...
   */
  public void tidyOnGameOver()
  {
    for (int i = 0; i < 6; i++)
    {
      if (cups[i] > 0)
      {
        int n = cups[i];
        clearCup(i);
        fireUpdate(i);
        cups[6] += n;
        fireUpdate(6);
      }
    }
    for (int i = 7; i < 13; i++)
    {
      if (cups[i] > 0)
      {
        int n = cups[i];
        clearCup(i);
        fireUpdate(i);
        cups[13] += n;
        fireUpdate(13);
      }
    }
  }

  public Object clone()
  {
    Board other = new Board(this.cups);
    other.listeners = this.listeners;
    return other;
  }

  public void addBoardUpdateListener( BoardUpdateListener l)
  {
    listeners.add(l);
  }

  public void removeBoardUpdateListener( BoardUpdateListener l)
  {
    listeners.remove(l);
  }

  protected void fireUpdate( int cup)
  {
    Iterator i = listeners.iterator();
    while (i.hasNext())
    {
      BoardUpdateListener listener = (BoardUpdateListener) i.next();
      listener.cupChanged(this, cup);
    }
  }

  /**
   * Add a bead to a cup at the given index
   * 
   * @param index
   */
  private void addToCup( int index)
  {
    cups[index]++;
  }

  /**
   * Clear the beads in a given cup
   * 
   * @param index
   */
  private void clearCup( int index)
  {
    cups[index] = 0;
  }

  /**
   * The other player
   * 
   * @return
   */
  private int otherPlayer( int p)
  {
    return p == 0 ? 1 : 0;
  }

  /**
   * The index of the other player's end cup
   * 
   * @param player The current player (0 or 1)
   * @return The other players end cup index (on the board)
   */
  private int otherPlayerEndCupIndex( int player)
  {
    if (player == 0) // other == 1
    {
      return 13;
    }
    else
    // other == 0
    {
      return 6;
    }
  }

  /**
   * Is the specified index a normal cup belonging to this player.
   * 
   * @param player The player (0 or 1)
   * @param cup The board index of the cup
   * @return true If the identified cup is a normal cup for the given player
   */
  private boolean isThisPlayerNormalCup( int player, int cup)
  {
    int min = player * 7; // e.g. cup 0 or 7
    int max = (player * 7) + 6; // e.g. cup 6 or 13 (end cups)
    //System.out.println("Normal? cup="+cup+" min="+min+" max="+max);
    return cup >= min && cup < max;
  }

  /**
   * The board index of the end cup of the given player
   * 
   * @param player The player (0 or 1)
   * @return The index of the player
   */
  private int playerEndCup( int player)
  {
    return player * 7 + 6;
  }

  /**
   * -------------->------------------ | | 0 | 1 | 2 | 3 | 4 | 5 | | Player 1
   * |13 |-----------------------| 6 | | |12 |11 |10 | 9 | 8 | 7 | | Player 2
   * -------------- <------------------
   * 
   * cup opposite 3 9 0 12 5 7 11 1
   * 
   * @param cup
   * @return
   */
  private int cupOpposite( int cup)
  {
    // e.g. cup = 10,
    int p = cup > 6 ? 1 : 0; // player = 1
    int normInd = cup - p * 7; // 10 - 7 = 3
    int invInd = 5 - normInd; // 4 - 3 = 2
    int op = p == 0 ? 1 : 0; // other player = 0
    int res = (op * 7) + invInd; // = (0 * 2) + 2 = 2
    //System.out.println("Cup opposite "+cup+"="+res);
    return res;
  }

  /**
   * Dumps the board as an ASII "picture" (See class comment for example)
   * 
   * @see java.lang.Object#toString()
   */
  public String toString( int playerToMove)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("    | 0 | 1 | 2 | 3 | 4 | 5 |    \n");
    sb.append("---------------->----------------\n");
    // player 1 row
    sb.append("|   |");
    for (int i = 0; i < 6; i++)
    {
      sb.append(pad(cups[i]));
      sb.append(" |");
    }
    sb.append("   |  Player 1 " + ((playerToMove == 0) ? "<-" : "") + "\n");
    // Middle row
    sb.append("|");
    sb.append(pad(cups[13]));
    sb.append(" |-----------------------|");
    sb.append(pad(cups[6]));
    sb.append(" |\n");
    // player 2 row
    sb.append("|   |");
    for (int i = 12; i > 6; i--)
    {
      sb.append(pad(cups[i]));
      sb.append(" |");
    }
    sb.append("   |  Player 2 " + ((playerToMove == 1) ? "<-" : "") + "\n");
    sb.append("----------------<----------------\n");
    sb.append("    | 5 | 4 | 3 | 2 | 1 | 0 |    \n");
    if (verbose)
      sb.append("    |12 |11 |10 | 9 | 8 | 7 |    \n");
    if (scores)
    {
      sb.append("Current scores: " + score(0) + " - " + score(1));
    }
    return sb.toString();
  }

  /**
   * Pads a space before a String formatted integer if less than 10
   * 
   * @param i The integer to pad
   * @return The string with padding
   */
  private String pad( int i)
  {
    if (i > 9)
      return "" + i;
    else
      return " " + i;
  }
}