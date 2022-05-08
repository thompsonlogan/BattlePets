package GameControllers;

/**
 * Interface defines behavior to return an iterator
 * @param <T> The type of iterator
 */
public interface Iterable<T>
{
    /**
     * @return an iterator
     */
    Iterator<T> iterator();
}
