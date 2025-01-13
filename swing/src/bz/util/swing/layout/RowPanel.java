package bz.util.swing.layout;

import bz.util.java.InstanceOf;
import bz.util.swing.Dimensions;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

public class RowPanel extends BoxPanel
{
  public RowPanel(boolean placeholderAtTheEnd)
  {
    super(BoxLayout.X_AXIS, placeholderAtTheEnd);
    setPreferredSize(Dimensions.widest);
    InstanceOf.runIfTrue(defaultBorder, EmptyBorder.class, emptyBorder->{
      Insets insets=emptyBorder.getBorderInsets();
      setBorder(BorderFactory.createEmptyBorder(insets.top, 0, insets.bottom, 0));
    });
  }

  public RowPanel()
  {
    this(true);
  }

}
