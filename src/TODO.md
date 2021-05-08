### TCP

> **Algunes característiques del TCP**
> - Protocol de la capa de Transport
> - Intenta assegurar que totes les dades de l'origen arribin al destí, i en el mateix ordre
> - El diàleg s'inicia amb una petició de connexió
> - És orientat a la connexió
> - La comunicació és d'un a un (a diferència del UDP que un mateix servidor responia a molts)
> - És **full duplex**, dos canals: un per escoltar i l'altre per enviar

Classes necessàries per realitzar connexions TCP amb java:
``` java
ServerSocket //crea el socket en la banda servidor
Socket //crea el socket en la banda del client

//fluxos que hem de connectar
Client: InputStream <--- OutputStream
Servidor: OutputStream ---> InputStream

//Despres podem connextar-hi els embellidors que 
//més ens agradin o que més bé ens vagin
PrintStream
BufferedReader
etc...  
```

Ara, oberserveu els exemples d'aquest directori. El joc és el mateix que
en l'exemple d'UDP del directori a3 i a4. En aquest cas, client i servidor s'intercanvien
missatges mitjaçant Strings.

L'aspecte més important però, i a diferència de UDP, és que com que
la comunicació és *un a un*, cada vegada que un client vol parlar amb el servidor, aquest
ha de crear un procés que s'ocupi d'aquesta comunicació.
Per tant, el servidor tidrà dues parts; Una en la que espera les petions (accept) i 
l'altre el procés de comunicació en sí.

`ClientTcpAdivina.java`
``` java
//Enllaç dels canals en el client on socket conté l'@ i la IP del server
in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
out = new PrintStream(socket.getOutputStream());
```
`ThreadServidorAdivina.java`
``` java
//Enllaç dels canals en el servidor on clientsocket conté 
//les dades(port i ip) del client que ha recuperat del mètode accept()
in =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
out= new PrintStream(clientSocket.getOutputStream());
```
`SrvTcpAdivina.java`
``` java
//Part del servidor on s'espera a que un client s'hi connecti
serverSocket = new ServerSocket(port);
while(true) {           //espera connexió del client i llançar thread
	clientSocket = serverSocket.accept();
	//Llançar Thread per establir la comunicació
	ThreadSevidorAdivina FilServidor = new ThreadSevidorAdivina(clientSocket, ns);
	Thread client = new Thread(FilServidor);
	client.start();
}
```

>Per provar el codi, cal arrecar el servidor:  
>**`SrvTcpAdivina.java`**  
>i les vegades que es vulgui el client:  
>**`ClientTcpAdivina.java`**  

## Exercici
Escriu un Servidor i un Client que es comuniquin amb TCP amb kes següents funcions:
1. El client li envia un objecte [`Llista.java`](Llista.java) al servidor amb un nom i una llista de números desordenats  
2. El Servidor li retorna la llista amb els números ordenats i sense repetits.
