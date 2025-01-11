package bz.util.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Callable;

public abstract class AbstractSingletonFactory
{
  private final HashMap<String, Object> store=new HashMap<>();
  private final HashSet<String> lock=new HashSet<>();

  protected AbstractSingletonFactory()
  {
  }

  protected <T> T get(Callable<T> supplier)
  {
    if(supplier==null)
    {
      throw new NullPointerException("supplier"); //NOI18N
    }
    synchronized(lock)
    {
      String name=getCallerName();
      lockName(name);
      try
      {
        return doGet(name, supplier);
      }
      finally
      {
        unlockName(name);
      }
    }
  }

  private String getCallerName()
  {
    StackTraceElement[] stackTrace=Thread.currentThread().getStackTrace();
    boolean getMethodIsFound=false;
    for(StackTraceElement element: stackTrace)
    {
      if(getMethodIsFound)
      {
        return element.getMethodName();
      }
      if(element.getClassName().equals(AbstractSingletonFactory.class.getName()) && element.getMethodName().equals("get")) //NOI18N
      {
        getMethodIsFound=true;
      }
    }
    throw new IllegalStateException("Caller name not found"); //NOI18N
  }

  private void lockName(String name)
  {
    if(lock.contains(name))
    {
      throw new IllegalStateException("Circular reference"); //NOI18N
    }
    lock.add(name);
  }

  private void unlockName(String name)
  {
    lock.remove(name);
  }

  private <T> T doGet(String name, Callable<T> supplier)
  {
    if(store.containsKey(name))
    {
      return (T)store.get(name);
    }
    try
    {
      T object=supplier.call();
      store.put(name, object);
      return object;
    }
    catch(RuntimeException e)
    {
      throw e;
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }

}
