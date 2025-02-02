package bz.util.swing.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JComponent;

public class Dimensions
{
  public static Dimension zero=new Dimension(0, 0);
  public static Dimension largest=new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
  public static Dimension widest=new Dimension(Integer.MAX_VALUE, 0);
  public static Dimension tallest=new Dimension(0, Integer.MAX_VALUE);
  public static final Insets emptyInsets=new Insets(0, 0, 0, 0);

  public static Dimension changeWidth(Dimension original, int newWidth)
  {
    return new Dimension(newWidth, original.height);
  }

  public static Dimension maxWidth(Dimension original)
  {
    return new Dimension(Integer.MAX_VALUE, original.height);
  }

  public static Dimension maxWidth(int height)
  {
    return new Dimension(Integer.MAX_VALUE, height);
  }

  public static Dimension changeHeight(Dimension original, int newHeight)
  {
    return new Dimension(original.width, newHeight);
  }

  public static Dimension maxHeight(Dimension original)
  {
    return new Dimension(original.width, Integer.MAX_VALUE);
  }

  public static Dimension maxHeight(int width)
  {
    return new Dimension(width, Integer.MAX_VALUE);
  }

  public static Dimension of(int width, int height)
  {
    return new Dimension(width, height);
  }
  
  public static void minimumWidth(Component component, int newValue)
  {
    component.setMinimumSize(changeWidth(component.getMinimumSize(), newValue));
  }

  public static void minimumHeight(Component component, int newValue)
  {
    component.setMinimumSize(changeHeight(component.getMinimumSize(), newValue));
  }

  public static void preferredWidth(Component component, int newValue)
  {
    component.setPreferredSize(changeWidth(component.getPreferredSize(), newValue));
  }

  public static void preferredHeight(Component component, int newValue)
  {
    component.setPreferredSize(changeHeight(component.getPreferredSize(), newValue));
  }

  public static void maximumWidth(Component component, int newValue)
  {
    component.setMaximumSize(changeWidth(component.getMaximumSize(), newValue));
  }

  public static void maximumHeight(Component component, int newValue)
  {
    component.setMaximumSize(changeHeight(component.getMaximumSize(), newValue));
  }

  public static void allWidth(Component component, int newValue)
  {
    minimumWidth(component, newValue);
    preferredWidth(component, newValue);
    maximumWidth(component, newValue);
  }

  public static void allHeight(Component component, int newValue)
  {
    minimumHeight(component, newValue);
    preferredHeight(component, newValue);
    maximumHeight(component, newValue);
  }

  public static void allDimensions(Component component, Dimension newValue)
  {
    component.setMinimumSize(newValue);
    component.setPreferredSize(newValue);
    component.setMaximumSize(newValue);
  }

  public static Insets componentInsets(Component component)
  {
    Insets insets;
    if(component instanceof JComponent)
    {
      JComponent jComponent=(JComponent)component;
      insets=jComponent.getInsets();
    }
    else
    {
      insets=emptyInsets;
    }
    return insets;
  }

  public static Dimension grossSize(Dimension netSize, Insets insets)
  {
    Dimension grossSize=new Dimension(
        netSize.width+insets.left+insets.right,
        netSize.height+insets.top+insets.bottom
    );
    return grossSize;
  }

  public static Dimension grossSize(Component component)
  {
    return grossSize(component.getPreferredSize(), componentInsets(component));
  }




}
