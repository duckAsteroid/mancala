package com.cs.games.mancala.human;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class to receive system input from the player
 * @author <A HREF="mailto:chris.senior@teradyne.com?subject=com.cs.games.mancala.model.human.GameInput">Chris Senior</A>
 */
public class GameInput
{
  private BufferedReader reader;
  
  public GameInput()
  {
    reader = new BufferedReader(new InputStreamReader(System.in));
  }
  /**
   * The next command inputed by user
   * @return
   */
  public String getCommand()
  {
    try
    {
      return reader.readLine();
    }
    catch(IOException ioe)
    {
      return ioe.getMessage();
    }
  }
}
