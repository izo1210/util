package bz.util.java;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.NonNull;

/**
 * With the help of this class, methods throwing exception can be used in stream.
 * The thrown exception is re-throwed wrapped in a RuntimeException.
 * Examples:
 *
 * //URI.toURL throws MalformedURLException
 * Stream.of(new URI("")).map(uri->NOX.get(uri::toURL)).collect(Collectors.toList());
 *
 * //Files.size throws IOException
 * Files.list(Path.of(".")).collect(Collectors.summingLong(path->NOX.apply(Files::size, path)));
 *
 * //Files.delete throws IOException
 * Files.list(Path.of(".")).forEach(path->NOX.accept(Files::delete, path));
 *
 * //Files.getLastModifiedTime throws IOException
 * Path oldest=Files.list(Path.of(".")).collect(Collectors.minBy(Comparator.comparing(path->NOX.apply(Files::getLastModifiedTime, path)))).get();
 */
//tested in tms-sds-common/com.siemens.tms.sds.common.utilities.NOXTest
public abstract class NOX
{
  public static class WrappedException extends RuntimeException
  {
    protected WrappedException(@NonNull Throwable cause)
    {
      super(cause.getMessage(), cause);
    }
  }

  public static Throwable unwrap(Throwable maybeWrapped, Set<Class<? extends Throwable>> wrapperClasses)
  {
    if(maybeWrapped==null)
    {
      return null;
    }
    if(wrapperClasses.contains(maybeWrapped.getClass()) && maybeWrapped.getCause()!=null)
    {
      return unwrap(maybeWrapped.getCause());
    }
    else
    {
      return maybeWrapped;
    }
  }

  public static <T extends Throwable> void unwrap(NOX.WrappedException wrapped, Class<T> unwrappedClass) throws T
  {
    Throwable unwrapped=wrapped.getCause();
    if(unwrappedClass.isAssignableFrom(unwrapped.getClass()))
    {
      throw (T)unwrapped;
    }
    else if(unwrapped instanceof RuntimeException)
    {
      throw (RuntimeException)unwrapped;
    }
    else
    {
      throw wrapped;
    }
  }

  public static Throwable unwrap(Throwable maybeWrapped)
  {
    return unwrap(maybeWrapped, Set.of(WrappedException.class));
  }

  public static Function<Throwable, Void> exceptionally(ConsumerThrowsException<Throwable> handler)
  {
    return throwable->{
      Throwable unwrapped=unwrap(throwable, Set.of(WrappedException.class, CompletionException.class));
      accept(handler, unwrapped);
      return null;
    };
  }

  public static <R> R get(Callable<R> supplier)
  {
    if(supplier==null)
    {
      return null;
    }
    try
    {
      return supplier.call();
    }
    catch(Exception e)
    {
      throw new WrappedException(e);
    }
  }

  public static <R> Supplier<R> supplier(Callable<R> callable)
  {
    return ()->NOX.get(callable);
  }

  public interface FunctionThrowsException<P, R>
  {
    R apply(P p) throws Exception;
  }

  public static <P, R> R apply(FunctionThrowsException<P, R> function, P parameter)
  {
    if(function==null)
    {
      return null;
    }
    try
    {
      return function.apply(parameter);
    }
    catch(Exception e)
    {
      throw new WrappedException(e);
    }
  }

  public static <P, R> Function<P, R> function(FunctionThrowsException<P, R> function)
  {
    return parameter->apply(function, parameter);
  }

  public static <P, R> Function<P, R> voidFunction(ConsumerThrowsException<P> consumer)
  {
    return parameter->{accept(consumer, parameter); return null;};
  }

  public interface ConsumerThrowsException<P>
  {
    void accept(P p) throws Exception;
  }

  public static <P> void accept(ConsumerThrowsException<P> consumer, P parameter)
  {
    if(consumer==null)
    {
      return;
    }
    try
    {
      consumer.accept(parameter);
    }
    catch(Exception e)
    {
      throw new WrappedException(e);
    }
  }

  public static <P> Consumer<P> consumer(ConsumerThrowsException<P> consumer)
  {
    return (parameter)->accept(consumer, parameter);
  }

  public interface PredicateThrowsException<T>
  {
    boolean test(T t) throws Exception;
  }

  public static <T> boolean test(PredicateThrowsException<T> predicate, T t)
  {
    try
    {
      return predicate.test(t);
    }
    catch(Exception e)
    {
      throw new WrappedException(e);
    }
  }

  public static <T> Predicate<T> predicate(PredicateThrowsException<T> predicate)
  {
    return (parameter)->test(predicate, parameter);
  }


  public interface RunnableThrowsException
  {
    void run() throws Exception;
  }

  public static void run(RunnableThrowsException runnable)
  {
    if(runnable==null)
    {
      return;
    }
    try
    {
      runnable.run();
    }
    catch(Exception e)
    {
      throw new WrappedException(e);
    }
  }

  public static Runnable runnable(RunnableThrowsException runnable)
  {
    return ()->NOX.run(()->runnable.run());
  }

  public static Callable<Void> toCallable(RunnableThrowsException runnable)
  {
    return ()->{runnable.run(); return null;};
  }

}
