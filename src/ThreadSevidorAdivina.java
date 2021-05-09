import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import static java.util.Collections.sort;

public class ThreadSevidorAdivina implements Runnable {
	/* Thread que gestiona la comunicació de SrvTcPAdivina.java i un cllient ClientTcpAdivina.java */

	Socket clientSocket = null;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;;
	List<Integer> msgEntrant, msgSortint;
	NombreSecret ns;
	boolean acabat;
	int intentsJugador;

	public ThreadSevidorAdivina(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		this.ns = ns;
		acabat = false;
		out= new ObjectOutputStream(clientSocket.getOutputStream());
		in = new ObjectInputStream(clientSocket.getInputStream());
	}

	@Override
	public void run() {
		try {
			while(!acabat) {
				msgEntrant = (List<Integer>) in.readObject();

				msgSortint = generaResposta(msgEntrant);

				out.writeObject(msgSortint);
				out.flush();
			}
		}catch(IOException | ClassNotFoundException e){
			System.out.println(e.getLocalizedMessage());
		}
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Integer> generaResposta(List<Integer> en) {
		List<Integer> lista;
		sort(en);
		return en;
	}

}