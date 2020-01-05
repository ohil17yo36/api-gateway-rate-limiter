package rate.limiter;

import org.junit.Before;
import org.junit.Test;

public class RateLimiterBatchTest {

    private RateLimitTester rateLimitTester;

    @Before
    public void init() {
        rateLimitTester = new RateLimitTester();
    }

    @Test
    public void firstTest() throws InterruptedException {
        AbstractRateLimiter rl = new TokenBucketRateLimiter(20);
        Thread thread = new Thread(() -> {
            rateLimitTester.test(rl, 10, 1);
            rateLimitTester.test(rl, 20, 2);
            rateLimitTester.test(rl,50, 5);
            rateLimitTester.test(rl,100, 10);
            rateLimitTester.test(rl,200, 20);
            rateLimitTester.test(rl,250, 25);
            rateLimitTester.test(rl,500, 50);
            rateLimitTester.test(rl,1000, 100);
        });

        thread.start();
        thread.join();
    }
}
