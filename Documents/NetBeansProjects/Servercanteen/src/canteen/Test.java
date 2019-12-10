package canteen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Test implements Runnable{

	
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		try(ServerSocket server=new ServerSocket(5556))
		{
			while (true) 
			{
				Socket client = server.accept();
					
				System.out.println("Client Connected");
				
				TestAuthenticate testAuthenticate=new TestAuthenticate(client);
				//TestAuthenticate.run();
				Thread threadTestAuthenticate=new Thread(testAuthenticate);
				threadTestAuthenticate.start();
				
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}

	}

}


