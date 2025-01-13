package bz.util.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Arrays;
import java.util.function.Consumer;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BzPanel extends JPanel
{
  public <C extends Component> C addComponent(C newComponent, Consumer<C>... settings)
  {
    Arrays.stream(settings).forEach(setting->setting.accept(newComponent));
    add(newComponent);
    return newComponent;
  }

  public JLabel addLabel(String text, Consumer<JLabel>... settings)
  {
    JLabel o=new JLabel(text);
    Arrays.stream(settings).forEach(setting->setting.accept(o));

    JPanel p=new JPanel();
    p.setLayout(new BorderLayout());

    p.add(o, BorderLayout.CENTER);
    add(p);
    return o;
  }
}
