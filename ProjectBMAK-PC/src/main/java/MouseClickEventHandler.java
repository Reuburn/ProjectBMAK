import java.awt.*;
/**
 * Created by Reuben Spiers on 06/04/2016.
 */
public class MouseClickEventHandler implements MouseEventHandler{

    private final int mouseEvent;

    public MouseClickEventHandler(int mouseEvent) {
        this.mouseEvent = mouseEvent;
    }

    public void handleMouseEvent(int command) {
        try {
            Robot rob = new Robot();

            if (command < 207) rob.mousePress(mouseEvent);
            if (command > 206) rob.mouseRelease(mouseEvent);

            System.out.println("Mouse click handled.");

        } catch (AWTException e) {
            System.out.println("Robot could not be instantiated.");
        }
    }
}
