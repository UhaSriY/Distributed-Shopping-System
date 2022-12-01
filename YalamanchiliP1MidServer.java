import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.net.UnknownHostException;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.io.*;
public class YalamanchiliP1MidServer 
{
	@SuppressWarnings("resource")
	public static void main(String[] args) 
	{
		int flag=0;	
		Scanner sc=new Scanner(System.in);
		System.out.println("SERVER : Enter the port number");
	        int port = sc.nextInt();
	    	String groupname;
		String pointsStr;
		int points;
	    	try
	    	{
	    		ServerSocket server = new ServerSocket(port);
	    		System.out.println("SERVER : Waiting for client Request on port " + server.getLocalPort() + "...");
			server.setSoTimeout(18000);
	        		Socket socket = server.accept();
	        		DataOutputStream dataOutStream = new DataOutputStream(socket.getOutputStream());
			//DataInputStream inputStream=new DataInputStream(socket.getInputStream());
			dataOutStream.writeUTF("SERVER : Hello Welcome ....");
	        		System.out.println("SERVER : Connection request received and connected to " + socket.getRemoteSocketAddress());
			DataInputStream dataInStream = new DataInputStream(socket.getInputStream());
           	 	String username=dataInStream.readUTF();
           	 	String password=dataInStream.readUTF();
           	 	if(username!=null&&password!=null)
           	 	{
           	 		System.out.println("Username and password are received from client");
            		}
            		FileInputStream fis=new FileInputStream("userList.txt");   
			//FileOutputStream fout=new FileOutputStream("result.txt");
			//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout));
			String resultString = null;			
            		sc=new Scanner(fis);
            		while(sc.hasNextLine())  
      	  		{      						
      		  		String str=sc.nextLine();
      		  		String[] strArray = str.split("\\s+");
      		  		if(strArray[0].equals(username)&&strArray[1].equals(password))
      		  		{
					flag=1;
					resultString=str;
				}
				/*else
				{
					bw.write(str);
					bw.newLine();
				}*/
			}
			fis.close();
			if(flag==0)
			{
				dataOutStream.writeUTF("SERVER :authentication failed");
				dataOutStream.writeInt(flag);
				dataOutStream.writeUTF("N");
			}
			else
			{
				System.out.println("SERVER : Username and password are authenticated succesfully");
		  		dataOutStream.writeUTF("SERVER : Username and Password authenticated succesfully");
		  		dataOutStream.writeInt(flag);
		  		dataOutStream.writeUTF(resultString);
			}
			/*String ch=inputStream.readUTF();    //reading exit option
			if(ch.equals("Y"))
			{
				System.out.println("Successfully closing the connection");
				socket.close();
			}*/
			socket.close();
		}
	    	catch (InterruptedIOException iioe)
		{
			System.err.println("Remote host timed out during read operation");
		}
	    	catch(Exception e)
	    	{
	    		System.out.println("Exception occurred"+e.getMessage());	
	    	}
	}
}

