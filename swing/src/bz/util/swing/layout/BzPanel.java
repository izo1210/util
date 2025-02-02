package bz.util.swing.layout;

import bz.util.swing.util.ContainerExtension;
import java.awt.LayoutManager;
import java.util.function.Consumer;
import javax.swing.JPanel;

public class BzPanel extends JPanel implements ContainerExtension
{
  public BzPanel(BzLayout layout)
  {
    super(layout);
  }

  public BzPanel()
  {
    this(new BzLayout());
  }

  public BzPanel(Consumer<BzLayout> layoutAdjuster)
  {
    this();
    layoutAdjuster.accept(getLayout());
  }

  @Override
  public final BzLayout getLayout()
  {
    return (BzLayout)super.getLayout();
  }

  @Override
  public void setLayout(LayoutManager bzLayout)
  {
    if(!(bzLayout instanceof BzLayout)) throw new IllegalArgumentException("Must be instance of BzLayout"); //NOI18N
    super.setLayout(bzLayout);
  }

}
