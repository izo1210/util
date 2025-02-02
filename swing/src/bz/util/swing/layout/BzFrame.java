package bz.util.swing.layout;

import bz.util.java.NOX;
import bz.util.java.StackTraceHelper;
import bz.util.swing.util.Dimensions;
import java.awt.Frame;
import java.util.stream.Stream;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public abstract class BzFrame extends JFrame implements Thread.UncaughtExceptionHandler
{
  protected final RootPanel root;
  
  public BzFrame()
  {
    try
    {
      Thread.setDefaultUncaughtExceptionHandler(this);
      setWindowsLookAndFeel();
      root=setRoot();
      setTitle(this.getClass().getSimpleName());
      initComponents();
      setPreferredSize(Dimensions.of(400, 300));
      initCustomComponents();
      pack();
      centerOnScreen();
    }
    catch(RuntimeException e)
    {
      uncaughtException(Thread.currentThread(), e);
      System.exit(1);
      throw e; //this is not going to happen, but needed to relax the compiler about final fields
    }
  }

  public RootPanel setRoot()
  {
    RootPanel newRoot=new RootPanel();
    setContentPane(newRoot);
    return newRoot;
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
    SwingUtilities.invokeLater(()->{
      setVisible(true);
      pack();
    });
  }

  protected abstract void initCustomComponents();

  @Override
  public void uncaughtException(Thread t, Throwable e)
  {
    try
    {
      e=NOX.unwrap(e);
      e.printStackTrace(System.err);
      JOptionPane.showMessageDialog(
          this,
          StackTraceHelper.getFromThrowable(e),
          "Error",
          JOptionPane.ERROR_MESSAGE
      );
    }
    catch(Exception e2)
    {
    }
  }



  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    pack();
  }// </editor-fold>//GEN-END:initComponents
/*
  public static void main(String args[])
  {
    new BzFrame().start();
  }
*/

  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables
}
