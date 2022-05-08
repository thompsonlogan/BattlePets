package GameControllers;

/**
 * CaretakerIterator class
 * An iterator to iterate over the memento list in the caretaker
 */
public class CaretakerIterator implements  Iterator
{
    private final int FIRST_INDEX = 0; // index of the fist element in a list

    private int currentIndex; // the current index of iteration
    private Caretaker caretaker; // the object to be iterated over

    /**
     * CaretakerIterator constructor
     * @param caretaker the object to be iterated over
     */
    CaretakerIterator(Caretaker caretaker)
    {
        this.caretaker = caretaker;
        currentIndex = -1;
    }

    /**
     * @return true if there is a next element
     */
    @Override
    public boolean hasNext()
    {
        return currentIndex < caretaker.getSize() - 1;
    }

    /**
     * @return true if there is a previous element
     */
    @Override
    public boolean hasPrevious()
    {
        return currentIndex > FIRST_INDEX;
    }

    /**
     * @return the next element in the list
     */
    @Override
    public Object next()
    {
        currentIndex++;
        Round.Memento round = caretaker.getMemento(currentIndex);
        return round;
    }

    /**
     * @return the previous element in the list
     */
    @Override
    public Object previous()
    {
        currentIndex--;
        Round.Memento round = caretaker.getMemento(currentIndex);
        return round;
    }

    /**
     * @return the current index of iteration
     */
    @Override
    public int getCurrentIndex()
    {
        return this.currentIndex;
    }
}
