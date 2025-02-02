package bz.util.rx4j.util;

import bz.util.rx4j.BehaviorSubject;

public class TestCombineLatest 
{
  public record AB(String a, String b) {};
  
  public TestCombineLatest()
  {
    BehaviorSubject<String> a=new BehaviorSubject<>("a1");
    BehaviorSubject<String> b=new BehaviorSubject<>("b1");

    CombineLatest<AB> c=new CombineLatest<>(AB.class, a, b);

    c.subscribe(ab->System.out.println(ab));

    a.next("a2");

  }
}
