package bz.util.swing;

import bz.util.java.NOX;
import bz.util.swing.layout.CenterPanel;
import bz.util.swing.layout.ColumnPanel;
import bz.util.swing.layout.RowPanel;
import bz.util.swing.test.TestPanel;
import java.awt.Frame;
import java.util.stream.Stream;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class BzFrame extends JFrame
{
  protected final CenterPanel root=new CenterPanel();;
  
  public BzFrame()
  {
    initComponents();
    setContentPane(root);
    initCustomComponents();
    pack();
  }

  public void setWindowsLookAndFeel()
  {
    Stream.of(UIManager.getInstalledLookAndFeels())
        .filter(info->"Windows".equals(info.getName())) //NOI18N
        .findAny()
        .ifPresent(info->NOX.accept(UIManager::setLookAndFeel, info.getClassName()));
  }

  public void centerOnScreen()
  {
    SwingUtilities.invokeLater(()->setLocationRelativeTo(null));
  }

  public void maximize()
  {
    SwingUtilities.invokeLater(()->setExtendedState(Frame.MAXIMIZED_BOTH));
  }

  public void start()
  {
    SwingUtilities.invokeLater(()->setVisible(true));
  }

  protected void initCustomComponents()
  {
    setWindowsLookAndFeel();
    
    WidthMaintainer labelWidths=new WidthMaintainer();

    RowPanel row0=root.addComponent(new RowPanel(false));

    TestPanel column1=row0.addComponent(new TestPanel());

    ColumnPanel column2=row0.addComponent(new ColumnPanel());

    column2.addLabel("Hello");

    RowPanel row21=column2.addComponent(new RowPanel(false));
    row21.addLabel("Harmadik", o->labelWidths.add(o));
    row21.addComponent(new JTextField("abc")/*, o->DimensionHelper.minimumWidth(o, 200)*/, o->Dimensions.preferredWidth(o, Integer.MAX_VALUE));

    RowPanel row22=column2.addComponent(new RowPanel());
    row22.addLabel("Negyedik", o->labelWidths.add(o));
    row22.addComponent(new JTextField("abc"), o->Dimensions.minimumWidth(o, 200));

    setPreferredSize(Dimensions.of(400, 300));
    centerOnScreen();
    maximize();
  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  public static void main(String args[])
  {
    BzFrame frame=new BzFrame();
    frame.start();
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables
}
