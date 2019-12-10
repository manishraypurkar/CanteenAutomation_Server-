package canteen;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientAuthenticateAccept implements Runnable{

	public ClientAuthenticateAccept() {
		
	}
	
	@Override
	public void run()
	{
		
		// TODO Auto-generated method stub
		try
		{
			System.out.println("Server Started");
		ServerSocket server=new ServerSocket(3334);
			while (true) 
			{
				Socket client = server.accept();
				System.out.println("Client Connected");
				ClientAuthenticate clientAuthenticate=new ClientAuthenticate(client);
				Thread threadClientAuthenticate=new Thread(clientAuthenticate);
				threadClientAuthenticate.start();
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}

	}

}

