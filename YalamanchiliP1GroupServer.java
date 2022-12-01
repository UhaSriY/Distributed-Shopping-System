import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
public class YalamanchiliP1GroupServer
{
	@SuppressWarnings("resource")
	public static void main(String[] args) 
	{
		try 
		{
			ServerSocket serSocket=new ServerSocket(16952);
			Socket socket=serSocket.accept();
			DataOutputStream outputStream=new DataOutputStream(socket.getOutputStream());
			DataInputStream inputStream=new DataInputStream(socket.getInputStream());
			outputStream.writeUTF("GROUP SERVER : Hello Client");
			String groupName=inputStream.readUTF();
			int points=inputStream.readInt();
			System.out.println("group name = "+groupName+"  points  ="+points);
			if(groupName!=null)
			{
				if(groupName.equalsIgnoreCase("Platinum"))
				{
					String menu="  SERVER \n"
							+ "Platinum Menu\n"
							+ ""
							+ "1	Watch		100\n"
							+ "2	Mobile		1000\n"
							+ "3	Laptop		1700\n";
					outputStream.writeUTF(menu);
					
				}
				else if(groupName.equalsIgnoreCase("Gold"))
				{
					String menu=" SERVER \n"
							+ "Gold Menu\n"
							+ ""
							+ "1	Watch		100\n"
							+ "2	Mobile		1000\n"
							+ "3	Laptop		1700\n";
					outputStream.writeUTF(menu);
					
				}
				else if(groupName.equalsIgnoreCase("Silver"))
				{
					String menu="	SERVER \n"
							+ "  Silver Menu\n"
							+ ""
							+ "1	Watch		100\n"
							+ "2	Mobile		1000\n"
							+ "3	Laptop		1700\n";
					outputStream.writeUTF(menu);
					
				}
				//socket.setSoTimeout(6000);
				int option=inputStream.readInt();		//reading the option
				int quantity=inputStream.readInt();		//reading the quantity
				int total = 0;
				int flag=0;
				switch(option)
				{
						case 1:
								if(quantity==1)
									System.out.println("You added "+quantity+" watch to your purchase");
								else
									System.out.println("You added "+quantity+" watch to your purchase");
								total=quantity*100;
								break;
						case 2:
								if(quantity==1)
									System.out.println("You added "+quantity+" mobile to your purchase");
								else
									System.out.println("You added "+quantity+" mobiles to your purchase");
								total=quantity*1000;
								break;
						case 3:
								if(quantity==1)
									System.out.println("You added "+quantity+" laptop to your purchase");
								else
									System.out.println("You added "+quantity+" laptops to your purchase");
								total=quantity*1700;
								break;
						default:
								System.out.println("case default ");
								System.out.println("option =" +option);
								flag=1;
								break;
				}
				if(flag==0)		
				{
					outputStream.writeInt(total);
					System.out.println("Successfully updated the list with the available credit score");
				}
				else
				{
					System.out.println("Please select in the given options");
					outputStream.writeInt(0);
				}
				String ch=inputStream.readUTF();    //reading exit option
				if(ch.equals("Y"))
				{
					System.out.println("Successfully closing the connection");
					socket.close();
				}	
			} 
		}
		catch (IOException e) 		
		{
				e.printStackTrace();    
		}
	    
	    
	}

}