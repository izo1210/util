package bz.util.rx4j;

import java.util.function.Predicate;


public class FilteringObservable<T> extends Observable<T>
{
  private final Predicate<T> predicate;

  public FilteringObservable(Predicate<T> predicate)
  {
    this.predicate=predicate;
  }

  @Override
  public void next(T nextValue)
  {
    if(predicate.test(nextValue))
    {
      super.next(nextValue);
    }
  }

}
