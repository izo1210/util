package bz.util.rx4j.util;

import bz.util.rx4j.Observable;
import bz.util.rx4j.Subscription;
import java.util.concurrent.Semaphore;

public class MutualSubscribe<T>
{
  private final Observable<T> a;
  private final Observable<T> b;
  private final Subscription<?> aSubscription;
  private final Subscription<?> bSubscription;
  private final Semaphore changeSemaphore=new Semaphore(1);

  public MutualSubscribe(Observable<T> a, Observable<T> b)
  {
    this.a=a;
    this.b=b;
    aSubscription=a.subscribe(this::onChangeA);
    bSubscription=b.subscribe(this::onChangeB);
  }

  public void unsubscribe()
  {
    aSubscription.unsubscribe();
    bSubscription.unsubscribe();
  }

  private void onChangeA(T newValue)
  {
    if(!changeSemaphore.tryAcquire()) return;
    try
    {
      b.next(newValue);
    }
    finally
    {
      changeSemaphore.release();
    }
  }

  private void onChangeB(T newValue)
  {
    if(!changeSemaphore.tryAcquire()) return;
    try
    {
      a.next(newValue);
    }
    finally
    {
      changeSemaphore.release();
    }
  }


}
