package net.thumbtack.school.database.functional;

public interface MyFunction<T, K> {
    K apply(T arg);
}
