package bz.util.swing.rx;

import bz.util.rx4j.BehaviorSubject;
import bz.util.rx4j.Observable;
import bz.util.rx4j.util.MutualSubscribe;
import bz.util.swing.layout.BzFrame;
import bz.util.swing.layout.BzPanel;
import bz.util.swing.util.Dimensions;
import bz.util.swing.util.Listener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

class RxTextFrame extends BzFrame
{
  public static void main(String[] args)
  {
    new RxTextFrame().start();
  }

  private JButton button;
  private JTextField input;
  private JLabel label;
  private BehaviorSubject<String> data;
  private MutualSubscribe inputController;

  @Override
  protected void initCustomComponents()
  {
    data=new BehaviorSubject<>("test");

    BzPanel content=root.addComponent(new BzPanel(l->l.inRow(true).expand(false)));

    button=content.addComponent(new JButton("Reset"));

    input=content.addComponent(new JTextField(), o->Dimensions.preferredWidth(o, 100));

    label=content.addComponent(new JLabel(""));

    Listener.actionPerformed(button, event->data.next("test"));
    
    data.subscribe(text->label.setText(text));
    //data.subscribe(text->System.out.println(text));

    Observable<String> inputObservable=new JTextComponentTextObservable(input, JTextComponentTextObservable.publishWhenFocusLost);
    inputController=new MutualSubscribe(data, inputObservable);

    inputObservable.subscribe(text->System.out.println(text));
  }

}
