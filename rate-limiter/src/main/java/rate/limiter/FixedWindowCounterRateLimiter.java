package rate.limiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowCounterAbstractRateLimiter extends AbstractRateLimiter {

    private final ConcurrentMap<Long, AtomicInteger> windowMap;

    public FixedWindowCounterAbstractRateLimiter(int maxRequestPerSecond) {
        super(maxRequestPerSecond);
        this.windowMap = new ConcurrentHashMap();
    }

    public boolean allow() {
        long currentTimeInMillis = System.currentTimeMillis();
        long windowIndex = currentTimeInMillis/(1000*1000);
        windowMap.putIfAbsent(windowIndex, new AtomicInteger(0));
        return windowMap.get(windowIndex).incrementAndGet() <= this.maxRequestPerSecond;
    }
}