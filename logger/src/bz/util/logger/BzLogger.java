package bz.util.logger;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import bz.util.logger.appenders.SystemOutAppender;
import bz.util.logger.formatters.PlainFormatter;
import bz.util.logger.model.Appender;
import bz.util.logger.model.Filter;
import bz.util.logger.model.FilterChain;
import bz.util.logger.model.Formatter;
import bz.util.logger.model.Record;
import bz.util.logger.model.Rule;
import bz.util.logger.model.TimestampProvider;
import bz.util.logger.timestampproviders.RealtimeTimestampProvider;

/**
 * Don't use this class directly for logging.
 * Instead implement LogInterface in the class where you want to log, and call its log method.
 * Use SiLogger.defaultLogger to modify logging properties.
 */
public class BzLogger
{
  public static final String DEFAULT="DEFAULT";

  public static BzLogger defaultLogger=defaultLogger();
  private static BzLogger defaultLogger()
  {
    return new BzLogger();
  }

  public TimestampProvider timestampProvider=defaultTimestampProvider();
  protected TimestampProvider defaultTimestampProvider()
  {
    return new RealtimeTimestampProvider();
  }

  /**
   * Convenience collection to store filters.
   * The filters are not needed to add this map.
   * A filter takes effect only when added to a filter chain.
   */
  public Map<Object, Filter> filters=defaultFilters();
  protected Map<Object, Filter> defaultFilters()
  {
    return new HashMap<>();
  }

  public Map<Object, FilterChain> filterChains=defaultFilterChains();
  protected Map<Object, FilterChain> defaultFilterChains()
  {
    return new HashMap<>(Map.of(
        DEFAULT, new FilterChain()
    ));
  }

  public Map<Object, Formatter> formatters=defaultFormatters();
  protected Map<Object, Formatter> defaultFormatters()
  {
    return new HashMap<>(Map.of(DEFAULT, new PlainFormatter()
    ));
  }

  public final Map<Object, Appender> appenders=defaultAppenders();
  protected Map<Object, Appender> defaultAppenders()
  {
    return new HashMap<>(Map.of(
        DEFAULT, new SystemOutAppender()
    ));
  }

  public Map<Object, Rule> rules=defaultRules();
  protected Map<Object, Rule> defaultRules()
  {
    return new HashMap<>(Map.of(DEFAULT, new Rule(filterChains.get(DEFAULT), formatters.get(DEFAULT), appenders.get(DEFAULT))
    ));
  }

  public void log(Record record)
  {
    Set<FilterChain> usedFilterChains=rules.values().stream()
        .map(rule->rule.recordFilterChain())
        .collect(Collectors.toSet());

    Map<FilterChain, Boolean> filterChainsResults=applyToMap(usedFilterChains, filterChain->filterChain.evaluate(record));

    boolean nothingToLog=filterChainsResults.values().stream().allMatch(result->result==false);
    if(nothingToLog) return;

    //Rules that are not filtered out
    Set<Rule> usedRules=rules.values().stream()
        .filter(rule->filterChainsResults.get(rule.recordFilterChain()))
        .collect(Collectors.toSet());

    Stream<Formatter> usedFormatters=usedRules.stream()
        .map(rule->rule.recordFormatter())
        .distinct();

    Map<Formatter, Object> formattedRecords=applyToMap(usedFormatters, recordFormatter->recordFormatter.format(record));

    usedRules.forEach(rule->rule.appender().append(formattedRecords.get(rule.recordFormatter())));
  }

  protected <K, V> Map<K, V> applyToMap(Map<Object, K> operators, Function<K, V> function)
  {
    return applyToMap(operators.values().stream(), function);
  }

  protected <K, V> Map<K, V> applyToMap(Collection<K> operators, Function<K, V> function)
  {
    return applyToMap(operators.stream(), function);
  }
  protected <K, V> Map<K, V> applyToMap(Stream<K> operatorsStream, Function<K, V> function)
  {
    return operatorsStream.collect(Collectors.toMap(
        operator->operator,
        function
    ));

  }

  /**
   * The most convenient way to add a filter.
   * Use SiLogger.DEFAULT as filterChainId to add to the default filter chain.
   * If the specified filter chain not exists, it will be created.
   *
   * @param filterChainId
   * @param filters
   */
  public void addToFilterChain(Object filterChainId, Filter... filters)
  {
    FilterChain filterChain=filterChains.get(filterChainId);
    if(filterChain==null)
    {
      filterChain=new FilterChain();
      filterChains.put(filterChainId, filterChain);
    }
    filterChain.addAll(Arrays.asList(filters));
  }

}
