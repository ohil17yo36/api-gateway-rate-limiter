package rate.limiter;

public class TokenBucketLazyRateLimiter extends AbstractRateLimiter {

    private int tokenCount;

    private long lastRefillTime;

    public TokenBucketLazyRateLimiter(int maxRequestPerSecond) {
        super(maxRequestPerSecond);
        this.tokenCount = maxRequestPerSecond;
        this.lastRefillTime = System.currentTimeMillis();
    }

    @Override
    public boolean allow() {
        synchronized(this) {
            refill();

            if(this.tokenCount>0) {
                this.tokenCount--;
                return true;
            }
            else {
                return false;
            }
        }
    }

    private void refill() {
        long currentTime = System.currentTimeMillis();
        if(currentTime > lastRefillTime) {
            long elapsedTime = (currentTime - lastRefillTime)/1000;
            int tokensToAdd = ((int)elapsedTime)*this.maxRequestPerSecond;
            if(tokensToAdd>0) {
                this.tokenCount = Math.min(this.tokenCount + tokensToAdd, maxRequestPerSecond);
                lastRefillTime = currentTime;
            }
        }
    }
}