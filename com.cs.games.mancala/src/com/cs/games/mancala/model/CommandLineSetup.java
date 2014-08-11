package com.cs.games.mancala.model;

import com.cs.games.mancala.ai.ComputerPlayer;
import com.cs.games.mancala.ai.RandomPlayer;
import com.cs.games.mancala.human.GameInput;
import com.cs.games.mancala.human.HumanPlayer;

/**
 * @author SeniorC
 */
public class CommandLineSetup
{
  public GameInput in = new GameInput();
  public Player choosePlayer(int pNum)
  {
    Player player = null;
    while(player == null)
    {
      System.out.println("Please select a player type for player "+(pNum+1)+":");
          System.out.println("\t(R)andom player");
          System.out.println("\t(C)omputer player");
          System.out.println("\t(H)uman player");
      String choice = in.getCommand();
      if (choice.equalsIgnoreCase("R"))
      {
        player = new RandomPlayer(pNum);
      }
      else if (choice.equalsIgnoreCase("C"))
      {
        player = new ComputerPlayer(pNum);
      }
      else if (choice.equalsIgnoreCase("H"))
      {
        player = new HumanPlayer(pNum, in);
      }
    }
    return player;
  }

}
