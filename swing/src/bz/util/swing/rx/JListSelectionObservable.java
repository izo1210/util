package bz.util.swing.rx;

import bz.util.swing.util.Listener;
import java.util.EventObject;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.JList;
import javax.swing.ListModel;

public class JListSelectionObservable<T> extends InputObservable<JList, T>
{

  public JListSelectionObservable(JList input)
  {
    super(input,
        JListSelectionObservable::listSelectionListener,
        JListSelectionObservable::getSelectedElement,
        JListSelectionObservable::setSelectedElement
    );
  }

  private static Listener listSelectionListener(Object eventSource, Consumer<EventObject> handler)
  {
    return Listener.valueChanged(eventSource, event->handler.accept(event));
  }

  public static <T1> T1 getSelectedElement(EventObject event)
  {
    JList list=(JList)event.getSource();
    ListModel model=list.getModel();
    int index=list.getLeadSelectionIndex();
    if(index==-1 || index>=model.getSize()) return null;
    T1 newValue=(T1)model.getElementAt(index);
    return newValue;
  }

  public static void setSelectedElement(JList list, Object element)
  {
    ListModel model=list.getModel();
    for(int i=0; i<model.getSize(); i++)
    {
      Object modelElement=model.getElementAt(i);
      if(Objects.equals(element, modelElement))
      {
        list.setSelectedIndex(i);
        return;
      }
    }
    list.setSelectedIndex(-1);
  }

}
