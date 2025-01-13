package bz.util.swing.layout;

import bz.util.java.InstanceOf;
import bz.util.swing.BzPanel;
import bz.util.swing.Dimensions;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class BoxPanel extends BzPanel
{
  protected JPanel placeholder;

  public Border defaultBorder=BorderFactory.createEmptyBorder(4, 4, 4, 4);

  public BoxPanel(int axis, boolean placeholderAtTheEnd)
  {
    init(axis, placeholderAtTheEnd);
  }

  private void init(int axis, boolean placeholderAtTheEnd)
  {
    setLayout(new BoxLayout(this, axis));
    if(placeholderAtTheEnd) addPlaceholderAtTheEnd();
  }

  @Override
  public Component add(Component component)
  {
    setDefaultBorderOn(component);
    int offset=(placeholder==null ? 0 : -1);
    return super.add(component, getComponentCount()+offset);
  }

  private void setDefaultBorderOn(Component component)
  {
    InstanceOf.runIfTrue(component, JComponent.class, jc->{
      if(defaultBorder==null) return;

      if(jc.getBorder()==null)
      {
        jc.setBorder(defaultBorder);
      }
    });
  }

  public void addPlaceholderAtTheEnd()
  {
    if(placeholder!=null) return;

    placeholder=new JPanel();
    placeholder.setMinimumSize(Dimensions.zero);
    placeholder.setMaximumSize(Dimensions.largest);
    placeholder.setPreferredSize(Dimensions.largest);
    super.add(placeholder);
  }

  public void removePlaceholderAtTheEnd()
  {
    if(placeholder==null) return;

    remove(getComponentCount()-1);

    placeholder=null;
  }

  public JPanel placeholder()
  {
    return placeholder;
  }

}
