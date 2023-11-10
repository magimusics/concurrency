package course.concurrency.exams.auction;

public class AuctionStoppablePessimistic implements AuctionStoppable {

    private Notifier notifier;
    private final Object lock = new Object();
    private volatile boolean stop;

    public AuctionStoppablePessimistic(Notifier notifier) {
        this.notifier = notifier;
    }

    private Bid latestBid;

    public boolean propose(Bid bid) {
        if (!stop) {
            synchronized (lock) {
                if ((latestBid == null || bid.getPrice() > latestBid.getPrice()) && !stop) {
                    latestBid = bid;
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized Bid getLatestBid() {
        return latestBid;
    }

    public synchronized Bid stopAuction() {
        stop = true;
        return latestBid;
    }
}
