package canteen;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


	public class TestAuthenticate implements Runnable
	{
		String quant;
		String itemName;
		String mob;
		String msg;
		Connection con=null;
		PreparedStatement psSelect=null;
		PreparedStatement psSelect1=null;
		PreparedStatement psSelect2=null;
		PreparedStatement psSelect3=null;
		PreparedStatement psSelect4=null;
		PreparedStatement psSelect6=null;
		PreparedStatement psSelect7=null;
		PreparedStatement psSelect8=null;
		PreparedStatement psSelect9=null;
		
		ResultSet result=null;
		ResultSet result2=null;
		ResultSet result3=null;
		Socket client = null;
		
		public TestAuthenticate(Socket client) 
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
//				ServerSocket socket = new ServerSocket(5555);
//				client = socket.accept();
				System.out.println("Connected");
				DataInputStream dis= new DataInputStream(client.getInputStream());
				DataOutputStream dos=new DataOutputStream(client.getOutputStream());
				msg=dis.readUTF();
				if(msg.equals("orderr"))
				{
					Order order=new Order(client);
					Thread thread = new Thread(order);
					thread.start();
					/*OrderAuthenticate order=new OrderAuthenticate(client);
					order.run();*/
				}
				else if(msg.equals("place"))
				{
					try
					{
									
									
						//Connecting to database	
						final String url = "jdbc:mysql://localhost:3306/canteen";
						final String dbUsernm = "Manish";
						final String dbPass = "Manish@123";
									
						Class.forName("com.mysql.jdbc.Driver");
						con= DriverManager.getConnection(url,dbUsernm,dbPass);
						String msg1=dis.readUTF();//mobile
						psSelect7=con.prepareStatement("select * from users where mobile=?");
						psSelect7.setString(1, msg1);
						result = psSelect7.executeQuery();
						if(result.next())
						{
							int client=result.getInt("clientid");
							//System.out.println(client);
							String a="Preparing";
							String b="Added-to-cart";
							psSelect9=con.prepareStatement("select * from canteen.ordert where clientid=? and status=?");
							psSelect9.setInt(1, client);
							psSelect9.setString(2,b);
							result3 = psSelect9.executeQuery();
							if(result3.next())
							{
								dos.writeUTF("Fine");
							
							psSelect6=con.prepareStatement("UPDATE canteen.ordert set status=? where clientid=?");
							psSelect6.setString(1,a);
							psSelect6.setInt(2, client);
							psSelect6.executeUpdate();
							
							/*String c=Integer.toString(client);	
							func(c);*/
							String state=null;
							String s="ready";
							do
							{	
							psSelect8=con.prepareStatement("select * from canteen.ordert where clientid=?");
							psSelect8.setInt(1,client);
							result2 = psSelect8.executeQuery();
							if(result2.next())
							{
								
								
								
								 state=result2.getString("status");
								 //System.out.println(state);
								 if(state.equals("ready"))
									 break;
							}
							}while(state!=s);
								
								System.out.println(state);
								dos.writeUTF("OK");
								
							}
							else
							{
								dos.writeUTF("Notplaced");
							}
							}
					}
					
				
					catch(Exception e)
					{
						System.out.println(e);
					}
				}
				else
				{
				itemName=dis.readUTF();
				quant=dis.readUTF();
				 mob=dis.readUTF();
						try
						{
										
										
							//Connecting to database	
							final String url = "jdbc:mysql://localhost:3306/canteen";
							final String dbUsernm = "Manish";
							final String dbPass = "Manish@123";
										
							Class.forName("com.mysql.jdbc.Driver");
							con= DriverManager.getConnection(url,dbUsernm,dbPass);
										
										
							psSelect=con.prepareStatement("select * from menu where itemname=? and quantity>=?");
							psSelect.setString(1, itemName);
							psSelect.setString(2, quant);
							result = psSelect.executeQuery();
										

							
							
							
							//DataOutputStream dos = new DataOutputStream(client.getOutputStream());
							if(result.next())
							{
								dos.writeUTF("Added to cart");	
								System.out.println("ok");
								int x=result.getInt("quantity");
								int z=result.getInt("price");
								int fin=x-Integer.parseInt(quant);
								psSelect2=con.prepareStatement("UPDATE menu set quantity="+fin+" where itemname=?");
								psSelect2.setString(1, itemName);
								psSelect2.executeUpdate();
								psSelect4=con.prepareStatement("select * from users where mobile=?");
								psSelect4.setString(1, mob);
								ResultSet result4=psSelect4.executeQuery();
								
								
								int y;
								if(result4.next())
								{
									y=result4.getInt("clientid");
								
								int totprice=z*Integer.parseInt(quant);
								//System.out.println("ad");
								String msg3="Added-to-cart";
									psSelect3=con.prepareStatement("INSERT INTO canteen.ordert(clientid,orderid,item,quantity,totalprice,status) VALUES('"+y+"','"+y+"','"+itemName+"','"+quant+"','"+totprice+"','"+msg3+"')");
									psSelect3.execute();
									psSelect3=con.prepareStatement("INSERT INTO canteen.details(clientid,orderid,item,quantity,totalprice) VALUES('"+y+"','"+y+"','"+itemName+"','"+quant+"','"+totprice+"')");
									psSelect3.execute();
								}
								//Order order=new Order();
								//Thread thread=new Thread(order);
								//order.run();
								//thread.start();
								//OrderAuthenticate order=new OrderAuthenticate(client);
								//order.run();
							}
							else
							{
								psSelect1=con.prepareStatement("select * from menu where itemname=?");
								psSelect1.setString(1, itemName);
								ResultSet result1=psSelect1.executeQuery();
								if(result1.next())
								{
								System.out.println(result1.getInt(3));
								dos.writeUTF(result1.getInt("quantity")+" quantities left");
								}
								/*Order order=new Order();
								order.run();*/
								/*OrderAuthenticate order=new OrderAuthenticate(client);
								order.run();*/
							}
										
							/*if(con!=null)
							{
										System.out.println("Connection Sucessful");

									
							}*/
						}
						catch(Exception e)
						{
							System.out.println(e);
						}
				}	
			}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		
		/*void func(String id)
		{
			try
			{
				
				DataOutputStream dos=new DataOutputStream(client.getOutputStream());
		String new_status="ready";
		psSelect8=con.prepareStatement("select * from canteen.order where status=? and clientid=?");
		psSelect8.setString(1,new_status);
		psSelect8.setString(2,id);
		result2 = psSelect8.executeQuery();
		if(result2.next())
		{
			System.out.println(new_status);
			dos.writeUTF("Order is ready");
		}
		else
		{
			//psSelect8.close();
			
			//System.out.println("no data");
			func(id);
		}
		}
			catch(Exception e)
		{
				e.printStackTrace();
		} 
	}*/
	}

		
	
