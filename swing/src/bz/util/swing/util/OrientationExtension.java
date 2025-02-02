package bz.util.swing.util;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

public interface OrientationExtension
{
  boolean inRow();

  default boolean alongRow(boolean alongAxis)
  {
    return inRow()==alongAxis;
  }

  default Rectangle rectangle(int startAlongAxis, int startCrossAxis, int lengthAlongAxis, int lengthCrossAxis)
  {
    if(inRow())
    {
      return new Rectangle(startAlongAxis, startCrossAxis, lengthAlongAxis, lengthCrossAxis);
    }
    else
    {
      return new Rectangle(startCrossAxis, startAlongAxis, lengthCrossAxis, lengthAlongAxis);
    }
  }
  
  default Dimension dimension(int lengthAlongAxis, int lengthCrossAxis)
  {
    if(inRow())
    {
      return new Dimension(lengthAlongAxis, lengthCrossAxis);
    }
    else
    {
      return new Dimension(lengthCrossAxis, lengthAlongAxis);
    }
  }

  default Point point(int startAlongAxis, int startCrossAxis)
  {
    if(inRow())
    {
      return new Point(startAlongAxis, startCrossAxis);
    }
    else
    {
      return new Point(startCrossAxis, startAlongAxis);
    }
  }

  default int length(boolean alongAxis, Rectangle source)
  {
    if(alongRow(alongAxis))
    {
      return source.width;
    }
    else
    {
      return source.height;
    }
  }

  default int length(boolean alongAxis, Dimension source)
  {
    if(alongRow(alongAxis))
    {
      return source.width;
    }
    else
    {
      return source.height;
    }
  }

  default int start(boolean alongAxis, Rectangle source)
  {
    if(alongRow(alongAxis))
    {
      return source.x;
    }
    else
    {
      return source.y;
    }
  }

  default int start(boolean alongAxis, Point source)
  {
    if(alongRow(alongAxis))
    {
      return source.x;
    }
    else
    {
      return source.y;
    }
  }

  default Rectangle start(boolean alongAxis, Rectangle target, int start)
  {
    if(alongRow(alongAxis))
    {
      target.x=start;
    }
    else
    {
      target.y=start;
    }
    return target;
  }

  default Point start(boolean alongAxis, Point target, int start)
  {
    if(alongRow(alongAxis))
    {
      target.x=start;
    }
    else
    {
      target.y=start;
    }
    return target;
  }

  default Rectangle length(boolean alongAxis, Rectangle target, int length)
  {
    if(alongRow(alongAxis))
    {
      target.width=length;
    }
    else
    {
      target.height=length;
    }
    return target;
  }

  default Dimension length(boolean alongAxis, Dimension target, int length)
  {
    if(alongRow(alongAxis))
    {
      target.width=length;
    }
    else
    {
      target.height=length;
    }
    return target;
  }

  default int start(boolean alongAxis, Insets insets)
  {
    if(alongRow(alongAxis))
    {
      return insets.left;
    }
    else
    {
      return insets.top;
    }
  }

  default int end(boolean alongAxis, Insets insets)
  {
    if(alongRow(alongAxis))
    {
      return insets.right;
    }
    else
    {
      return insets.bottom;
    }
  }

  default int length(boolean alongAxis, Insets insets)
  {
    if(alongRow(alongAxis))
    {
      return insets.left+insets.right;
    }
    else
    {
      return insets.top+insets.bottom;
    }
  }


}
