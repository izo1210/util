package bz.util.swing.test;

import bz.util.logger.LogExtension;
import bz.util.swing.layout.BzFrame;
import bz.util.swing.layout.BzPanel;
import bz.util.swing.util.Listener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

public class TestFrame2 extends BzFrame implements LogExtension
{
  static boolean opaque=true;
  public static final TestFrame2 instance=new TestFrame2();

  public static void main(String[] args)
  {
    instance.start();
  }

  public BzPanel main;
  public BzPanel column;
  public BzPanel row;
  public JLabel label;
  public JButton button;
  public Listener<ActionListener> buttonActionListener;

  @Override
  protected void initCustomComponents()
  {
    root.setOpaque(opaque);
    root.setBackground(Color.black);

    main=root.addComponent(new BzPanel(layout->layout.inRow(false)), o->o.setOpaque(opaque), o->o.setBackground(Color.red));

    column=main.addComponent(new BzPanel(layout->layout.inRow(false)), o->o.setOpaque(opaque), o->o.setBackground(Color.orange));

    row=column.addComponent(new BzPanel(layout->layout.inRow(true)), o->o.setOpaque(opaque), o->o.setBackground(Color.yellow));

    button=row.addComponent(new JButton("click"));
    buttonActionListener=Listener.actionPerformed(button, Listener.filter(ActionEvent.CTRL_MASK, e->buttonactionPerformed()));
    label=row.addComponent(new JLabel("abc"), o->o.setOpaque(opaque), o->o.setBackground(Color.green));

  }

  void buttonactionPerformed()
  {
    row.removeAll();
    button=row.addComponent(new JButton("click"));
    buttonActionListener.removeFromAll();
    buttonActionListener.set(event->buttonactionPerformed2());
    buttonActionListener.addTo(button);
    label=row.addComponent(new JLabel("abcdefgh"), o->o.setOpaque(opaque), o->o.setBackground(Color.green));
    root.validateBranch(row);
  }

  void buttonactionPerformed2()
  {
    row.removeAll();
    button=row.addComponent(new JButton("click"));
    buttonActionListener.removeFromAll();
    buttonActionListener.set(event->buttonactionPerformed());
    buttonActionListener.addTo(button);
    label=row.addComponent(new JLabel("abc"), o->o.setOpaque(opaque), o->o.setBackground(Color.green));
    root.validateBranch(row);
  }

}
