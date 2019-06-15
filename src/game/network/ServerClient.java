package game.network;

import java.net.InetAddress;

public class ServerClient {

	private String name;
	private InetAddress address;
	private int port;
	private final int ID;
	private int attempt = 0;
	
	public ServerClient(String name, InetAddress address, int port, int ID) {
		this.name = name;
		this.address = address;
		this.port = port;
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getAttempt() {
		return attempt;
	}

	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	public int getID() {
		return ID;
	}
}
