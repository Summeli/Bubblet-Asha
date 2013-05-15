/*
 * Created on 29.07.2004
 * Last commit $Date: 2004/08/02 01:26:08 $
 * $Revision: 1.4 $
 * $Author: tag $
 */
package highscore;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author tag
 *  
 */
public class ScoreList {
    private Vector list;
    private int listLength;
    private boolean namedList;

    /**
     *  
     */
    public ScoreList(Vector rawValues, boolean pNamedList, int pListLength) {
        namedList = pNamedList;
        listLength = pListLength;
        list = new Vector(listLength);

        Enumeration e = rawValues.elements();

        while(e.hasMoreElements()) {
            list.addElement(new Score(Integer.parseInt((String) e.nextElement())));
        }
    }

    public void addNewScore(Score pScore) {
        if (pScore == null) {
            return;
        }

        list.addElement(pScore);
        
        sort();
        
        if (list.size() > listLength) {
            list.removeElementAt(list.size()-1);
        }
        return;
    }

    public Vector getList() {
        return list;
    }

    private void sort() {
        for (int i = list.size(); --i >= 0;) {
            boolean flipped = false;
            for (int j = 0; j < i; j++) {
                if (((Score) list.elementAt(j)).getScore() < ((Score) list.elementAt(j + 1)).getScore()) {
                    int T = ((Score) list.elementAt(j)).getScore();
                    list.setElementAt(list.elementAt(j + 1), j);
                    list.setElementAt(new Score(T), j + 1);
                    flipped = true;
                }
            }
            if (!flipped) {
                return;
            }
        }
    }

}