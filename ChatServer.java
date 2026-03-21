
import java.io.IOException;        //for input-outputStream
import java.net.ServerSocket;     //for seversocket
import java.net.Socket;          //for socket
import java.util.List;          //for list
import java.util.Vector;       //for vector

public class ChatServer {
    
    static List <ClientHandler> ClientList=new Vector<ClientHandler>();
    public static void main(String args[])
    {
        try{
             ServerSocket serversocket =new ServerSocket(8191);  //created a serversocket on port 8191
             System.out.println("Server started successfully");

             while(true)                                              //infinite loop for accepting the clients
             {
                 Socket socket=  serversocket.accept();               //accepts client connections
                 System.out.println("client connected:"+socket);
             
            

                ClientHandler client = new ClientHandler(socket);
                ClientList.add(client);
                Thread t1 =new Thread(client);
                t1.start();
             }
           


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
}
}