/*
 * Created on 31.07.2004
 * Last commit $Date: 2004/07/31 16:23:41 $
 * $Revision: 1.1 $
 * $Author: tag $
 */
package highscore;

/**
 * @author tag
 *
 */
public class NamedScore extends Score {
    private String name;
    /**
     * @param pScore
     */
    public NamedScore(int pScore) {
        super(pScore);
        // TODO Auto-generated constructor stub
    }
    
    public NamedScore(int pScore, String pName){
        super(pScore);
        name = pName;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
