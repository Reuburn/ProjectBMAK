import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Reuben Spiers on 04/04/2016.
 */
public class MouseMovementEventHandler implements MouseEventHandler{


    public MouseMovementEventHandler() {
    }

    public void handleMouseMoveEvent(int direction) {
        try {
            Robot rob = new Robot();

            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            int mouseX = (int) mouseLocation.getX();
            int mouseY = (int) mouseLocation.getY();

            switch (direction) {
                case 200:
                    rob.mouseMove(mouseX - 5, mouseY);
                    break;
                case 201:
                    rob.mouseMove(mouseX + 5, mouseY);
                    break;
                case 202:
                    rob.mouseMove(mouseX, mouseY + 5);
                    break;
                case 203:
                    rob.mouseMove(mouseX, mouseY - 5);
                    break;
            }

        } catch (AWTException e) {
            System.out.println("Robot could not be instantiated.");
        }
    }

    public void handleMouseEvent(int command) {
        handleMouseMoveEvent(command);
    }
}
