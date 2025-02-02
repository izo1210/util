package bz.util.rx4j.doubles;

public class DoubleBehaviorSubject extends DoubleObservable
{
  public boolean notifyIfNextUnchanged=true;

  private final double initialValue;
  private double value;

  public DoubleBehaviorSubject(double initialValue)
  {
    this.initialValue=initialValue;
    value=initialValue;
  }

  @Override
  public DoubleSubscription subscribe(DoubleObserver observer)
  {
    observer.next(value);
    return super.subscribe(observer);
  }

  @Override
  public void next(double nextValue)
  {
    if(notifyIfNextUnchanged || value!=nextValue)
    {
      value=nextValue;
      super.next(nextValue);
    }
  }

  public double getValue()
  {
    return value;
  }

  public void reset()
  {
    next(initialValue);
  }

}
