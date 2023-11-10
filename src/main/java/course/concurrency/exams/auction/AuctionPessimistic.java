package course.concurrency.exams.auction;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AuctionPessimistic implements Auction {

    private Notifier notifier;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    public AuctionPessimistic(Notifier notifier) {
        this.notifier = notifier;
    }

//    private Bid latestBid = new Bid(0L, 0L, 0L);
    private Bid latestBid;

    public AuctionPessimistic() {
        scheduledExecutor.scheduleAtFixedRate(() -> {
            notifier.sendOutdatedMessage(latestBid);
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public synchronized boolean propose(Bid bid) {
        if (latestBid == null || bid.getPrice() > latestBid.getPrice()) {
            latestBid = bid;
            return true;
        }
        return false;
    }

    public synchronized Bid getLatestBid() {
        return latestBid;
    }
}
