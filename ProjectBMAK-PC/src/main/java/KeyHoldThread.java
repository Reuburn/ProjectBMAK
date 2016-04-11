import java.awt.*;
import java.util.concurrent.Callable;

/**
 * Created by Reuben Spiers on 14/03/2016.
 */
public class KeyHoldThread implements Callable<Void> {

    private final Robot robot;
    private final Integer keyToHold;

    public KeyHoldThread(Integer keyToHold, Robot robot) {
        this.robot = robot;
        this.keyToHold = keyToHold;
    }

    public void release() {
        robot.keyRelease(keyToHold);
    }

    public Void call() throws Exception {
        System.out.println("Holding key down.");
        robot.keyPress(keyToHold);
        return null;
    }
}
