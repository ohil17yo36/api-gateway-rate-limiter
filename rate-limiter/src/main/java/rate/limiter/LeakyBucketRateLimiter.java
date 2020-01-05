package rate.limiter;

public class LeakyBucketRateLimiter extends AbstractRateLimiter {

    private long nextAllowedTime;

    private final long TIME_INTERVAL_BETWEEN_REQUESTS;

    public LeakyBucketRateLimiter(int maxRequestPerSecond) {
        super(maxRequestPerSecond);
        TIME_INTERVAL_BETWEEN_REQUESTS = 1000/maxRequestPerSecond;
        nextAllowedTime = System.currentTimeMillis();
    }

    @Override
    public boolean allow() {
        long currentTime = System.currentTimeMillis();
        synchronized(this) {
            if(currentTime>=nextAllowedTime) {
                nextAllowedTime = currentTime + TIME_INTERVAL_BETWEEN_REQUESTS;
                return true;
            }

            return false;
        }
    }
}