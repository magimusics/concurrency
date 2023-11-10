package course.concurrency.exams.auction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AuctionOptimistic implements Auction {

    private Notifier notifier;
    private ExecutorService executorService = Executors.newFixedThreadPool(12);

    public AuctionOptimistic(Notifier notifier) {
        this.notifier = notifier;
    }

    private final AtomicReference<Bid> latestBidReference = new AtomicReference<>(null);

    public boolean propose(Bid newBid) {
        AtomicBoolean result = new AtomicBoolean(false);
        Bid nextBid = latestBidReference.getAndAccumulate(newBid, (latestBid, bid) -> {
            if (latestBid == null || bid.getPrice() > latestBid.getPrice()) {
                result.set(true);
                executorService.submit(() -> notifier.sendOutdatedMessage(latestBidReference.get()));
                return bid;
            }
            return latestBid;
        });
        return result.get();
    }

    public Bid getLatestBid() {
        return latestBidReference.get();
    }
}
