package bz.util.rx4j;

import java.util.Objects;

public class Subject<T> extends Observable<T>
{
  public boolean notifyIfNextUnchanged=true;

  protected T value;
  protected boolean valueIsSet;

  public Subject()
  {
    value=null;
    valueIsSet=false;
  }

  @Override
  public Subscription<T> subscribe(Observer<T> observer)
  {
    if(valueIsSet)
    {
      observer.next(value);
    }
    return super.subscribe(observer);
  }

  @Override
  public void next(T nextValue)
  {
    if(notifyIfNextUnchanged || !Objects.equals(value, nextValue))
    {
      value=nextValue;
      valueIsSet=true;
      super.next(nextValue);
    }
  }

  public T getValue()
  {
    return value;
  }

}
