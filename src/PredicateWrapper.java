public interface PredicateWrapper<T, E> {
    Boolean test(T customer, E fieldValue);
}

