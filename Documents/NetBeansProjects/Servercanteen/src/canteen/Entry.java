package canteen;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.Random;

public class Entry
{

	
	public static void main(String [] args) throws Exception
	{
		ClientAuthenticateAccept clientAuthenticateAccept=new ClientAuthenticateAccept();
		Thread threadClientAuthenticateAccept=new Thread(clientAuthenticateAccept);
		threadClientAuthenticateAccept.start();
		//new ClientAuthenticateAccept();
	}
}



