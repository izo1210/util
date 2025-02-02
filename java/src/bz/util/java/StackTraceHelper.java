package bz.util.java;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StackTraceHelper
{
  private static final SimpleDateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); //NOI18N

  public static Stream<StackTraceElement> streamCurrent(int skip)
  {
    Thread thread=Thread.currentThread();
    StackTraceElement[] stackTrace=thread.getStackTrace();
    int startIndex=getStartIndex(stackTrace)+skip;
    return Arrays.stream(stackTrace, startIndex, stackTrace.length);
  }

  public static void printCurrent(Consumer<String> lineConsumer)
  {
    synchronized(dateFormatter)
    {
      String formattedDate=dateFormatter.format(new Date());
      lineConsumer.accept(formattedDate+" "+Thread.currentThread()); //NOI18N
      streamCurrent(0).forEach(ste->lineConsumer.accept(" at "+ste)); //NOI18N
    }
  }

  public static void printCurrent()
  {
    synchronized(System.out)
    {
      printCurrent(System.out::println);
    }
  }

  public static String getCurrent()
  {
    List<String> lines=new LinkedList<>();
    StackTraceHelper.printCurrent(lines::add);
    return lines.stream().collect(Collectors.joining(System.lineSeparator()));
  }

  private static int getStartIndex(StackTraceElement[] stackTrace)
  {
    String thisClassName=StackTraceHelper.class.getName();
    boolean thisClassReached=false;
    for(int startIndex=0; startIndex<stackTrace.length; startIndex++)
    {
      StackTraceElement e=stackTrace[startIndex];
      if(!thisClassReached && e.getClassName().equals(thisClassName))
      {
        thisClassReached=true;
        continue;
      }

      if(thisClassReached && !e.getClassName().equals(thisClassName))
      {
        return startIndex;
      }
    }
    return 0;
  }

  public static String getFromThrowable(Throwable e)
  {
    StringWriter sw=new StringWriter();
    PrintWriter pw=new PrintWriter(sw);
    e.printStackTrace(pw);
    return sw.toString();
  }
}
