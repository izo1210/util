package bz.util.logger.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import bz.util.logger.LogExtension;

public record Record
(
  Instant timestamp,
  String threadName,
  Event event,
  StackTraceElement stackTraceElement,
  Class<?> objectClass,
  String objectId,
  Map<Object, Object> values,
  int level
)
{
  public Record(
      LogExtension logSource,
      Event event,
      Object[] keysAndValuesAndMaybeLevel
  )
  {
    this(
      logSource.logger().timestampProvider.timestamp(),
      Thread.currentThread().getName(),
      event,
      buildStackTraceElement(),
      logSource.getClass(),
      logSource.objectId(),
      buildValues(keysAndValuesAndMaybeLevel),
      buildLevel(keysAndValuesAndMaybeLevel)
    );
  }

  private static Map<Object, Object> buildValues(Object[] keysAndValuesAnyMaybeLevel)
  {
    var values=new HashMap<>();
    for(int i=0; i<keysAndValuesAnyMaybeLevel.length-1; i+=2)
    {
      values.put(keysAndValuesAnyMaybeLevel[i], keysAndValuesAnyMaybeLevel[i+1]);
    }
    if(keysAndValuesAnyMaybeLevel.length==1) //write something even if only one value is passed
    {
      Object value=keysAndValuesAnyMaybeLevel[0];
      if(!(value instanceof Integer)) //maybe level
      {
        values.put("", value);
      }
    }
    return values;
  }

  public static int buildLevel(Object[] keysAndValuesAndMaybeLevel)
  {
    if(keysAndValuesAndMaybeLevel.length%2==0) return LogExtension.INFO; //no level provided

    Object maybeLevel=keysAndValuesAndMaybeLevel[keysAndValuesAndMaybeLevel.length-1];

    try
    {
      return (Integer)maybeLevel;
    }
    catch(Exception e)
    {
      return LogExtension.INFO;
    }
  }

  private static final String logInterfaceFileName=LogExtension.class.getSimpleName()+".java";

  private static StackTraceElement buildStackTraceElement()
  {
    StackTraceElement[] stackTrace=Thread.currentThread().getStackTrace();
    //System.err.println(Arrays.asList(stackTrace));

    boolean logMethodFound=false;
    for(StackTraceElement ste: stackTrace)
    {
      if(logMethodFound)
        return ste;

      if(ste.getFileName().equals(logInterfaceFileName) && ste.getMethodName().startsWith("log"))
        logMethodFound=true;
    }
    return stackTrace[stackTrace.length-1];
  }

}
