package bz.util.rx4j;

import java.util.function.Function;
import java.util.function.Predicate;

public class Rx 
{
  public static <A, B> Function<Observable<A>, Observable<B>> map(Function<A, B> functionAB)
  {
    return observableA->{
      Observable<B> observableB=new Observable<>();
      observableA.subscribe(nextA->functionAB.apply(nextA));
      return observableB;
    };
  }

  public static <A> Function<Observable<A>, Observable<A>> filter(Predicate<A> predicate)
  {
    return observableA->{
      Observable<A> observableB=new FilteringObservable<>(predicate);
      observableA.subscribe(nextA->observableB.next(nextA));
      return observableB;
    };
  }

}
