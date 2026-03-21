import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    
private Socket socket;                  //    Connection with client      
    private DataInputStream dis;        //Receive messages
    private DataOutputStream dos;       //Send messages
    private boolean isActive;           //Check if client is connected

    public ClientHandler(Socket socket,DataInputStream dis,DataOutputStream dos)      //Initializes client data
    {
        this .socket=socket;
        this.dis=dis;
        this.dos=dos;
        this.isActive=true;
    }
    public void run()
    {
        String message;

        try
        {
            while(isActive)      
            {
                message=dis.readUTF();                  //reads message from clients

                if (message.equalsIgnoreCase("exit"))             //check if clients wants to exit
                    {                     
                    isActive = false;
                    socket.close();

                    System.out.println("Client disconnected: " + socket);
                    break;
                
                     }
                for (ClientHandler client : ChatServer.ClientList)                   // Broadcast message to all clients
                    {     
                    if (client != this && client.isActive)
                         {
                        client.dos.writeUTF("Message is: " + message);
                         }

                     }
           }
        }
        catch(IOException e)
        {
         System.out.println("client disconnected unexpectedly.");
        }
        finally
        { 
            
            try {
                ChatServer.ClientList.remove(this);
                socket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
}
}
    }