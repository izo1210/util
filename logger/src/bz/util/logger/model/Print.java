package bz.util.logger.model;

public class Print
{
  public boolean timestamp=true;
  public boolean level=true;
  public boolean method=true;
  public boolean objectId=true;
  public boolean threadName=true;
  public boolean event=true;

  public Print()
  {
    timestamp=true;
    level=true;
    method=true;
    objectId=true;
    threadName=true;
    event=true;
  }

  public void minimal()
  {
    timestamp=true;
    level=false;
    method=true;
    objectId=false;
    threadName=false;
    event=false;
  }
}
