package bz.util.swing.rx;

import bz.util.rx4j.Observable;
import bz.util.swing.util.Listener;
import java.util.EventObject;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class InputObservable<I, O> extends Observable<O>
{
  //Object: event source, Consumer: event handler, Listener: created listener
  public interface ListenerFactory extends BiFunction<Object, Consumer<EventObject>, Listener> {}

  public static final ListenerFactory publishWhenFocusLost=(input, handler)->Listener.focusLost(input, event->handler.accept(event));

  protected final I input;
  protected final ListenerFactory publishWhen;
  protected final Function<EventObject, O> valueGetter;
  protected final BiConsumer<I, O> valueSetter;
  private Listener<?> viewSubscription;

  public InputObservable(I input, ListenerFactory publishWhen, Function<EventObject, O> valueGetter, BiConsumer<I, O> valueSetter)
  {
    this.input=input;
    this.publishWhen=publishWhen;
    this.valueGetter=valueGetter;
    this.valueSetter=valueSetter;
    viewSubscription=publishWhen.apply(input, this::onInputChange);
  }

  public void unsubscribe()
  {
    if(viewSubscription!=null)
    {
      viewSubscription.removeFromAll();
      viewSubscription=null;
    }
  }

  @Override
  public void next(O nextValue)
  {
    valueSetter.accept(input, nextValue);
  }

  private void onInputChange(EventObject event)
  {
    O nextValue=valueGetter.apply(event);
    super.next(nextValue);
  }



}
