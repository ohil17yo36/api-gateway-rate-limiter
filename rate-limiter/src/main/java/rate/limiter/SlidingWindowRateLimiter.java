package rate.limiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SlidingWindowRateLimiter extends AbstractRateLimiter {

    private final ConcurrentMap<Long, AtomicInteger> windowMap;

    public SlidingWindowRateLimiter(int maxRequestPerSecond) {
        super(maxRequestPerSecond);
        this.windowMap = new ConcurrentHashMap();
    }

    public boolean allow() {
        long currentTime = System.currentTimeMillis();
        long currentWindowKey = currentTime / (1000*1000);

        windowMap.putIfAbsent(currentWindowKey, new AtomicInteger(0));


        long preWindowKey = currentWindowKey - 1000;
        AtomicInteger preCount = windowMap.get(preWindowKey);
        if(preCount == null) {
            return windowMap.get(currentWindowKey).incrementAndGet() <= this.maxRequestPerSecond;
        }

        double preWeight = 1 - (currentTime - currentWindowKey) / 1000.0;
        long count = (long) (preCount.get() * preWeight + windowMap.get(currentWindowKey).incrementAndGet());
        return count <= maxRequestPerSecond;
    }
}