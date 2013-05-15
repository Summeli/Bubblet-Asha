/*
 * Created on 27.07.2004
 * Last commit $Date: 2004/08/02 01:26:08 $
 * $Revision: 1.5 $
 * $Author: tag $
 */
package highscore;

import graviton.observerPattern.Observable;
import graviton.observerPattern.Observer;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;

/**
 * @author tag
 *  
 */
public class HighScoreManager implements Observer {
    private final String recordName;
    private final boolean namedList = false; // true if names should be saved
                                             // too
    private ScoreList scoreList;
    private static HighScoreManager instance; // Singleton instance
    private static String delimiter = ":::";

    private HighScoreManager(int pListLength, boolean pNamedScores, String pRecordName) {
        recordName = pRecordName;
        Vector rawValues = readHighScoreList();
        scoreList = new ScoreList(rawValues, pNamedScores, pListLength);
    }

    /**
     * Retrieve and configure the singleton instance of the Highscore Manager
     * 
     * @param pListLength
     *            Sets the number of elements of the highscore list.
     * @param pNamedScores
     *            true if the highscores should have a name associated to them,
     *            false otherwise
     * @param pRecordName
     *            Sets the name of the Recordstore under which the highscore
     *            data is saved.
     * @return Singleton instance of HighScoreManager
     */
    public static HighScoreManager getInstance(int pListLength, boolean pNamedScores, String pRecordName) {
        if (instance == null) {
            instance = new HighScoreManager(pListLength, pNamedScores, pRecordName);
        }
        return instance;
    }

    public Vector getHighScoreList() {
        return scoreList.getList();
    }
    
    public String getSimpleList() {
        Vector list = scoreList.getList();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Score s = (Score) list.elementAt(i);
            sb.append(i+1 +": " + s.getScore() + " \n");
        }
        return sb.toString();
    }

    public void addNewScore(int pScore) {
        if (namedList) {
            throw new RuntimeException("addNewScore() failed, name attribute must be supplied");
        }
        Score s = new Score(pScore);
        scoreList.addNewScore(s);
        saveScoreList();

    }

    public void addNewScore(int pScore, String pName) {
        if (!namedList) {
            throw new RuntimeException("addNewScore() failed, name attribute must not be supplied");
        }
        NamedScore s = new NamedScore(pScore, pName);
        scoreList.addNewScore(s);
        saveScoreList();
    }

    public synchronized void saveScoreList() {
        RecordStore rs = null;
        Vector scores = scoreList.getList();
        try {
            Enumeration e = scores.elements();
            StringBuffer sb = new StringBuffer();
            while (e.hasMoreElements()) {
                Score singleScore = (Score) e.nextElement();
                //String name = singleScore.getName();
                String score = "" + singleScore.getScore();
                sb.append(score + delimiter);
            }

            String values = sb.toString();

            byte[] data = values.getBytes();
            RecordStore.deleteRecordStore(recordName);
            rs = RecordStore.openRecordStore(recordName, true);
            // int numRecords = rs.getNumRecords();
            rs.addRecord(data, 0, data.length);

        } catch (RecordStoreFullException e) {
            throw new RuntimeException(e.getMessage());
        } catch (RecordStoreNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (RecordStoreException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.closeRecordStore();
                } catch (Exception e) {
                }
            }
        }
    }

    private Vector readHighScoreList() {
        RecordStore rs = null;
        Vector vect = new Vector();

        String values = "";//"123" + delimiter + "234" + delimiter + "7894" + delimiter;

        try {
            rs = RecordStore.openRecordStore(recordName, true);
            if (rs.getNumRecords() == 0) {
                rs.addRecord(null, 0, 0);
            } else {
                byte[] data = rs.getRecord(1);
                StringBuffer sb = new StringBuffer();

		if (data != null) {
		    for (int i = 0; i < data.length; i++) {
			sb.append((char) data[i]);
		    }
		}
                values = sb.toString();
            }
        } catch (RecordStoreFullException e) {
            throw new RuntimeException(e.getMessage());
        } catch (RecordStoreNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (RecordStoreException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.closeRecordStore();
                } catch (Exception e) {
                }
            }
        }

        if (values != null) {
            int index = 0;
            int lastindex = index;
            while ((index = values.indexOf(delimiter, lastindex + 1)) != -1) {
                if (lastindex == 0) {
                    vect.addElement(values.substring(lastindex, index));
                } else {
                    vect.addElement(values.substring(lastindex + delimiter.length(), index));
                }

                lastindex = index;
            }
        }

        return vect;
    }

    public void notify(Observable observable) {
        if (observable instanceof IHighScoreSubject) {
            IHighScoreSubject subject = (IHighScoreSubject) observable;
            int score = subject.getScore();
            String name = subject.getPlayerName();
            if (name == null) {
                addNewScore(score);
            } else {
                addNewScore(score, name);
            }
        }
    }

}
