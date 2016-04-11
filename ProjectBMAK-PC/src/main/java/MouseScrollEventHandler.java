import java.awt.*;

/**
 * Created by Reuben Spiers on 08/04/2016.
 */
public class MouseScrollEventHandler implements MouseEventHandler{

    private final int scrollDirection;

    public MouseScrollEventHandler(int scrollDirection) {
        this.scrollDirection = scrollDirection;
    }

    public void handleMouseEvent(int command) {
        try{
            Robot rob = new Robot();
            rob.mouseWheel(scrollDirection);
        } catch (AWTException e) {
            System.out.println("ROB is a dying race. He also couldn't be instantiated.");
        }
    }
}
