package rate.limiter;

public abstract class AbstractRateLimiter {

    protected int maxRequestPerSecond;

    public AbstractRateLimiter(int maxRequestPerSecond) {
        this.maxRequestPerSecond = maxRequestPerSecond;
    }

    public abstract boolean allow();
}