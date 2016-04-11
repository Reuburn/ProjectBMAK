import java.awt.event.KeyEvent;

/**
 * Created by Reuben Spiers on 10/03/2016.
 */
import java.awt.*;

public class ModifierThread extends Thread {
    private int command;
    private boolean held;
    private Robot robot;
    private int modifier;

    public ModifierThread(){

    }

    public void run(){
        while (held && isALetter()){
            robot.keyPress(KeyEvent.VK_SHIFT);
        }
    }

    private boolean isALetter() {
        return command > 6 && command < 33;
    }

}
