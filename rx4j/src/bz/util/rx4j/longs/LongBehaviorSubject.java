package bz.util.rx4j.longs;

public class LongBehaviorSubject extends LongObservable
{
  public boolean notifyIfNextUnchanged=true;

  private final long initialValue;
  private long value;

  public LongBehaviorSubject(long initialValue)
  {
    this.initialValue=initialValue;
    value=initialValue;
  }

  @Override
  public LongSubscription subscribe(LongObserver observer)
  {
    observer.next(value);
    return super.subscribe(observer);
  }

  @Override
  public void next(long nextValue)
  {
    if(notifyIfNextUnchanged || value!=nextValue)
    {
      value=nextValue;
      super.next(nextValue);
    }
  }

  public long getValue()
  {
    return value;
  }

  public void reset()
  {
    next(initialValue);
  }

}
