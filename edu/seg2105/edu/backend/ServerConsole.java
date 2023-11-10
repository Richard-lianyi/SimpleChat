package server.backend;

import java.io.*;
import java.util.Scanner;

import edu.seg2105.client.common.*;
import edu.seg2105.client.ui.ClientConsole;
import edu.seg2105.edu.server.backend.EchoServer;

public class ServerConsole implements ChatIF{
	EchoServer server;
	
	Scanner fromConsole;
	
	
	
	final public static int DEFAULT_PORT = 5555;

	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		System.out.println("> " + message);
	}
	
	public ServerConsole(int port) {
		try {
			server = new EchoServer(port,this);
		}
		catch(IOException exception){
			System.out.println("Error: Can't setup server!"
	                + " Terminating server.");
	      System.exit(1);
		}
		fromConsole = new Scanner(System.in); 
		
	}
	
	public void accept(){
	    try{

	      String message;

	      while (true) 
	      {
	    	//no space allowed for commands
	        message = fromConsole.nextLine();
	        
	        if(message.equals("#quit")) {
	        	server.close();
	        	System.exit(0);
	        }
	        else if(message.equals("#stop")) {
	        	server.stopListening();
	        }
	        else if(message.equals("#close")) {
	        	server.close();
	        }
	        else if(message.startsWith("#setport")) {
	        	int port = Integer.parseInt(message.substring("#setport".length())); 
	        	if(!server.isListening()) {
	        		server.setPort(port);
	            	System.out.println("Setting port to: " + port);
	        	}else {
	        		System.out.println("Can not set port when listening");
	        	}
	        }
	        else if(message.equals("#start")) {
	        	server.listen();
	        }    
	        else if(message.equals("#getport")) {
	        	server.getPort();
	        }
	        
	        else {
	        	display(message);
	        	server.sendToAllClients("<SERVER MSG> "+ message);
	        } 
	      }
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println
	        ("Unexpected error while reading from console!");
	    }
	  }
	
	public static void main(String[] args) 
	  {
	    int port = 0; //Port to listen on

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
		

	    ServerConsole chat= new ServerConsole(port);
	    
	    try 
	    {
	      chat.server.listen(); //Start listening for connections
	      
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println("ERROR - Could not listen for clients!");
	    }
	    chat.accept();  //Wait for console data
	    
	    
	  }
}