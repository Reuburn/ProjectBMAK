import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Window.Type;
import java.io.IOException;

public class ProjectBMAKWindow {

	private static WaitThread waitThread = new WaitThread(new WaitThread.OnConnectionCallback() {
			public void setDeviceNameLabel() {
			ProjectBMAKWindow.applyDeviceName(waitThread.getDeviceName());
		}
	});

	static JLabel lbl_connectedDevice = new JLabel("Waiting for connection.");

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Thread btWaitThread = new Thread(waitThread);
					ProjectBMAKWindow window = new ProjectBMAKWindow();
					window.frame.setVisible(true);
					btWaitThread.start();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Machine does not support bluetooth.");
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ProjectBMAKWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setType(Type.UTILITY);
		frame.setBounds(100, 100, 215, 112);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				try {
					waitThread.sendDisconnect();
				}
				catch (IOException e){
				}
				System.exit(0);
			}
		});

		JButton btn_disconnect = new JButton("Disconnect");
		btn_disconnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					waitThread.sendDisconnect();
					lbl_connectedDevice.setText("Waiting for connection.");
				} catch (IOException e) {
					System.out.println("No device connected.");
				}
			}
		});
		btn_disconnect.setBounds(10, 36, 179, 23);
		frame.getContentPane().add(btn_disconnect);

		lbl_connectedDevice.setBounds(10, 11, 179, 14);
		frame.getContentPane().add(lbl_connectedDevice);
	}

	public static void applyDeviceName(String deviceName){
		System.out.println(deviceName);
		lbl_connectedDevice.setText(deviceName);

	}
}
