package id.ac.ub.filkom.sekcv.appstroke.model.custom.java.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.custom.java.util> created by :
 * Name         : syafiq
 * Date / Time  : 15 September 2016, 9:41 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ObservableLinkedList<E> extends Observable
{
    private LinkedList<E> lists;

    public ObservableLinkedList()
    {
        this.lists = new LinkedList<>();
    }

    public void replaceList(final Collection<? extends E> lists)
    {
        if(this.lists == null)
        {
            this.lists = new LinkedList<>();
        }
        this.lists.clear();
        this.lists.addAll(lists);
        this.update();
    }

    public LinkedList<E> getLists()
    {
        return this.lists;
    }

    private void update()
    {
        super.setChanged();
        super.notifyObservers();
    }
}
