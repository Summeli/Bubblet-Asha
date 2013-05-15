package graviton.observerPattern;

import java.util.Vector;

/**
 * 
 * @author M. Serhat Cinar, http://graviton.de
 * @author Juan Antonio Agudo, modified code for the J2ME MIDP 1.0 Platform
 */
public class ObserverManager
{

    private Vector mObservers;

    public ObserverManager()
    {
    }

    public void addObserver(Observer pObserver)
    {
        if(mObservers == null)
            mObservers = new Vector();
        if(!mObservers.contains(pObserver))
            mObservers.addElement(pObserver);
    }

    public void removeObserver(Observer pObserver)
    {
        if(mObservers != null && mObservers.contains(pObserver))
            mObservers.removeElement(pObserver);
    }

    public void notifyObservers(Observable pObservable)
    {
        if(mObservers != null)
        {
            for(int i = 0; i < mObservers.size(); i++)
                ((Observer)mObservers.elementAt(i)).notify(pObservable);
        }
    }
}
