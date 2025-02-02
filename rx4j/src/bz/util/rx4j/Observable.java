package bz.util.rx4j;

import java.util.HashSet;
import java.util.function.Function;

public class Observable<T> implements Observer<T>
{
  protected final HashSet<Subscription<T>> subscriptions=new HashSet<>();

  public Subscription<T> subscribe(Observer<T> observer)
  {
    Subscription<T> subscription=new Subscription<>(this, observer);
    subscriptions.add(subscription);
    return subscription;
  }

  public void unsubscribe(Subscription<T> subscription)
  {
    subscriptions.remove(subscription);
  }

  @Override
  public void next(T nextValue)
  {
    subscriptions.forEach(subscription->subscription.observer().next(nextValue));
  }

  /* Start pipe methods */
  public <A> Observable<A> pipe(Function<Observable<T>, Observable<A>> functionTA)
  {
    return functionTA.apply(this);
  }

  public <A, B> Observable<B> pipe(
      Function<Observable<T>, Observable<A>> functionTA,
      Function<Observable<A>, Observable<B>> functionAB
  )
  {
    return
        functionAB.apply(
        functionTA.apply(this)
    );
  }
  /* End pipe methods */

}
