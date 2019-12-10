package canteen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderAuthenticate implements Runnable
	{
		Connection con=null;
		PreparedStatement psSelect=null;
		ResultSet result=null;
		PreparedStatement psSelect1=null;
		String mob;
		int price;
		Socket client = null;
		
		public OrderAuthenticate(Socket client) 
		{
			
			this.client=client;
		}
		
		@Override
		public void run() 
		{
		
//		public static void main(String [] args) throws Exception
//		{
			// TODO Auto-generated method stub
			
			try
			{
//				ServerSocket socket = new ServerSocket(6666);
//				client = socket.accept();
				System.out.println("Connected");
				DataInputStream dis= new DataInputStream(client.getInputStream());
				DataOutputStream dos=new DataOutputStream(client.getOutputStream());		
				StringBuffer buffer=new StringBuffer();
				try
						{
							System.out.println("ok");
							mob=dis.readUTF();
							System.out.println(mob);
							//Connecting to database	
							final String url = "jdbc:mysql://localhost:3306/canteen";
							final String dbUsernm = "Manish";
							final String dbPass = "Manish@123";
										
							Class.forName("com.mysql.jdbc.Driver");
							con= DriverManager.getConnection(url,dbUsernm,dbPass);
							psSelect=con.prepareStatement("select * from users where mobile=?");
							psSelect.setString(1, mob);
							result=psSelect.executeQuery();
							if(result.next())
							{
								int client=result.getInt("clientid");
								//String c=Integer.toString(clientid);
								psSelect1=con.prepareStatement("select * from canteen.ordert where clientid=?");
								psSelect1.setInt(1, client);
								ResultSet result1=psSelect1.executeQuery();
								
								while(result1.next())
								{
									String items=result1.getString("item");
									int quantity=result1.getInt("quantity");
									int totprice=result1.getInt("totalprice");
									//System.out.println("Test data");
									//System.out.println(totprice);
									 price=price+totprice;
									
									
									buffer.append("\nItem:"+items+"\t\tquantity:"+quantity+"\t\tPrice:"+totprice);
								}
								 System.out.println("Total Price:" + price);
								 System.out.println("Buffer" + buffer.toString());
									dos.writeUTF(buffer.toString());
									dos.writeUTF(Integer.toString(price));
								
							}
						}
						catch(Exception e)
						{
							System.out.println(e);
						}
					
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}

		}

