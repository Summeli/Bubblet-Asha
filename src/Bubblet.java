/*
 * Created on 22.07.2004
 * Last commit $Date: 2004/07/25 01:38:28 $
 * $Revision: 1.2 $
 * $Author: tag $
 */


import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * @author tag
 *
 */
public class Bubblet extends MIDlet {
    BubbletCanvas canvas;
    /**
     * 
     */
    public Bubblet() {
    	String keyboardType = System.getProperty("com.nokia.keyboard.type");
    	if (keyboardType.equals("OnekeyBack") || keyboardType.equals("None")) {
    	    // Series 40 full touch or Nokia Asha software platform detected
    	    // Your code here
    	    com.nokia.mid.ui.VirtualKeyboard.hideOpenKeypadCommand(true);
    	}
    	
        canvas = new BubbletCanvas(this,10,7);
    }

    protected void startApp() throws MIDletStateChangeException {
        Display.getDisplay(this).setCurrent(canvas);
    }

    /* (non-Javadoc)
     * @see javax.microedition.midlet.MIDlet#pauseApp()
     */
    protected void pauseApp() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
     */
    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // TODO Auto-generated method stub
    }
    
    protected Display getDisplay(){
        return Display.getDisplay(this);
    }

}
