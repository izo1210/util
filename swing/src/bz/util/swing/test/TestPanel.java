package bz.util.swing.test;

import bz.util.swing.Dimensions;
import bz.util.swing.WidthMaintainer;
import bz.util.swing.layout.ColumnPanel;
import bz.util.swing.layout.RowPanel;
import javax.swing.JTextField;

public class TestPanel extends ColumnPanel
{
  public TestPanel()
  {
    WidthMaintainer labelWidths=new WidthMaintainer();
    ColumnPanel column1=this;
    column1.addLabel("Hello");

    RowPanel row11=column1.addComponent(new RowPanel(false));
    row11.addLabel("Első", o->labelWidths.add(o));
    row11.addComponent(new JTextField("abc")/*, o->DimensionHelper.minimumWidth(o, 200)*/, o->Dimensions.preferredWidth(o, Integer.MAX_VALUE));

    RowPanel row12=column1.addComponent(new RowPanel());
    row12.addLabel("Második", o->labelWidths.add(o));
    row12.addComponent(new JTextField("abc"), o->Dimensions.minimumWidth(o, 200));
    
  }
}
