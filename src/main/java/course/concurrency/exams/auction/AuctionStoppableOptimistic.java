package course.concurrency.exams.auction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AuctionStoppableOptimistic implements AuctionStoppable {

    private Notifier notifier;
    private ExecutorService executorService = Executors.newFixedThreadPool(12);

    public AuctionStoppableOptimistic(Notifier notifier) {
        this.notifier = notifier;
    }

    private final AtomicReference<Bid> latestBidReference = new AtomicReference<>(new Bid(0L, 0L, 0L));
    private final AtomicBoolean stop = new AtomicBoolean(false);

    public boolean propose(Bid newBid) {
//        if (!stop.get()) {
            AtomicBoolean result = new AtomicBoolean(false);
            Bid nextBid = latestBidReference.getAndAccumulate(newBid, (latestBid, bid) -> {
//                System.out.println("Stop: " + stop.get());
                if ((latestBid == null || bid.getPrice() > latestBid.getPrice()) && !stop.get()) {
                    result.set(true);
                    executorService.submit(() -> notifier.sendOutdatedMessage(latestBidReference.get()));
                    return bid;
                }
                return latestBid;
            });
            return result.get();
//        }
//        return false;
    }

    public Bid getLatestBid() {
        return latestBidReference.get();
    }

    public Bid stopAuction() {
        stop.set(true);
        return latestBidReference.get();
    }
}
