package nz.co.udenbrothers.clockwork.abstractions;

/**
 * Created by user on 28/08/2017.
 */

public interface Filter<T> {
    public boolean filt(T t);
}
