package com.cs.games.mancala.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Application
{
  protected Shell shell;

  /**
   * Launch the application
   * @param args
   */
  public static void main( String[] args)
  {
    try
    {
      Application window = new Application();
      window.open();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Open the window
   */
  public void open()
  {
    final Display display = Display.getDefault();
    createContents();
    shell.open();
    shell.layout();
    while (!shell.isDisposed())
    {
      if (!display.readAndDispatch())
        display.sleep();
    }
  }

  /**
   * Create contents of the window
   */
  protected void createContents()
  {
    shell = new Shell();
    shell.setSize(500, 375);
    shell.setText("Mancala");
    //
  }
}
