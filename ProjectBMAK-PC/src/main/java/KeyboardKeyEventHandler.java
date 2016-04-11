import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Reuben Spiers on 12/03/2016.
 */
public class KeyboardKeyEventHandler implements KeyEventHandler {

    private final int keyToType;

    public KeyboardKeyEventHandler(int keyToType) {
        this.keyToType = keyToType;
    }

    public void handleKeyEvent(List<Integer> modifiers) {
        try {
            Robot robot = new Robot();
            ExecutorService executorService = null;
            List<KeyHoldThread> keysBeingHeld = new ArrayList<KeyHoldThread>();
            if(modifiers.size() > 0) {
                executorService = Executors.newFixedThreadPool(modifiers.size());
                keysBeingHeld.addAll(holdAllKeysFor(robot, executorService, modifiers));
            }

            robot.keyPress(keyToType);
            robot.keyRelease(keyToType);

            System.out.println("Keyboard key handled.");
            if(modifiers.size() > 0) {
                releaseAll(keysBeingHeld, modifiers);
                executorService.shutdownNow();
            }
        } catch (AWTException e) {
            System.out.println("Robot could not be instantiated.");
        }
    }

    private void releaseAll(List<KeyHoldThread> keysBeingHeld, List<Integer> modifiers) {
        System.out.println("Releasing all held keys.");
        for(KeyHoldThread keyBeingHeld : keysBeingHeld) {
            keyBeingHeld.release();
        }
        modifiers.clear();
    }

    private List<KeyHoldThread> holdAllKeysFor(Robot robot, ExecutorService executorService, List<Integer> modifiers) {
        List<KeyHoldThread> allThreads = new ArrayList<KeyHoldThread>();
        Collection<Callable<Void>> keysBeingHeld = new ArrayList<Callable<Void>>();
        for(Integer modifier: modifiers) {
            KeyHoldThread keyBeingHeld = new KeyHoldThread(modifier, robot);
            keysBeingHeld.add(keyBeingHeld);
            allThreads.add(keyBeingHeld);
        }
        try {
            executorService.invokeAll(keysBeingHeld);
        } catch (InterruptedException e) {
            System.out.println("Keys could not be held down.");
        }
        return allThreads;
    }
}