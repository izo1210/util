package bz.util.swing.util;

import java.awt.Component;
import java.awt.Container;
import java.util.Arrays;
import java.util.function.Consumer;

public interface ContainerExtension
{
  Component add(Component c);
  Container getParent();

  default <C extends Component> C addComponent(C newComponent, Consumer<C>... settings)
  {
    Arrays.stream(settings).forEach(setting->setting.accept(newComponent));
    add(newComponent);

    return newComponent;
  }

  default void validateBranch(Container leafContainer)
  {
    for(Container parent=leafContainer; parent!=null; parent=parent.getParent())
    {
      parent.validate();
    }
  }

}
