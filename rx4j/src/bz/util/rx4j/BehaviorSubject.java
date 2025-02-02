package bz.util.rx4j;

public class BehaviorSubject<T> extends Subject<T>
{
  private final T initialValue;

  public BehaviorSubject(T initialValue)
  {
    this.initialValue=initialValue;
    value=initialValue;
    valueIsSet=true;
  }

  public BehaviorSubject()
  {
    this(null);
  }

  public void reset()
  {
    next(initialValue);
  }

}
