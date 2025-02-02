package bz.util.swing.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public interface SimpleLayoutManager extends LayoutManager
{
  @Override
  public default void addLayoutComponent(String name, Component comp)
  {
    layoutContainer(comp.getParent());
  }

  @Override
  public default void removeLayoutComponent(Component comp)
  {
    layoutContainer(comp.getParent());
  }

  @Override
  public default Dimension minimumLayoutSize(Container parent)
  {
    return preferredLayoutSize(parent);
  }


}
