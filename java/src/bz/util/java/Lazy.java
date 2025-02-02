package bz.util.java;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Lazy<T> implements Future<T>
{
  private final Supplier<T> supplier;
  private T instance;

  @Override
  public synchronized T get()
  {
    if(instance==null)
    {
      instance=supplier.get();
    }
    return instance;
  }

  @Override
  public boolean cancel(boolean mayInterruptIfRunning)
  {
    return false;
  }

  @Override
  public boolean isCancelled()
  {
    return false;
  }

  @Override
  public boolean isDone()
  {
    return instance!=null;
  }

  @Override
  public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
  {
    return get();
  }
}
