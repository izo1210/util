package bz.util.rx4j.longs;

import java.util.HashSet;

public class LongObservable implements LongObserver
{
  protected final HashSet<LongSubscription> subscriptions=new HashSet<>();

  public LongSubscription subscribe(LongObserver observer)
  {
    LongSubscription subscription=new LongSubscription(this, observer);
    subscriptions.add(subscription);
    return subscription;
  }

  public void unsubscribe(LongSubscription subscription)
  {
    subscriptions.remove(subscription);
  }

  @Override
  public void next(long nextValue)
  {
    subscriptions.forEach(subscription->subscription.observer().next(nextValue));
  }

}
