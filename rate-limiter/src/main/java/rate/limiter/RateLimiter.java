package rate.limiter;

public abstract class RateLimiter {

    protected int maxRequestPerSecond;

    public RateLimiter(int maxRequestPerSecond) {
        this.maxRequestPerSecond = maxRequestPerSecond;
    }

    public abstract boolean allow();
}