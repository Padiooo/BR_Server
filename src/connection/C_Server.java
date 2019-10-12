package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import controller.Con_Connection;

public class C_Server extends Thread {

	private ServerSocket server = null;
	private Con_Connection con_controller;

	public C_Server() {
		this.con_controller = new Con_Connection();
		try {
			server = new ServerSocket(9999);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			Socket socket = null;
			try {
				socket = server.accept();
				if (socket != null) {
					con_controller.addConnection(socket);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
