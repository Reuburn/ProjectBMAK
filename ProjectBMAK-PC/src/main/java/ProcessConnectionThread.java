import javax.microedition.io.StreamConnection;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by Reuben Spiers on 22/02/2016.
 */
public class ProcessConnectionThread implements Runnable {

    private StreamConnection mConnection;
    private static final Map<Integer, KeyEventHandler> CHAR_MAP = new HashMap<Integer, KeyEventHandler>();
    private static final Map<Integer, MouseEventHandler> MOUSE_MAP = new HashMap();
    private static final int EXIT_CMD = -1;
    static{
        CHAR_MAP.put(1, new VolumeKeyEventHandler(1));
        CHAR_MAP.put(2, new VolumeKeyEventHandler(2));
        CHAR_MAP.put(3, new VolumeKeyEventHandler(3));
        CHAR_MAP.put(4, new VolumeKeyEventHandler(4));
        CHAR_MAP.put(5, new VolumeKeyEventHandler(5));
        CHAR_MAP.put(6, new VolumeKeyEventHandler(6));
        CHAR_MAP.put(7,new KeyboardKeyEventHandler(KeyEvent.VK_A));
        CHAR_MAP.put(8,new KeyboardKeyEventHandler(KeyEvent.VK_B));
        CHAR_MAP.put(9,new KeyboardKeyEventHandler(KeyEvent.VK_C));
        CHAR_MAP.put(10,new KeyboardKeyEventHandler(KeyEvent.VK_D));
        CHAR_MAP.put(11,new KeyboardKeyEventHandler(KeyEvent.VK_E));
        CHAR_MAP.put(12,new KeyboardKeyEventHandler(KeyEvent.VK_F));
        CHAR_MAP.put(13,new KeyboardKeyEventHandler(KeyEvent.VK_G));
        CHAR_MAP.put(14,new KeyboardKeyEventHandler(KeyEvent.VK_H));
        CHAR_MAP.put(15,new KeyboardKeyEventHandler(KeyEvent.VK_I));
        CHAR_MAP.put(16,new KeyboardKeyEventHandler(KeyEvent.VK_J));
        CHAR_MAP.put(17,new KeyboardKeyEventHandler(KeyEvent.VK_K));
        CHAR_MAP.put(18,new KeyboardKeyEventHandler(KeyEvent.VK_L));
        CHAR_MAP.put(19,new KeyboardKeyEventHandler(KeyEvent.VK_M));
        CHAR_MAP.put(20,new KeyboardKeyEventHandler(KeyEvent.VK_N));
        CHAR_MAP.put(21,new KeyboardKeyEventHandler(KeyEvent.VK_O));
        CHAR_MAP.put(22,new KeyboardKeyEventHandler(KeyEvent.VK_P));
        CHAR_MAP.put(23,new KeyboardKeyEventHandler(KeyEvent.VK_Q));
        CHAR_MAP.put(24,new KeyboardKeyEventHandler(KeyEvent.VK_R));
        CHAR_MAP.put(25,new KeyboardKeyEventHandler(KeyEvent.VK_S));
        CHAR_MAP.put(26,new KeyboardKeyEventHandler(KeyEvent.VK_T));
        CHAR_MAP.put(27,new KeyboardKeyEventHandler(KeyEvent.VK_U));
        CHAR_MAP.put(28,new KeyboardKeyEventHandler(KeyEvent.VK_V));
        CHAR_MAP.put(29,new KeyboardKeyEventHandler(KeyEvent.VK_W));
        CHAR_MAP.put(30,new KeyboardKeyEventHandler(KeyEvent.VK_X));
        CHAR_MAP.put(31,new KeyboardKeyEventHandler(KeyEvent.VK_Y));
        CHAR_MAP.put(32,new KeyboardKeyEventHandler(KeyEvent.VK_Z));
        CHAR_MAP.put(33,new KeyboardKeyEventHandler(KeyEvent.VK_ENTER));
        CHAR_MAP.put(34,new KeyboardKeyEventHandler(KeyEvent.VK_BACK_SPACE));
        CHAR_MAP.put(35,new KeyboardKeyEventHandler(KeyEvent.VK_SPACE));
        CHAR_MAP.put(37,new KeyboardKeyEventHandler(KeyEvent.VK_CAPS_LOCK));
        CHAR_MAP.put(38,new KeyboardKeyEventHandler(KeyEvent.VK_0));
        CHAR_MAP.put(39,new KeyboardKeyEventHandler(KeyEvent.VK_1));
        CHAR_MAP.put(40,new KeyboardKeyEventHandler(KeyEvent.VK_2));
        CHAR_MAP.put(41,new KeyboardKeyEventHandler(KeyEvent.VK_3));
        CHAR_MAP.put(42,new KeyboardKeyEventHandler(KeyEvent.VK_4));
        CHAR_MAP.put(43,new KeyboardKeyEventHandler(KeyEvent.VK_5));
        CHAR_MAP.put(44,new KeyboardKeyEventHandler(KeyEvent.VK_6));
        CHAR_MAP.put(45,new KeyboardKeyEventHandler(KeyEvent.VK_7));
        CHAR_MAP.put(46,new KeyboardKeyEventHandler(KeyEvent.VK_8));
        CHAR_MAP.put(47,new KeyboardKeyEventHandler(KeyEvent.VK_9));
        CHAR_MAP.put(50,new KeyboardKeyEventHandler(KeyEvent.VK_DELETE));
        CHAR_MAP.put(51,new KeyboardKeyEventHandler(KeyEvent.VK_HOME));
        CHAR_MAP.put(52,new KeyboardKeyEventHandler(KeyEvent.VK_END));
        CHAR_MAP.put(53,new KeyboardKeyEventHandler(KeyEvent.VK_PAGE_UP));
        CHAR_MAP.put(54,new KeyboardKeyEventHandler(KeyEvent.VK_PAGE_DOWN));
        CHAR_MAP.put(55,new KeyboardKeyEventHandler(KeyEvent.VK_INSERT));
        CHAR_MAP.put(57,new KeyboardKeyEventHandler(KeyEvent.VK_WINDOWS));
        CHAR_MAP.put(58,new KeyboardKeyEventHandler(KeyEvent.VK_F1));
        CHAR_MAP.put(59,new KeyboardKeyEventHandler(KeyEvent.VK_F2));
        CHAR_MAP.put(60,new KeyboardKeyEventHandler(KeyEvent.VK_F3));
        CHAR_MAP.put(61,new KeyboardKeyEventHandler(KeyEvent.VK_F4));
        CHAR_MAP.put(62,new KeyboardKeyEventHandler(KeyEvent.VK_F5));
        CHAR_MAP.put(63,new KeyboardKeyEventHandler(KeyEvent.VK_F6));
        CHAR_MAP.put(64,new KeyboardKeyEventHandler(KeyEvent.VK_F7));
        CHAR_MAP.put(65,new KeyboardKeyEventHandler(KeyEvent.VK_F8));
        CHAR_MAP.put(66,new KeyboardKeyEventHandler(KeyEvent.VK_F9));
        CHAR_MAP.put(67,new KeyboardKeyEventHandler(KeyEvent.VK_F10));
        CHAR_MAP.put(68,new KeyboardKeyEventHandler(KeyEvent.VK_F11));
        CHAR_MAP.put(69,new KeyboardKeyEventHandler(KeyEvent.VK_F12));
        CHAR_MAP.put(70,new KeyboardKeyEventHandler(KeyEvent.VK_UP));
        CHAR_MAP.put(71,new KeyboardKeyEventHandler(KeyEvent.VK_LEFT));
        CHAR_MAP.put(72,new KeyboardKeyEventHandler(KeyEvent.VK_RIGHT));
        CHAR_MAP.put(73,new KeyboardKeyEventHandler(KeyEvent.VK_DOWN));
        CHAR_MAP.put(74,new KeyboardKeyEventHandler(KeyEvent.VK_ESCAPE));
        CHAR_MAP.put(76,new KeyboardKeyEventHandler(KeyEvent.VK_PERIOD));
        CHAR_MAP.put(77,new KeyboardKeyEventHandler(KeyEvent.VK_COMMA));
        CHAR_MAP.put(78,new KeyboardKeyEventHandler(KeyEvent.VK_SEMICOLON));
        CHAR_MAP.put(79,new KeyboardKeyEventHandler(KeyEvent.VK_OPEN_BRACKET));
        CHAR_MAP.put(80,new KeyboardKeyEventHandler(KeyEvent.VK_CLOSE_BRACKET));
        CHAR_MAP.put(81,new KeyboardKeyEventHandler(KeyEvent.VK_QUOTE));
        CHAR_MAP.put(82,new KeyboardKeyEventHandler(KeyEvent.VK_BACK_QUOTE));
        CHAR_MAP.put(83,new KeyboardKeyEventHandler(KeyEvent.VK_MINUS));
        CHAR_MAP.put(84,new KeyboardKeyEventHandler(KeyEvent.VK_EQUALS));
        CHAR_MAP.put(85,new KeyboardKeyEventHandler(KeyEvent.VK_SLASH));
        CHAR_MAP.put(86,new KeyboardKeyEventHandler(KeyEvent.VK_BACK_SLASH));
        CHAR_MAP.put(87,new KeyboardKeyEventHandler(KeyEvent.VK_NUMBER_SIGN));
        CHAR_MAP.put(88,new KeyboardKeyEventHandler(KeyEvent.VK_PRINTSCREEN));
        CHAR_MAP.put(89,new KeyboardKeyEventHandler(KeyEvent.VK_TAB));

        CHAR_MAP.put(36,new HoldableKeyEventHandler(KeyEvent.VK_SHIFT));
        CHAR_MAP.put(48,new HoldableKeyEventHandler(KeyEvent.VK_CONTROL));
        CHAR_MAP.put(49,new HoldableKeyEventHandler(KeyEvent.VK_ALT));
        CHAR_MAP.put(56,new HoldableKeyEventHandler(KeyEvent.VK_ALT_GRAPH));

        MOUSE_MAP.put(200,new MouseMovementEventHandler());
        MOUSE_MAP.put(201,new MouseMovementEventHandler());
        MOUSE_MAP.put(202,new MouseMovementEventHandler());
        MOUSE_MAP.put(203,new MouseMovementEventHandler());
        MOUSE_MAP.put(204,new MouseClickEventHandler(InputEvent.BUTTON1_MASK));
        MOUSE_MAP.put(205,new MouseClickEventHandler(InputEvent.BUTTON2_MASK));
        MOUSE_MAP.put(206,new MouseClickEventHandler(InputEvent.BUTTON3_MASK));
        MOUSE_MAP.put(207,new MouseClickEventHandler(InputEvent.BUTTON1_MASK));
        MOUSE_MAP.put(208,new MouseClickEventHandler(InputEvent.BUTTON2_MASK));
        MOUSE_MAP.put(209,new MouseClickEventHandler(InputEvent.BUTTON3_MASK));
        MOUSE_MAP.put(210,new MouseScrollEventHandler(-1));
        MOUSE_MAP.put(211,new MouseScrollEventHandler(1));


    }

    private final List<Integer> modifiers = new ArrayList<Integer>();

    protected static boolean volumeHeld = false;

    public ProcessConnectionThread(StreamConnection connection) {
        mConnection = connection;
    }

    public void run() {

        try{
            InputStream inputStream = mConnection.openInputStream();
            Thread modifier = new Thread(new ModifierThread());
            modifier.start();

            System.out.println("Waiting for input.");

            while (true){
                if (parseCommand(inputStream)) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private boolean parseCommand(InputStream inputStream) throws IOException, AWTException {
        int command = inputStream.read();

        if(command == EXIT_CMD) {
            System.out.println("Finish process.");
            ProjectBMAKWindow.applyDeviceName("Waiting for connection.");
            return true;
        }
        processCommand(command);
        return false;
    }

    private void processCommand(int command) throws AWTException {

        System.out.println(command);

        if(command>=1 && command <=89) {
            CHAR_MAP.get(command).handleKeyEvent(modifiers);
        }

        if(command>=200 && command<= 211){
            MOUSE_MAP.get(command).handleMouseEvent(command);
        }

    }

}
