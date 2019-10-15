package main;

import connection.C_Server;
import view.Window;

public class BR_Server {

	public static void main(String[] args) {

		new C_Server().start();
		new Window();

	}

}
