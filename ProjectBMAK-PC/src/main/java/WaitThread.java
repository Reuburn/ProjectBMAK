import com.intel.bluetooth.RemoteDeviceHelper;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Reuben Spiers on 22/02/2016.
 */
public class WaitThread implements Runnable {
    private OnConnectionCallback callback;
    private static OutputStream outputStream = null;


    public WaitThread(OnConnectionCallback mCallback) {
        callback = mCallback;
    }

    public interface OnConnectionCallback{
        void setDeviceNameLabel();
    }

    public String getDeviceName() {
        return deviceName;
    }

    private String deviceName;


    public void run() {
        try {
            waitForConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() throws IOException {
        StreamConnectionNotifier notifier = setupListenServer();
        while(true) {
            StreamConnection connection = startListeningForConnection(notifier);
            RemoteDevice connectedDevice = RemoteDeviceHelper.implGetRemoteDevice(connection);
            deviceName = connectedDevice.getFriendlyName(true);
            callback.setDeviceNameLabel();
            outputStream = connection.openOutputStream();
            startProcessing(connection);
        }
    }

    private void startProcessing(StreamConnection connection) throws IOException {
        Thread processThread = new Thread(new ProcessConnectionThread(connection));
        processThread.start();
    }

    private StreamConnection startListeningForConnection(StreamConnectionNotifier notifier) {
            try {
                System.out.println("Waiting for connection.");
                return notifier.acceptAndOpen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }


    private StreamConnectionNotifier setupListenServer() {
        try {
            LocalDevice local = LocalDevice.getLocalDevice();
            local.setDiscoverable(DiscoveryAgent.GIAC);

            UUID uuid = new UUID(80087355); // "04c6093b-0000-1000-8000-00805f9b34fb"
            String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
            return (StreamConnectionNotifier) Connector.open(url);
        } catch (Exception e) {
            System.out.println("Could not retrieve local bluetooth device.");
        }
        return null;
    }

    public static void sendDisconnect() throws IOException{
        final byte data = 1;

        System.out.println("Broadcasting data (" + data + ").");
        outputStream.write(data);
    }
}
