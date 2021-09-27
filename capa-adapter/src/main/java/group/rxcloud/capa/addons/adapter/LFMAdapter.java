package group.rxcloud.capa.addons.adapter;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import reactor.core.publisher.Mono;

/**
 * ListenableFutureMonoAdapter
 */
public final class LFMAdapter {

    private LFMAdapter() {
    }

    public static <T> ListenableFuture<T> wrap(Mono<T> mono) {
        SettableFuture<T> future = SettableFuture.create();
        mono.subscribe(future::set, future::setException);
        return future;
    }

    public static <T> Mono<T> wrap(ListenableFuture<T> future) {
        return Mono.create(monoSink -> {
            future.addListener(() -> {
                try {
                    T value = future.get();
                    monoSink.success(value);
                } catch (Throwable throwable) {
                    monoSink.error(throwable);
                }
            }, Runnable::run);
        });
    }
}
