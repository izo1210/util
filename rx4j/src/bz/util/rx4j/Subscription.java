package bz.util.rx4j;

public record Subscription<T>(Observable<T> observable, Observer<T> observer)
{
  public void unsubscribe()
  {
    observable.unsubscribe(this);
  }
}
