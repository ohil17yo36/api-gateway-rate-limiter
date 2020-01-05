package rate.limiter.util;

import metric.PerformanceMetric;
import rate.limiter.AbstractRateLimiter;

import java.util.concurrent.CountDownLatch;

public class RateLimitTester {

    public PerformanceMetric test(AbstractRateLimiter rl, int totalRequests, int requestRate) {

        long startTime = System.currentTimeMillis();
        CountDownLatch cdl = new CountDownLatch(totalRequests);

        for(int i=0; i<totalRequests; i++) {
            new Thread(() -> {
                while(!rl.allow()) {
                    try{
                        Thread.sleep(10);
                    } catch(InterruptedException ie) {
                        throw new RuntimeException(ie);
                    }
                }
                cdl.countDown();
            }).start();
            try{
                Thread.sleep(1000/requestRate);
            } catch(InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        }

        try{
            cdl.await();
        } catch(InterruptedException ie) {
            throw new RuntimeException(ie);
        }


        double duration = (System.currentTimeMillis() - startTime) / 1000.0;
        return new PerformanceMetric(totalRequests, duration, (double) totalRequests / duration);
    }
}
