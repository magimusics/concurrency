package course.concurrency.m2_async.cf.min_price;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PriceAggregator {

    private PriceRetriever priceRetriever = new PriceRetriever();

    public void setPriceRetriever(PriceRetriever priceRetriever) {
        this.priceRetriever = priceRetriever;
    }

    private Collection<Long> shopIds = Set.of(10l, 45l, 66l, 345l, 234l, 333l, 67l, 123l, 768l);

    public void setShops(Collection<Long> shopIds) {
        this.shopIds = shopIds;
    }

    public double getMinPrice(long itemId)  {
        try {
            Executor executor = Executors.newCachedThreadPool();
            List<CompletableFuture<Double>> prices = shopIds.stream()
                    .map(id -> CompletableFuture
                            .supplyAsync(() -> priceRetriever.getPrice(itemId, id), executor)
                            .exceptionally(exc -> Double.MAX_VALUE)
                            .completeOnTimeout(Double.MAX_VALUE, 3, TimeUnit.SECONDS)
                    )
                    .collect(Collectors.toList());

            CompletableFuture.allOf(prices.toArray(CompletableFuture[]::new))
                    .completeOnTimeout(null, 2500, TimeUnit.MILLISECONDS).get();
            List<Double> list = prices.stream()
                    .filter(CompletableFuture::isDone)
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
            return list.stream().min(Double::compareTo).orElse(Double.NaN);
        } catch (Exception e) {
            return Double.NaN;
        }
    }
}
