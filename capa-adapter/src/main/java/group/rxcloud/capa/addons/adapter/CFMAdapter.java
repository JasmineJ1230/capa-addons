package group.rxcloud.capa.addons.adapter;

import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFutureMonoAdapter
 */
public final class CFMAdapter {

    private CFMAdapter() {
    }

    public static <T> CompletableFuture<T> wrap(Mono<T> mono) {
        return mono.toFuture();
    }

    public static <T> Mono<T> wrap(CompletableFuture<T> future) {
        return Mono.fromCompletionStage(future);
    }
}
