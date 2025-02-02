package bz.util.rx4j.doubles;

import java.util.HashSet;

public class DoubleObservable implements DoubleObserver
{
  protected final HashSet<DoubleSubscription> subscriptions=new HashSet<>();

  public DoubleSubscription subscribe(DoubleObserver observer)
  {
    DoubleSubscription subscription=new DoubleSubscription(this, observer);
    subscriptions.add(subscription);
    return subscription;
  }

  public void unsubscribe(DoubleSubscription subscription)
  {
    subscriptions.remove(subscription);
  }

  @Override
  public void next(double nextValue)
  {
    subscriptions.forEach(subscription->subscription.observer().next(nextValue));
  }

}
