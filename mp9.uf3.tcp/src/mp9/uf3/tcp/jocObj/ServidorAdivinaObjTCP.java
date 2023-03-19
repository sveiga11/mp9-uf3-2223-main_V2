package mp9.uf3.tcp.jocObj;

import mp9.uf3.udp.unicast.joc.SecretNum;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorAdivinaObjTCP {
    /* Servidor TCP que genera un número perquè ClientTcpAdivina_Obj.java jugui a encertar-lo
     * i on la comunicació dels diferents jugadors la gestionaran els Threads : ThreadServidorAdivina_Obj.java
     * */

    private final int port;
    private final SecretNum ns;
    private Tauler t;

    private ServidorAdivinaObjTCP(int port, Tauler tauler) {
        this.port = port;
        ns = new SecretNum(100);
        t = tauler;
    }

    private void listen() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while(true) { //esperar connexió del client i llançar thread
                clientSocket = serverSocket.accept();
                //Llançar Thread per establir la comunicació
                //sumem 1 al numero de jugadors
                t.addNUmPlayers();
                ThreadServidorAdivinaObj FilServidor = new ThreadServidorAdivinaObj(clientSocket, ns, t);
                Thread client = new Thread(FilServidor);
                client.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorAdivinaObjTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) throws IOException {
        Tauler tauler = new Tauler();
        ServidorAdivinaObjTCP servidorTCP = new ServidorAdivinaObjTCP(5558,tauler);
        servidorTCP.listen();
        Thread thread = new Thread(servidorTCP::listen);
        thread.start();
    }
}
