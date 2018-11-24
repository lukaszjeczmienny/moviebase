package lj.moviebase.query.filter;

import java.util.function.Predicate;

public interface FilteringCriteria<T> {
    Predicate<T> asPredicate();
}
