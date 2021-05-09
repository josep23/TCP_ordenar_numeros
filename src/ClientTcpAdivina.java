import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTcpAdivina extends Thread {
/* CLient TCP que ha endevinar un número pensat per SrvTcpAdivina.java */
	String hostname;
	int port;
	boolean continueConnected;
	int intents;
	Scanner sc = new Scanner(System.in);

	public ClientTcpAdivina(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		continueConnected = true;
	}

	public void run() {
		//necesitamos una lista por eso es list integer
		List<Integer> serverData;

		Socket socket;
		ObjectInputStream in;
		ObjectOutputStream out;
		
		try {
			socket = new Socket(InetAddress.getByName(hostname), port);
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			//el client atén el port fins que decideix finalitzar
			while(continueConnected){
				System.out.println("Dime numeros que quieras añadir una vez acabado escribe -1");
				List<Integer> Numeros = new ArrayList<>();
				int salir=1;
				while (salir==1){
					int Respuesta = sc.nextInt();
					if (Respuesta==-1){
						salir++;
					}
					else if (Respuesta!=-1){
						Numeros.add(Respuesta);
						System.out.println("añadido");
					}
				}
				out.writeObject(Numeros);
				out.flush();

				serverData = (List<Integer>) in.readObject();
				getRequest(serverData);
			}
		 	close(socket);
		} catch (UnknownHostException ex) {
			System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("Error de connexió indefinit: " + ex.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void getRequest(List<Integer> serverData) {
		System.out.println(serverData.toString());
		continueConnected = false;
	}
	
	public boolean mustFinish(String dades) {
		if (dades.equals("exit")) return false;
		return true;
		
	}
	
	private void close(Socket socket){
		//si falla el tancament no podem fer gaire cosa, només enregistrar
		//el problema
		try {
			//tancament de tots els recursos
			if(socket!=null && !socket.isClosed()){
				if(!socket.isInputShutdown()){
					socket.shutdownInput();
				}
				if(!socket.isOutputShutdown()){
					socket.shutdownOutput();
				}
				socket.close();
			}
		} catch (IOException ex) {
			//enregistrem l'error amb un objecte Logger
			Logger.getLogger(ClientTcpAdivina.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void main(String[] args) {
		/*if (args.length != 2) {
            System.err.println(
                "Usage: java ClientTcpAdivina <host name> <port number>");
            System.exit(1);
        }*/
 
       // String hostName = args[0];
       // int portNumber = Integer.parseInt(args[1]);
        ClientTcpAdivina clientTcp = new ClientTcpAdivina("localhost",5558);
        clientTcp.start();
	}
}
