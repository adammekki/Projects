import java.io.*; 
import java.net.*; 

public class TCPClient {
	
	public static void main(String argv[]) throws Exception 
	{ 
		String sentence; 
		String modifiedSentence; 
		boolean flag = true;

		BufferedReader inFromUser = 
				new BufferedReader(new InputStreamReader(System.in)); 

		Socket clientSocket = new Socket("192.168.1.29", 6670); 

		DataOutputStream outToServer = 
				new DataOutputStream(clientSocket.getOutputStream()); 
		BufferedReader inFromServer = 
				new BufferedReader(new
						InputStreamReader(clientSocket.getInputStream())); 

		sentence = inFromUser.readLine(); 

		outToServer.writeBytes(sentence + '\n'); 
		
		outToServer.flush();
		
		while(flag){

		modifiedSentence = inFromServer.readLine(); 

		System.out.println("FROM SERVER: " + modifiedSentence); 
		
		if(modifiedSentence == null || modifiedSentence.equalsIgnoreCase("QUIT")) {
             System.out.println("Disconnected from server");
             break;
         }
		sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        outToServer.flush();

		}
		clientSocket.close(); 
	} 
} 



