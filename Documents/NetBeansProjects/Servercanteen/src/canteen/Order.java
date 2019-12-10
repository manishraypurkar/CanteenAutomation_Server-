
package canteen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Order implements Runnable
{
	Socket client;
	public Order(Socket client) {
		this.client = client;
	}
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		/*try(ServerSocket server=new ServerSocket(6667))
		{
			while (true) 
			{
				Socket client = server.accept();
				System.out.println("Client Connected");*/
				OrderAuthenticate orderAuthenticate=new OrderAuthenticate(client);
				//OrderAuthenticate.run();
				Thread threadOrderAuthenticate=new Thread(orderAuthenticate);
				threadOrderAuthenticate.start();
			/*}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}*/

	}
}

