package bz.util.rx4j;

public interface Observer<T>
{
  void next(T nextValue);
}
