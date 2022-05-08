package GameControllers;
import java.util.ArrayList;
import java.util.List;

/**
 * Caretaker class
 * Manages a list of memento objects and implements
 * an iterator to iterate over the memento list
 */
public class Caretaker implements Iterable
{
    private Round originator; // The originator object to be stored as a memento
    private List<Round.Memento> mementoList; // A list of all mementos

    /**
     * Caretaker constructor
     * @param originator Round
     */
    public Caretaker(Round originator)
    {
        mementoList = new ArrayList<>();
        this.originator = originator;
    }

    /**
     * Saves the originator as a memento
     */
    public void saveMemento()
    {
        Round.Memento memento = originator.saveState();
        addMemento(memento);
    }

    /**
     * Restores the state the originator from a memento
     * @param index of the memento in the memento list
     */
    public void restoreMemento(int index)
    {
        Round.Memento memento = getMemento(index);
        originator.restoreState(memento);
    }

    /**
     * Adds a memento to the list
     * @param memento round
     */
    public void addMemento(Round.Memento memento)
    {
        mementoList.add(memento);
    }

    /**
     * @param index of the memento in the memento list
     * @return a memento object from the list based on the passed index
     */
    public Round.Memento getMemento(int index)
    {
        return mementoList.get(index);
    }

    /**
     * Removes a memento from the memento list
     * @param index of the memento in the memento list
     * @return the removed memento
     */
    public Round.Memento removeMemento(int index)
    {
        return mementoList.remove(index);
    }

    /**
     * @return the size of the memento list
     */
    public int getSize()
    {
        return mementoList.size();
    }

    /**
     * Updates the caretaker for a new fight
     */
    public void newFight()
    {
        this.originator.newFight();
    }

    /**
     * @return an iterator for the memento list
     */
    @Override
    public Iterator iterator()
    {
        return new CaretakerIterator(this);
    }
}
