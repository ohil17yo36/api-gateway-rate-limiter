package rate.limiter;

import java.util.concurrent.TimeUnit;

public class TokenBucketAbstractRateLimiter extends AbstractRateLimiter {

    private int numberOfTokens;

    public TokenBucketAbstractRateLimiter(int maxRequestPerSecond) {
        super(maxRequestPerSecond);
        numberOfTokens = maxRequestPerSecond;
        new Thread(() -> {
            while(true) {
                try{
                    TimeUnit.SECONDS.sleep(1);
                } catch(InterruptedException ie) {
                    throw new RuntimeException(ie);
                }
                refill(numberOfTokens);
            }
        }).start();
    }

    public boolean allow() {
        synchronized(this) {
            if(numberOfTokens>0) {
                numberOfTokens--;
                return true;
            }
            return false;
        }
    }

    private void refill(int cnt) {
        synchronized(this) {
            numberOfTokens = Math.min(numberOfTokens + cnt, this.maxRequestPerSecond);
            notifyAll();
        }
    }
}