package com.cs.games.mancala.ui;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class Cup extends Canvas
{
  /**
   * Create the composite
   * @param parent
   * @param style
   */
  public Cup(Composite parent, int style)
  {
    super(parent, style);
    this.addPaintListener(new PaintListener()
    {
      public void paintControl( PaintEvent e)
      {
        doPaint(e.gc);
      }
    });
  }

  protected void doPaint( GC gc)
  {
    // TODO Auto-generated method stub
    
  }
}
