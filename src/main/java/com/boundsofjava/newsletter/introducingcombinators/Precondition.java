package com.boundsofjava.newsletter.introducingcombinators;

import java.util.function.Function;
import java.util.function.Predicate;

// @formatter:off
@FunctionalInterface
public interface Precondition<T, R, X extends RuntimeException>
        extends Function<Predicate<T>,
                Function<Function<T, R>,
                Function<Function<T, X>,
                Function<T, R>>>> {
// @formatter:on

    static <T, R, X extends RuntimeException> Precondition<T, R, X> create() {
        return condition -> function -> error -> Provided.decorate(
                condition,
                function,
                arg -> {
                    throw error.apply(arg);
                });
    }

    static <T, R, X extends RuntimeException> Function<T, R> decorate(
            Predicate<T> condition,
            Function<T, R> function,
            Function<T, X> error) {
        return Precondition.<T, R, X>create().apply(condition).apply(function).apply(error);
    }
}
