package bz.util.logger.formatters;

import bz.util.logger.model.Formatter;
import bz.util.logger.model.Record;

public class PlainFormatter extends Formatter
{
  public String logSeparator=System.lineSeparator();
  public String logEnd="--------------------------------------------------------------------------------";

  @Override
  public String format(Record record)
  {
    StringBuilder sb=new StringBuilder();
    if(print.timestamp)
    {
      sb.append(" timest = ").append(timestampFormatter.format(record.timestamp())).append(logSeparator);
    }
    if(print.threadName)
    {
      sb.append(" thread = ").append(record.threadName()).append(logSeparator);
    }
    if(print.level)
    {
      sb.append(" level  = ").append(record.level()).append(logSeparator);
    }
    if(print.method)
    {
      sb.append(" method = ").append(methodString(record.objectClass(), record.stackTraceElement())).append(logSeparator);
    }
    if(print.event)
    {
      sb.append(" event  = ").append(record.event()).append(logSeparator);
    }
    if(print.objectId)
    {
      sb.append(" object = ").append(record.objectId()).append(logSeparator);
    }
    if(record.values()!=null && !record.values().isEmpty())
    {
      sb.append(" values = ").append(record.values()).append(logSeparator);
    }
    if(logEnd!=null)
    {
      sb.append(logEnd);
    }
    return sb.toString();
  }

  private String methodString(Class<?> objectClass, StackTraceElement ste)
  {
    return objectClass.getSimpleName()+"."+ste.getMethodName()+" ("+ste.getFileName()+":"+ste.getLineNumber()+")";
    //return ste.toString().replace(":", ";"); //NetBeans System.out error: doesn't print stack trace element
  }

}
