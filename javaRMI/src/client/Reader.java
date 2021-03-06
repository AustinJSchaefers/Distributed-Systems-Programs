package client;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import inputport.nio.manager.NIOManagerFactory;
import stringProcessors.HalloweenCommandProcessor;

public class Reader implements Runnable {
	private ArrayBlockingQueue<ByteBuffer> readRequests;
	private HalloweenCommandProcessor commandProcessor;
	
	public Reader(ArrayBlockingQueue<ByteBuffer> readRequests, HalloweenCommandProcessor commandProcessor) {
		this.readRequests = readRequests;
		this.commandProcessor = commandProcessor;
		
	}

	@Override
	public void run() {
		ByteBuffer msg = null;
		while (true) {
			try {
				msg = readRequests.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int length = msg.array().length;
			String cmd = new String(msg.array(), msg.position(), length);
			System.out.println("Client receives " + cmd + " from server");
			commandProcessor.processCommand(cmd);
		}
	}
}