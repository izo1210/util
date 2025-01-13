package bz.util.swing.layout;

import bz.util.swing.BzPanel;
import bz.util.swing.Dimensions;
import java.awt.BorderLayout;
import java.awt.Component;

public class CenterPanel extends BzPanel
{
  public CenterPanel()
  {
    init();
  }

  private void init()
  {
    setLayout(new BorderLayout());
    setPreferredSize(Dimensions.largest);
  }

  @Override
  public Component add(Component component)
  {
    removeAll();
    super.add(component, BorderLayout.CENTER);
    return component;
  }

}
