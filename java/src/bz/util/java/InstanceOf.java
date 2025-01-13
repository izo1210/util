package bz.util.java;

import java.util.function.Consumer;

public class InstanceOf<T>
{
  public static <T> T runIfTrue(Object object, Class<T> type, Consumer<T> runIfTrue)
  {
    if(object==null) return null;
    
    if(type.isAssignableFrom(object.getClass()))
    {
      if(runIfTrue!=null) runIfTrue.accept((T)object);
      return (T)object;
    }

    return null;
  }
}
