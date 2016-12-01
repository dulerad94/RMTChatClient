import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
	private Socket socket;
	private PrintStream outputStream;
	private BufferedReader inputStream;
	private KeyboardCommunication keyboard;
	private boolean end;
	private Thread thread;
	
	 public Client() {
		thread=new Thread(this);
		thread.start();
	}
	@Override
	public void run() {
		try {
			socket = new Socket("localhost", 8090);
			outputStream = new PrintStream(socket.getOutputStream());
			inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			keyboard=new KeyboardCommunication();
			
			String sentence;
			while ((sentence = inputStream.readLine()) != null) {
				System.out.println(sentence);
				if (sentence.equals("Goodbye")) {
					end = true;
					return;
				}
				
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class KeyboardCommunication implements Runnable {
		private BufferedReader inputStream;
		private Thread thread;
		private KeyboardCommunication() {
			thread=new Thread(this);
			thread.start();
		}
		@Override
		public void run() {
			inputStream = new BufferedReader(new InputStreamReader(System.in));

			try {
				while (!end) {
					outputStream.println(inputStream.readLine());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
