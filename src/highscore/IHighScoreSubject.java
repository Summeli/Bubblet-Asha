/*
 * Created on 28.07.2004
 * Last commit $Date: 2004/07/29 00:28:01 $
 * $Revision: 1.1 $
 * $Author: tag $
 */
package highscore;

/**
 * @author tag
 * 
 */
public interface IHighScoreSubject {
    public boolean isGameFinished();
    public int getScore ();
    public String getPlayerName(); // just return null if undesired
}
