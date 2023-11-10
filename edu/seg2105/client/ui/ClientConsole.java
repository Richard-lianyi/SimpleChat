package edu.seg2105.client.ui;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.Scanner;

import edu.seg2105.client.backend.ChatClient;
import edu.seg2105.client.common.*;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 */
public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;
  
  
  
  /**
   * Scanner to read from the console
   */
  Scanner fromConsole; 

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole((String id, String host, int port) 
  {
    try 
    {
      client= new ChatClient(host, port, this);
      
      
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
             + " Terminating client.");
      System.exit(1);
    }
    
    // Create scanner object to read from console
    fromConsole = new Scanner(System.in); 
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
    try
    {

      String message;

      while (true) 
      {
       message = fromConsole.nextLine();
        if(message.equals("#quit")) {
        	client.quit();
        }
        else if(message.equals("#logoff")) {
        	client.closeConnection();
        }
		else if(message.equals("#login")) {
        	if(!client.isConnected()) {
        		client.openConnection();
        	}else {
        		System.out.println("You are already logged in");
        	}
        	
        }
        else if(message.startsWith("#sethost")) {
        	String host = message.substring("#sethost".length()); 
        
        	if(!client.isConnected()) {
        		client.setHost(host);
            	System.out.println("Setting host to: " + host);
        	}else {
        		System.out.println("Can not set host when connected");
        	}
        }
		else if(message.equals("#gethost")) {
        	System.out.println(client.getHost());        	
        }
        else if(message.equals("#getport")) {
        	System.out.println(client.getPort());
        }
        else if(message.startsWith("#setport")) {
        	int port = Integer.parseInt(message.substring("#setport".length())); 
        	if(!client.isConnected()) {
        		client.setPort(port);
            	System.out.println("Setting port to: " + port);
        	}else {
        		System.out.println("Can not set port when connected");
        	}
        	
        }
        
        
        else {
        	client.handleMessageFromClientUI(message);
        }
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("> " + message);
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
	String loginID="";  
    String host = "";
    int port=0;


    try
    { 
      loginID = args[0];
      host = args[1];
      port = Integer.parseInt(args[2]);
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
      port = DEFAULT_PORT;
	  id = "";
    }
    catch(NumberFormatException ne) {
    	port= DEFAULT_PORT;
    }
    ClientConsole chat= new ClientConsole(host, DEFAULT_PORT);
    chat.accept();  //Wait for console data
  }
}
//End of ConsoleChat class
