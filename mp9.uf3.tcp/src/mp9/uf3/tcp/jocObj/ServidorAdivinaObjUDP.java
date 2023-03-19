package mp9.uf3.tcp.jocObj;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ServidorAdivinaObjUDP {
    MulticastSocket socket;
    InetAddress multicastIP;

    int port;
    boolean continueRunning = true;

    Tauler tauler;

    public ServidorAdivinaObjUDP(int portValue, String strIp, Tauler tauler) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        tauler = tauler;
    }

    public void runServer() throws IOException{
        DatagramPacket packet;
        byte [] sendingData;

        while(continueRunning){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(tauler);
            sendingData = baos.toByteArray();

            packet = new DatagramPacket(sendingData, sendingData.length,multicastIP, port);
            socket.send(packet);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.getMessage();
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException{
        Tauler tauler = new Tauler();
        ServidorAdivinaObjUDP servidorMulticast = new ServidorAdivinaObjUDP(5557, "224.0.11.120", tauler);
        servidorMulticast.runServer();
    }
}
