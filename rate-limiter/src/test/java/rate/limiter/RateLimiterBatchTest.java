package rate.limiter;

import metric.PerformanceMetric;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RateLimiterBatchTest {

    private static double ERROR_THRESHOLD;

    private RateLimitTester rateLimitTester;

    @Before
    public void init() {
        ERROR_THRESHOLD = 0.1;
        rateLimitTester = new RateLimitTester();
    }

    @Test
    public void firstTest() throws InterruptedException {
        AbstractRateLimiter rl = new TokenBucketRateLimiter(20);
        Thread thread = new Thread(() -> {
            PerformanceMetric performanceMetric = rateLimitTester.test(rl, 10, 1);
            Assert.assertTrue(1 - performanceMetric.getHitRate() <= ERROR_THRESHOLD);
            performanceMetric = rateLimitTester.test(rl, 20, 2);
            Assert.assertTrue(2 - performanceMetric.getHitRate() <= ERROR_THRESHOLD);
            performanceMetric = rateLimitTester.test(rl,50, 5);
            Assert.assertTrue(5 - performanceMetric.getHitRate() <= ERROR_THRESHOLD);
            performanceMetric = rateLimitTester.test(rl,100, 10);
            Assert.assertTrue(10 - performanceMetric.getHitRate() <= ERROR_THRESHOLD);
        });

        thread.start();
        thread.join();
    }
}
