package segmentedfilesystem;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.DatagramPacket;

public class FileRetriever {

        private static InetAddress serverName;
        private static int portNum;
        DatagramPacket packet;
        DatagramSocket socket = null;


	public FileRetriever(String server, int port) {
        // Save the server and port for use in `downloadFiles()`
        //...
        try {
               serverName = InetAddress.getByName(server); 
        }catch(UnknownHostException e) {
                System.out.println("Unknown host.");
        }
                portNum = port;
	}

	public void downloadFiles() {

                try {
                        socket = new DatagramSocket();

                        byte[] buf = new byte[1028];
                        packet = new DatagramPacket(buf, buf.length, serverName, portNum);
                        socket.send(packet);
                } catch(SocketException e) {
                        System.out.println("There was a socket exception.");
                } catch(IOException e) {
                        System.out.println("IOException error.");
                }

                while()


        // Do all the heavy lifting here.
        // This should
        //   * Connect to the server
        //   * Download packets in some sort of loop
        //   * Handle the packets as they come in by, e.g.,
        //     handing them to some PacketManager class
        // Your loop will need to be able to ask someone
        // if you've received all the packets, and can thus
        // terminate. You might have a method like
        // PacketManager.allPacketsReceived() that you could
        // call for that, but there are a bunch of possible
        // ways.

	}

}
