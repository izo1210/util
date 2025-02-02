package bz.util.rx4j.bytes;

public record ByteSubscription(ByteObservable observable, ByteObserver observer)
{
  public void unsubscribe()
  {
    observable.unsubscribe(this);
  }
}
