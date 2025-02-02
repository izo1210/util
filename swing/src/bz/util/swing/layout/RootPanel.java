package bz.util.swing.layout;

import java.awt.Component;

public class RootPanel extends BzPanel
{
  public RootPanel()
  {
    getLayout().inRow(false).expand(true).gap(0);
  }

  @Override
  public Component add(Component component)
  {
    removeAll();
    getLayout().stretch(component);
    super.add(component);
    return component;
  }

}
