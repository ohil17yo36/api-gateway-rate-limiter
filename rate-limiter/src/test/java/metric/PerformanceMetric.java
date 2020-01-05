package metric;

public class PerformanceMetric {

    private int totalRequests;
    private double duration;
    private double hitRate;

    public PerformanceMetric(int totalRequests, double duration, double hitRate) {
        this.totalRequests = totalRequests;
        this.duration = duration;
        this.hitRate = hitRate;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(int totalRequests) {
        this.totalRequests = totalRequests;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getHitRate() {
        return hitRate;
    }

    public void setHitRate(double hitRate) {
        this.hitRate = hitRate;
    }
}
