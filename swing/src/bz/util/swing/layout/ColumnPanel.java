package bz.util.swing.layout;

import bz.util.java.InstanceOf;
import bz.util.swing.Dimensions;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

public class ColumnPanel extends BoxPanel
{
  public ColumnPanel(boolean placeholderAtTheEnd)
  {
    super(BoxLayout.Y_AXIS, placeholderAtTheEnd);
    setPreferredSize(Dimensions.tallest);
    InstanceOf.runIfTrue(defaultBorder, EmptyBorder.class, emptyBorder->{
      Insets insets=emptyBorder.getBorderInsets();
      setBorder(BorderFactory.createEmptyBorder(0, insets.left, 0, insets.right));
    });
  }

  public ColumnPanel()
  {
    this(true);
  }

}
