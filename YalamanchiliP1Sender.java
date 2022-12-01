import java.io.*;
import java.net.*;
import java.util.Scanner;
public class YalamanchiliP1Sender
{
	@SuppressWarnings("resource")
	public static void main(String[] args) 
	{
		String groupName;
		String pointsStr;
		String name;
		String pword;
		int points,updatedPoints;	
		Scanner sc=new Scanner(System.in);
		System.out.println("CLIENT : Enter Server name");
		String serverName =sc.next();
		System.out.println("CLIENT : Enter Port Number");
	    	int port = sc.nextInt();
	   	try(Socket clientObject = new Socket(serverName, port)) 
		{
			File fileName = new File("Result.txt");
		  	//BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
	  		System.out.println("CLIENT : Trying to Connect  " + serverName + " on port " + port);
	  		DataInputStream in = new DataInputStream(clientObject.getInputStream());
	         	DataOutputStream dataOutStream = new DataOutputStream(clientObject.getOutputStream());
			System.out.println(in.readUTF());
	         	System.out.println("CLIENT : Enter Username");
	 	     	String userName =sc.next();
	 	   	System.out.println("CLIENT : Enter Password");
	 	    	String password =sc.next();
	         	dataOutStream.writeUTF(userName);
	         	dataOutStream.writeUTF(password);
	         	System.out.println(in.readUTF());	//reading and printing authentication message
	         	int flag=in.readInt();				//reading authentication flag
	        	 	String resultString=in.readUTF();// authenticated old string
	         	if(flag==0)
	         	{
	        	 		System.out.println("Authentication failed Please restart the program");
	         	}
	         	else
	         	{
	        	 		String[] strArray = resultString.split("\\s+");
				name=strArray[0];
				pword=strArray[1];
	        	 		groupName=strArray[2];
				pointsStr=strArray[3];
				points=Integer.parseInt(pointsStr);
	        	 		updatedPoints= groupServerConnect(groupName,points);
	        	 		String str = resultString.replaceAll(pointsStr,String.valueOf(updatedPoints)); //updtaed string with result
				writingToFile(name,pword,pointsStr,String.valueOf(updatedPoints),str); //calling method to rewriting to file-cols:username,password,oldpoints,newpoints
	        	 		/*bw.write(str);
			 	bw.newLine();
			 	bw.close();*/
	         	}
	         	clientObject.close();
	      	}
		catch(UnknownHostException ex) 
		{
			System.out.println("Exception occurred Server not found: " + ex.getMessage());
	        	}
	      	catch(Exception e)
	      	{
			e.printStackTrace();
	      	}
	}
	private static void writingToFile(String username, String password,String oldPointsString,String newPointsString,String totalString)
	{
		File fileToBeModified = new File("userList.txt");
		String oldData = "";
       		BufferedReader reader = null;
        		FileWriter writer = null;
		try
        		{
            		reader = new BufferedReader(new FileReader(fileToBeModified));
            		String line = reader.readLine();
			while (line != null) 
            		{	
				if(line.contains(password)&&line.contains(username))
				{
					line=totalString;
					oldData = oldData + line + System.lineSeparator();
				}
				else
				{
					oldData = oldData + line + System.lineSeparator();
				}
                			line = reader.readLine();
           		}
			writer = new FileWriter(fileToBeModified);
            		writer.write(oldData);
            		reader.close();
            		writer.close();
        		}
		catch(Exception e)
		{
			System.out.print(e.getLocalizedMessage());
		}
	}
	private static int groupServerConnect(String groupName, int points) 
	{
		int port=16952;
		String serverName="localhost";
		try 
		{
			Socket socket=new Socket(serverName,port);
			DataOutputStream outputStream=new DataOutputStream(socket.getOutputStream());
			DataInputStream inputStream=new DataInputStream(socket.getInputStream());
			Scanner sc=new Scanner(System.in);
			System.out.println(inputStream.readUTF());    //Hello Client Msg
			outputStream.writeUTF(groupName);
			outputStream.writeInt(points);
			System.out.println(inputStream.readUTF());		//Menu
			System.out.println("Enter Option");
			int option=sc.nextInt();
			outputStream.writeInt(option);
			System.out.println("Enter quantity required");
			int quantity=sc.nextInt();
			outputStream.writeInt(quantity);
			int total=inputStream.readInt();
			System.out.println("Total purchase value = "+total);			//total value
			//System.out.println("Your credit points is going to be verified and if available the points will be deducted according to the purchase");
			if(total<=points)
			{
				points=points-total;
				System.out.println("Purchase is successful and the available points are "+points+" which you can use for future purchase");
			}
			else
			{
				System.out.println("The credit points are not sufficient to make purchase");
			}	
			System.out.println("Shopping is done successfully. Please enter Y to close the connections:");
			String ch=sc.next();
			outputStream.writeUTF(ch);
			if(ch.equals("Y"))
			{	
				System.out.println("Successfully closing the connection");
				socket.close();
			}
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return points;
	}

}
