package bz.util.logger.model;

public record Rule(
    FilterChain recordFilterChain,
    Formatter recordFormatter,
    Appender appender
)
{

}
