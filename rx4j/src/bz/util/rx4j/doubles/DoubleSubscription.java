package bz.util.rx4j.doubles;

public record DoubleSubscription(DoubleObservable observable, DoubleObserver observer)
{
  public void unsubscribe()
  {
    observable.unsubscribe(this);
  }
}
