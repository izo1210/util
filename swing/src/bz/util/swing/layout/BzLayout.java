package bz.util.swing.layout;

import bz.util.swing.util.OrientationExtension;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(fluent=true, chain=true)
@Data
public class BzLayout implements SimpleLayoutManager, OrientationExtension
{
  public static int defaultGap=4;

  private boolean inRow=false;
  private int gap=-1;
  private boolean beginGap=false;
  private boolean endGap=false;
  private boolean expand=false;
  private double align=0.5;
  private Component stretch=null;

  @Override
  public Dimension preferredLayoutSize(Container container)
  {
    return dimension(
        preferredLengthAlongAxis(container),
        preferredLengthCrossAxis(container)
    );
  }

  private int preferredLengthAlongAxis(Container container)
  {
    if(stretch!=null)
    {
      return length(true, contentBounds(container));
    }
    else
    {
      return componentSumAlongAxis(container);
    }
  }

  private int preferredLengthCrossAxis(Container container)
  {
    if(expand)
    {
      return length(false, contentBounds(container));
    }
    else
    {
      return componentMaxCrossAxis(container);
    }
  }

  @Override
  public void layoutContainer(Container container)
  {
    Dimension preferredSize=preferredLayoutSize(container);
    Rectangle contentBounds=contentBounds(container);
    Insets insets=container.getInsets();
    Rectangle[] boundsArray=createBounds(container.getComponentCount());
    int componentCount=container.getComponentCount();

    setPreferredBounds: {
      int startAlongAxis=beginGap ? actualGap() : 0;
      startAlongAxis+=start(true, insets);
      for(int i=0; i<componentCount; i++)
      {
        Component component=container.getComponent(i);
        Rectangle bounds=boundsArray[i];
        start(true, bounds, startAlongAxis);
        setBounds(component, bounds, length(false, preferredSize), insets);
        startAlongAxis+=length(true, bounds);
        startAlongAxis+=actualGap();
      }
    }

    if(stretch!=null)
    {
      int stretchLength=0;
      for(int i=0; i<componentCount; i++)
      {
        Component component=container.getComponent(i);
        if(stretchLength==0)
        {
          if(component==stretch)
          {
            //set stretchLength
            Rectangle lastBounds=boundsArray[boundsArray.length-1];
            int componentsEndAlongAxis=start(true, lastBounds)+length(true, lastBounds);
            int containerLengthAlongAxis=length(true, contentBounds);
            if(endGap)
            {
              containerLengthAlongAxis-=actualGap();
            }
            containerLengthAlongAxis-=end(true, insets);
            stretchLength=containerLengthAlongAxis-componentsEndAlongAxis;
            //add stetchLength to component's length
            Rectangle bounds=boundsArray[i];
            length(true, bounds, length(true, bounds)+stretchLength);
          }
        }
        else
        {
          //add stretchLength to component's start
          Rectangle bounds=boundsArray[i];
          start(true, bounds, start(true, bounds)+stretchLength);
        }
      }
    }

    applyBounds:
    {
      for(int i=0; i<componentCount; i++)
      {
        Component component=container.getComponent(i);
        Rectangle bounds=boundsArray[i];
        component.setBounds(bounds);
      }
    }
    
  }

  private void setBounds(Component component, Rectangle bounds, int containerLengthCrossAxis, Insets insets)
  {
    /*if(component.getClass().getSimpleName().equals("JLabel"))
    {
      System.out.println(bounds);
    }*/
    length(true, bounds, getLengthAlongAxis(component));
    length(false, bounds, getLengthCrossAxis(component, containerLengthCrossAxis, insets));
    start(false, bounds, getStartCrossAxis(length(false, bounds), containerLengthCrossAxis, insets));
  }

  private int getLengthAlongAxis(Component component)
  {
    return length(true, component.getPreferredSize());
  }

  private int getLengthCrossAxis(Component component, int containerLengthCrossAxis, Insets insets)
  {
    if(expand)
    {
      return containerLengthCrossAxis-start(false, insets)-end(false, insets);
    }
    else
    {
      return length(false, component.getPreferredSize());
    }
  }

  private int getStartCrossAxis(int lengthCrossAxis, int containerLengthCrossAxis, Insets insets)
  {
    int remaining=containerLengthCrossAxis-start(false, insets)-end(false, insets)-lengthCrossAxis;
    int result=(int)(remaining*align)+start(false, insets);
    return result;
  }

  private int componentSumAlongAxis(Container container)
  {
    int result=0;
    int componentCount=container.getComponentCount();
    for(int i=0; i<componentCount; i++)
    {
      Component component=container.getComponent(i);
      int componentValue=length(true, component.getPreferredSize());
      result+=componentValue;
    }
    if(actualGap()>0)
    {
      int gapCount=componentCount-1;
      if(beginGap) gapCount++;
      if(endGap) gapCount++;
      result+=actualGap()*gapCount;
    }

    Insets insets=container.getInsets();
    result+=start(true, insets);
    result+=end(true, insets);

    return result;
  }

  private int componentMaxCrossAxis(Container container)
  {
    int result=0;
    int componentCount=container.getComponentCount();
    for(int i=0; i<componentCount; i++)
    {
      Component component=container.getComponent(i);
      int componentValue=length(false, component.getPreferredSize());
      if(componentValue>result)
      {
        result=componentValue;
      }
    }

    Insets insets=container.getInsets();
    result+=start(false, insets);
    result+=end(false, insets);

    return result;
  }

  private Rectangle[] createBounds(int length)
  {
    Rectangle[] bounds=new Rectangle[length];
    for(int i=0; i<bounds.length; i++)
    {
      bounds[i]=new Rectangle();
    }
    return bounds;
  }

  private Rectangle contentBounds(Container container)
  {
    Rectangle bounds=container.getBounds();
    Insets insets=container.getInsets();
    Rectangle contentBounds=new Rectangle(
        insets.left,
        insets.top, 
        bounds.width-insets.left-insets.right,
        bounds.height-insets.top-insets.bottom);
    return contentBounds;
  }

  @Override
  public boolean inRow()
  {
    return inRow;
  }

  public int actualGap()
  {
    return gap==-1 ? defaultGap : gap;
  }

}
