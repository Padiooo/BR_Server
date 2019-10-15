package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JPanel;

public class CleanPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int size_x;
	private int size_y;

	String ip = null;

	public CleanPanel(int size_x, int size_y) {
		this.size_x = size_x;
		this.size_y = size_y;
		try {
			ip = InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			System.exit(0);
		}
	}

	public void paintComponent(Graphics g) {

		g.setFont(new Font("Arial", Font.PLAIN, 30));

		int index = g.getFont().getSize() * ip.length() / 3;

		g.clearRect(0, 0, size_x, size_y);

		g.setColor(Color.GRAY);
		g.fillRect(0, 0, size_x, size_y);

		g.setColor(Color.BLACK);
		g.drawString(ip, 0, size_y / 2);

	}
}
