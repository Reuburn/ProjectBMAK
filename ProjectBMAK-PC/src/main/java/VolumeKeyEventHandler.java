import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

/**
 * Created by Reuben Spiers on 14/03/2016.
 */
public class VolumeKeyEventHandler implements KeyEventHandler {

    private final int commandToProcess;

    public VolumeKeyEventHandler(int commandToProcess){
    this.commandToProcess = commandToProcess;
    }

        public void handleKeyEvent(List<Integer> modifiers) {
        try{
            Robot rob = new Robot();
            switch (commandToProcess){
                case 1:
                    if(ProcessConnectionThread.volumeHeld){
                        volumeDirection(1, rob);
                        System.out.println("Volume up held.");
                    } else {
                        openWindowsSoundMaster();
                        System.out.println("Volume up pressed.");
                        ProcessConnectionThread.volumeHeld = true;
                        Thread.sleep(500);
                    }
                    break;

                case 2:
                    System.out.println("Volume up held.");
                    ProcessConnectionThread.volumeHeld = true;
                    break;

                case 3:
                    rob.keyPress(KeyEvent.VK_ENTER);
                    System.out.println("Volume up released.");
                    ProcessConnectionThread.volumeHeld = false;
                    break;

                case 4:

                    if (ProcessConnectionThread.volumeHeld){
                        volumeDirection(0, rob);
                        System.out.println("Volume down held.");
                    } else {
                        openWindowsSoundMaster();
                        System.out.println("Volume down pressed.");
                        ProcessConnectionThread.volumeHeld = true;
                        Thread.sleep(500);
                    }
                    break;

                case 5:
                    System.out.println("Volume down held.");
                    ProcessConnectionThread.volumeHeld = true;
                    break;

                case 6:
                    rob.keyPress(KeyEvent.VK_ENTER);
                    System.out.println("Volume down released.");
                    ProcessConnectionThread.volumeHeld = false;
                    break;
            }
        }
        catch(AWTException e){
            System.out.println("Robot could not be instantiated.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }

    private void volumeDirection(int direction, Robot rob){
        if(direction == 1){
            rob.keyPress(KeyEvent.VK_UP);
        }

        else if(direction == 0){
            rob.keyPress(KeyEvent.VK_DOWN);
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void openWindowsSoundMaster() {
        try {
            Process process = Runtime.getRuntime().exec("sndvol -f");
        }
        catch(IOException e){

        }
    }
}