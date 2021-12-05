package group.rxcloud.capa.addons.foundation.trip;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public class ServiceBootstrap {
    public static <S> S loadFirst(Class<S> clazz) {
        Iterator<S> iterator = loadAll(clazz);
        if (!iterator.hasNext()) {
            throw new IllegalStateException(String.format(
                    "No implementation defined in /META-INF/services/%s, please check whether the file exists and has the right implementation class!",
                    clazz.getName()));
        }
        return iterator.next();
    }

    private static <S> Iterator<S> loadAll(Class<S> clazz) {
        ServiceLoader<S> loader = ServiceLoader.load(clazz);

        return loader.iterator();
    }
}
