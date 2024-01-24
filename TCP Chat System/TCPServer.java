import java.io.*; 
import java.net.*; 


public class TCPServer {
	
	public static void main(String argv[]) throws Exception 
	{ 
		String clientSentence; 
		String capitalizedSentence; 
		boolean flag = true;

		ServerSocket welcomeSocket = new ServerSocket(6670); 

		while(flag) { 

			Socket connectionSocket = welcomeSocket.accept(); 

			BufferedReader inFromClient = 
					new BufferedReader(new
							InputStreamReader(connectionSocket.getInputStream())); 
			DataOutputStream  outToClient = 
					new DataOutputStream(connectionSocket.getOutputStream()); 

			clientSentence = inFromClient.readLine(); 
			
			if(clientSentence.equalsIgnoreCase("CONNECT") && clientSentence != null){

				 System.out.println("Client connected. Start chatting!");
	                capitalizedSentence = clientSentence.toUpperCase() + '\n';
	                outToClient.writeBytes(capitalizedSentence);
	                outToClient.flush();
	                while (true) {
	                    clientSentence = inFromClient.readLine();
	                    System.out.println("Client: " + clientSentence);

	                    if (clientSentence == null || clientSentence.equalsIgnoreCase("QUIT")) {
	                        System.out.println("Client disconnected");
	                        break;
	                    }
	                    capitalizedSentence = clientSentence.toUpperCase() + '\n';
	                    outToClient.writeBytes(capitalizedSentence);
	                    outToClient.flush(); 
	                }
			}
			connectionSocket.close();
		} 
	} 
} 




