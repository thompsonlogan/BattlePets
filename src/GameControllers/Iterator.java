package GameControllers;

/**
 * Interface defines behavior to iterator over elements in an iterable data
 * @param <E> The type of elements being iterated over
 */
public interface Iterator<E>
{
    /**
     * @return if there is a next element in the iterable
     */
    boolean hasNext();

    /**
     * @return if there is a previous element in the iterable
     */
    boolean hasPrevious();

    /**
     * @return the next element in the iterable
     */
    E next();

    /**
     * @return the previous element in the iterable
     */
    E previous();

    /**
     * @return the current index of iteration
     */
    int getCurrentIndex();
}
