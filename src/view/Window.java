package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private int size_x = 300;
	private int size_y = 100;

	public Window() {

		setTitle("SERVER");

		setSize(size_x, size_y);

		setLocationRelativeTo(null);

		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBackground(Color.GRAY);
		
		setContentPane(new CleanPanel(size_x, size_y));

		setVisible(true);
	}
}
