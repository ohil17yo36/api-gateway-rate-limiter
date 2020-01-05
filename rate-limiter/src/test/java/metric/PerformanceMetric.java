package metric;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PUBLIC)
public abstract class PerformanceMetric {

    public abstract int totalRequests();
    public abstract double duration();
    public abstract double hitRate();
}
