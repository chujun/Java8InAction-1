package lambdasinaction.chap11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BestPriceFinder {

    private final List<Shop> shops    = Arrays.asList(new Shop("BestPrice"), new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"), new Shop("BuyItAll"), new Shop("ShopEasy"));

    private final Executor   executor = Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {
                                          @Override
                                          public Thread newThread(Runnable r) {
                                              Thread t = new Thread(r);
                                              t.setDaemon(true);
                                              return t;
                                          }
                                      });

    public List<String> findPricesSequential(String product) {
        return shops.stream().map(shop -> shop.getPrice(product)).map(Quote::parse).map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public List<String> findPricesParallel(String product) {
        return shops.parallelStream().map(shop -> shop.getPrice(product)).map(Quote::parse).map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public List<String> findPricesFuture(String product) {
        List<CompletableFuture<String>> priceFutures = findPricesStream(product)
                .collect(Collectors.<CompletableFuture<String>> toList());

        return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public List<String> findPricesFuture2(String product) {
        List<CompletableFuture<String>> priceFutures = findPricesStream2(product)
            .collect(Collectors.<CompletableFuture<String>> toList());

        return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(feature -> feature.thenApply((quote) -> Discount.applyDiscount(quote)));
        //.map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));
    }

    public Stream<CompletableFuture<String>> findPricesStream2(String product) {
        return shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                //thenCompose VS thenApply
                .map(future -> future.thenCompose(
                        quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));
    }

    public void printAllPricesStream(String product) {
        long start = System.nanoTime();
        CompletableFuture[] futures = findPricesStream(product)
                .map(f -> f.thenAccept(s -> System.out
                        .println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(size -> new CompletableFuture[size]);
        //等待所有CompletableFuture执行完毕
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

    public void printAnyPricesStream(String product) {
        long start = System.nanoTime();
        CompletableFuture[] futures = findPricesStream(product)
            .map(f -> f.thenAccept(s -> System.out
                .println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
            .toArray(size -> new CompletableFuture[size]);
        //等待任意一个CompletableFuture执行完毕，主线程就继续执行下去
        CompletableFuture.anyOf(futures).join();
        System.out.println("any shop have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

}
