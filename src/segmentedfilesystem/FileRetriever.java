package segmentedfilesystem;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.io.IOException;
import java.net.DatagramPacket;

public class FileRetriever {

        private static InetAddress serverName;
        private static int portNum;
        DatagramPacket packet;
        DatagramSocket socket = null;
        private static byte[] buf;
        private DataPacket dataPacket;
        static byte[] lastPackNum;
        public List<ReceivedFile> allPackets = new ArrayList<>();
        HeaderPacket head;
        DataPacket data;


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
                        socket.connect(serverName, portNum);

                        buf = new byte[1028];
                        packet = new DatagramPacket(buf, buf.length, serverName, portNum);
                        socket.send(packet);
                } catch(SocketException e) {
                        System.out.println("There was a socket exception.");
                } catch(IOException e) {
                        System.out.println("IOException error.");
                }

                while(!ReceivedFile.allPacketsReceived()) {
                        try{ 
                                socket.receive(packet);
                        } catch(IOException e){
                                System.out.println("There was an unexpected error.");
                        }
                        Packet packetType = new Packet(packet);

                        boolean isHead = packetType.isHeader();

                        if(isHead) {
                                head = new HeaderPacket(packet);
                        } else {
                                data = new DataPacket(packet);
                        }

                        for(ReceivedFile received : allPackets) {

                                if(received.files.containsValue(packetType.fileID)) {

                                        if(isHead) {
                                                received.addPacket(head);
                                        } else {
                                                received.addPacket(data);
                                        }
                                } else {
                                        ReceivedFile newMap = new ReceivedFile();

                                        if(isHead) {
                                                newMap.addPacket(head);
                                        } else {
                                                newMap.addPacket(data);
                                        }
                                        allPackets.add(newMap);

                                }
                                received.packetsReceived++;

                        if(dataPacket.isLastPacket()){
                                lastPackNum = dataPacket.packetNum;
                        }
                        packet = new DatagramPacket(buf, buf.length);
                }


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
