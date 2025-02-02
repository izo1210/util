package bz.util.rx4j.bytes;

public class ByteBehaviorSubject extends ByteObservable
{
  public boolean notifyIfNextUnchanged=true;

  private final byte initialValue;
  private byte value;

  public ByteBehaviorSubject(byte initialValue)
  {
    this.initialValue=initialValue;
    value=initialValue;
  }

  @Override
  public ByteSubscription subscribe(ByteObserver observer)
  {
    observer.next(value);
    return super.subscribe(observer);
  }

  @Override
  public void next(byte nextValue)
  {
    if(notifyIfNextUnchanged || value!=nextValue)
    {
      value=nextValue;
      super.next(nextValue);
    }
  }

  public byte getValue()
  {
    return value;
  }

  public void reset()
  {
    next(initialValue);
  }

}
