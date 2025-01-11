package bz.util.logger.model;

import java.util.ArrayList;
import java.util.List;
import bz.util.logger.model.Record;

public class FilterChain extends ArrayList<Filter>
{
  public FilterChain()
  {

  }

  public FilterChain(List<Filter> filters)
  {
    super(filters);
  }

  public boolean evaluate(Record record)
  {
    return stream().allMatch(filter->filter.evaluate(record));
  }

}
