/*
 * Created on 29.07.2004
 * Last commit $Date: 2004/07/31 16:23:41 $
 * $Revision: 1.2 $
 * $Author: tag $
 */
package highscore;

/**
 * @author tag
 *
 */
public class Score{

    private int score;
    
    public Score(int pScore){
        score = pScore;
    }
    /**
     * @return Returns the score.
     */
    public int getScore() {
        return score;
    }
    /**
     * @param score The score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }
   
    public boolean equals(Object o){
        if(o instanceof Score){
            Score score = (Score)o;
            if(!(o instanceof Score)){
                return false;
            }
            return this.score == score.score;
        }
        return false;
    }
    
    public int compareTo(Score s){
        if(score > s.getScore()){
            return 1; // this.bigger
        }else if(score < s.getScore()){
            return -1; // this.smaller
        }
        return 0; // equal
    }
    
    public String toString(){
        return ""+score;
    }
}