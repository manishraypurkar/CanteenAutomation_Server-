package canteen;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.Random;

public class ClientAuthenticate implements Runnable
{

	int ch = 0;
	Connection con=null;
	PreparedStatement psSelect=null;
	PreparedStatement psSelect1=null;
	ResultSet result=null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	Socket client = null;
	
	public ClientAuthenticate(Socket client) 
	{
		
		this.client=client;
	}
	
	@Override
	public void run() 
	{
	
//	public static void main(String [] args) throws Exception
//	{
		// TODO Auto-generated method stub
		
		try
		{
//			ServerSocket socket = new ServerSocket(4444);
//			client = socket.accept();
			System.out.println("Connected");
			DataInputStream dis= new DataInputStream(client.getInputStream());
			ch=dis.readInt();
			System.out.println("\nClient Message : " + ch);
			
			switch(ch)
			{
				case 1: 
				{
					try
					{
						System.out.println("\nEntered Login");
									
						String userId = dis.readUTF();
						String password = dis.readUTF();
									
						//Message received from client
						System.out.println("Mobile : " + userId);
						System.out.println("Password : " + password);
									
									
						//Connecting to database	
						final String url = "jdbc:mysql://localhost:3306/canteen";
						final String dbUsernm = "Manish";
						final String dbPass = "Manish@123";
									
						Class.forName("com.mysql.jdbc.Driver");
						con= DriverManager.getConnection(url,dbUsernm,dbPass);
									
									
						psSelect=con.prepareStatement("select * from users where mobile=? and password=?");
						psSelect.setString(1, userId);
						psSelect.setString(2, password);
						result = psSelect.executeQuery();
									
						DataOutputStream dos = new DataOutputStream(client.getOutputStream());
						if(result.next())
						{
							int clientId=result.getInt(1);
							System.out.println(result.getInt(1));
							System.out.println("Success");
							dos.writeUTF("Login");
							Test test=new Test();
							Thread thread=new Thread(test);
							thread.start();
							
							//test.run();
							
						}
						else
						{
							System.out.println("Failure");
							dos.writeUTF("Failure");
						}
									
						if(con!=null)
						{
									System.out.println("Connection Sucessful");
						}
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
					finally
					{
						try
						{
							dis.close();
							con.close();
						}
						catch(Exception e)
						{
							System.out.println(e);
						}
					}
				}break;
				case 2:
				{
					try
					{
						System.out.println("\nEntered Register");
						
						//Accepting data to be inserted
						//UserId
						String message = dis.readUTF();
								
						//Password
						String message2 = dis.readUTF();
								
						//Mobile
						String message3 = dis.readUTF();
						
						//Message received from client
						System.out.println("Message1 : " + message);
						System.out.println("Message2 : " + message2);
						System.out.println("Message3 : " + message3);
						
						//DataOutputStream dos = new DataOutputStream(client.getOutputStream());
							
						//Connecting to database	
						final String url = "jdbc:mysql://localhost:3306/canteen";
						final String dbUsernm = "Manish";
						final String dbPass = "Manish@123";
								
						Class.forName("com.mysql.jdbc.Driver");
						con= DriverManager.getConnection(url,dbUsernm,dbPass);
								
						psSelect=con.prepareStatement("select * from users where mobile=?");
						psSelect.setString(1, message3);
						result = psSelect.executeQuery();
						
						
						DataOutputStream dos1 = new DataOutputStream(client.getOutputStream());
						if(result.next())
						{
							//System.out.println("Success");
							dos1.writeInt(1);
							System.out.println("Account Exists");
						}
						else
						{
							psSelect1=con.prepareStatement("INSERT INTO users(username,password,mobile) VALUES('"+message+"','"+message2+"','"+message3+"')");
							psSelect1.execute();
							dos1.writeInt(2);
							System.out.println("Account created");
						}
								
						if(con!=null)
						{
							System.out.println("Connection Sucessful");
						}
					}
					catch(Exception e)
					{
						System.out.println(e);
						e.printStackTrace();
					}
					finally
					{
						try
						{
							dis.close();
							con.close();
						}
						catch(Exception e)
						{
							System.out.println(e);
						}
					}
					
				}break;
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}
