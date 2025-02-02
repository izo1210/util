package bz.util.rx4j.util;

import bz.util.rx4j.Observable;
import bz.util.rx4j.Observer;
import bz.util.rx4j.Subscription;
import java.lang.reflect.Constructor;

public class CombineLatest<T> extends Observable<T>
{
  private final Class<T> combinedType;
  private final Observable<?>[] sources;
  private final Constructor combineMethod;
  private final Object[] values;
  private Subscription<?>[] sourceSubscriptions;

  /**
   * combinedType should have a constructor that can receive objects from sources. Practically a record.
   */
  public CombineLatest(Class<T> combinedRecordType, Observable<?>... sources)
  {
    this.combinedType=combinedRecordType;
    this.sources=sources;
    combineMethod=getCombineMethod();
    values=new Object[sources.length];
  }

  private Constructor getCombineMethod()
  {
    try
    {
      for(Constructor constructor: combinedType.getDeclaredConstructors())
      {
        if(constructor.getParameterTypes().length==sources.length)
        {
          constructor.setAccessible(true);
          return constructor;
        }
      }
      throw new NoSuchMethodException();
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  private void subscribeToSources()
  {
    sourceSubscriptions=new Subscription<?>[sources.length];
    for(int i=0; i<sources.length; i++)
    {
      Observable<?> source=sources[i];
      sourceSubscriptions[i]=subscribeToOneSource(i, source);
    }
  }
  
  private Subscription<?> subscribeToOneSource(int i, Observable<?> source)
  {
    return source.subscribe(value->{
      values[i]=value;
      setNext();
    });
  }

  private void unsubscribeFromSources()
  {
    for(int i=0; i<sourceSubscriptions.length; i++)
    {
      if(sourceSubscriptions[i]!=null)
      {
        sourceSubscriptions[i].unsubscribe();
        sourceSubscriptions[i]=null;
      }
    }
  }

  private boolean hasNullValue()
  {
    for(int i=0; i<values.length; i++)
    {
      if(values[i]==null) return true;
    }
    return false;
  }

  private void setNext()
  {
    if(hasNullValue()) return;
    try
    {
      Object result=combineMethod.newInstance(values);
      next((T)result);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Subscription<T> subscribe(Observer<T> observer)
  {
    boolean haveToSubscribeToSources=subscriptions.isEmpty();
    Subscription<T> newSubscription=super.subscribe(observer);
    if(haveToSubscribeToSources)
    {
      subscribeToSources();
    }
    return newSubscription;
  }

  @Override
  public void unsubscribe(Subscription<T> subscription)
  {
    super.unsubscribe(subscription);
    if(subscriptions.isEmpty())
    {
      unsubscribeFromSources();
    }
  }

}
