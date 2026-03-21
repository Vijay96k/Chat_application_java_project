import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    
private Socket socket;                  //    Connection with client      
    private BufferedReader br;        //Receive messages
    private PrintWriter pw;       //Send messages
    private boolean isActive;   //Check if client is connected
   private String username;        

    public ClientHandler(Socket socket)      //Initializes client data
    {
        this.socket=socket;
        this.isActive=true;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e)
         {
            e.printStackTrace();
    }
}
    public void run()
    {
        String message;

        try
        {
            username=br.readLine();            //Read username first
            System.out.println("username="+username);
            if (username == null) return;

            if (username != null)
            { 
             broadcast(username + " joined the chat");
            }

            while(isActive && (message = br.readLine()) != null)      
            {
                               

                if (message.equalsIgnoreCase("exit"))             //check if clients wants to exit
                    {                     
                    isActive = false;
                    socket.close();

                    System.out.println("Client disconnected: " + socket);
                    break;
                
                     }
                     broadcast(username + ": " + message);
                
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
                broadcast(username + " left the chat");

                socket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
}
}
 private void broadcast(String msg) {
        for (ClientHandler client : ChatServer.ClientList)
             {
                if (client.isActive)
                { 
            client.pw.println(msg);
        }
    }
    }
}