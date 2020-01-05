package rate.limiter;

import java.util.LinkedList;
import java.util.Queue;

public class SlidingLogRateLimiter extends AbstractRateLimiter {

    private final Queue<Long> q;

    public SlidingLogRateLimiter(int maxRequestPerSecond) {
        super(maxRequestPerSecond);
        q = new LinkedList();
    }

    @Override
    public boolean allow() {
        long currentTime = System.currentTimeMillis();
        long boundary = currentTime - 1000;
        synchronized(this) {
            while(!q.isEmpty() && q.peek()<boundary) {
                q.remove();
            }

            q.add(currentTime);

            if(q.size() <= maxRequestPerSecond) {
                return true;
            }
            return false;
        }
    }
}