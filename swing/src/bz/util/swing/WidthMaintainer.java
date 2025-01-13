package bz.util.swing;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashSet;
import javax.swing.SwingUtilities;

public class WidthMaintainer extends ComponentAdapter
{
  private final HashSet<Component> components=new HashSet<>();

  public void add(Component component)
  {
    components.add(component);

    component.addComponentListener(this);
  }

  public void remove(Component component)
  {
    if(components.remove(component))
    {
      component.removeComponentListener(this);
    }
  }

  public void clear()
  {
    components.forEach(component->remove(component));
  }

  public void setToMaximum()
  {
    SwingUtilities.invokeLater(()->{
        int max=components.stream()
            .mapToInt(component->component.getWidth())
            .max()
            .orElse(0);

        components.forEach(component->{
          Dimensions.allWidth(component, max);
          component.revalidate();
        });
    });
  }

  @Override
  public void componentResized(ComponentEvent e)
  {
    setToMaximum();
  }
}
