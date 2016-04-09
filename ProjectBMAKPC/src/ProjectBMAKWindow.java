

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Window.Type;

public class ProjectBMAKWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProjectBMAKWindow window = new ProjectBMAKWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
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
		
		JButton btn_disconnect = new JButton("Disconnect");
		btn_disconnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SocketOpener btSocket = new SocketOpener();
			}
		});
		btn_disconnect.setBounds(10, 36, 179, 23);
		frame.getContentPane().add(btn_disconnect);
		
		JLabel lbl_connectedDevice = new JLabel("Connected Device: ");
		lbl_connectedDevice.setBounds(10, 11, 179, 14);
		frame.getContentPane().add(lbl_connectedDevice);
	}
}
