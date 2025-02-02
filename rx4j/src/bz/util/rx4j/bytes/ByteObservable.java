package bz.util.rx4j.bytes;

import java.util.HashSet;

public class ByteObservable implements ByteObserver
{
  protected final HashSet<ByteSubscription> subscriptions=new HashSet<>();

  public ByteSubscription subscribe(ByteObserver observer)
  {
    ByteSubscription subscription=new ByteSubscription(this, observer);
    subscriptions.add(subscription);
    return subscription;
  }

  public void unsubscribe(ByteSubscription subscription)
  {
    subscriptions.remove(subscription);
  }

  @Override
  public void next(byte nextValue)
  {
    subscriptions.forEach(subscription->subscription.observer().next(nextValue));
  }

}
