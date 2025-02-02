package bz.util.rx4j.longs;

public record LongSubscription(LongObservable observable, LongObserver observer)
{
  public void unsubscribe()
  {
    observable.unsubscribe(this);
  }
}
