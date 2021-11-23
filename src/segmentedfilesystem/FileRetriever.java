package segmentedfilesystem;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;

public class FileRetriever {

        private static InetAddress serverName;
        private static int portNum;
        private static FileOutputStream output;
        DatagramPacket packet;
        DatagramSocket socket = null;
        private static byte[] buf;
        static byte[] lastPackNum;
        public List<ReceivedFile> allPackets = new ArrayList<>();
        HeaderPacket head;
        DataPacket data;
        int numOfFullMaps = 0;
        ReceivedFile newMap;
        boolean IDFound = true;


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
                        // send.connect(serverName, portNum);
                        // socket = new DatagramSocket(portNum);

                        buf = new byte[1028];
                        System.out.println("server name = " + serverName.getHostName() + " and port # = " + portNum);
                        DatagramPacket request = new DatagramPacket(buf, buf.length, serverName, portNum);
                        socket.send(request);
                } catch(SocketException e) {
                        System.out.println("There was a socket exception.");
                } catch(IOException e) {
                        System.out.println("IOException error.");
                }

                System.out.println("Sent empty request packet.");

                //runs as long as all of the maps arent full
                do {
                        System.out.println("At top of do-while loop");
                        //counts how many of the maps are full
                        for(ReceivedFile received : allPackets) {
                                if(received.allPacketsReceived()) numOfFullMaps++;
                        }
                        System.out.println("Done checking for all packets received.");

                        try{ 
                                packet = new DatagramPacket(buf, buf.length);
                                socket.receive(packet);
                                System.out.println("We received a packet!");
                        } catch(IOException e){
                                System.out.println("There was an unexpected error.");
                        }
                        Packet packetType = new Packet(packet);

                        boolean isHead = packetType.isHeader();
                        System.out.println("isHead " + isHead);

                        if(isHead) {
                                head = new HeaderPacket(packet);
                        } else {
                                data = new DataPacket(packet);
                        }
                        System.out.println("This is packet " + packetType.packetNumber + " for file " + packetType.fileID);
                        for(ReceivedFile received : allPackets) {
                                if(received.handledID == packetType.fileID) {

                                        if(isHead) {
                                                received.addPacket(head);
                                        } else {
                                                received.addPacket(data);
                                                if(data.packetNumber > received.maxPackets) {
                                                        received.maxPackets = data.packetNumber;
                                                }
                                        }
                                        IDFound = true;
                                        System.out.println("For file " + received.handledID + " max packets " + received.maxPackets + " current packets " + received.packetsReceived);
                                        received.packetsReceived++;
                                        System.out.println("current size : " + received.files.size());
                                        if(!isHead && data.isLastPacket()){
                                                received.maxPackets = data.packetNumber;
                                        }
                                        break;
                                } else {
                                        IDFound = false;
                                }
                        }
                        System.out.println("is the ID found " + IDFound);
                        if(!IDFound) {
                                newMap = new ReceivedFile();
                                allPackets.add(newMap);
                                if(isHead) {
                                        newMap.addPacket(head);
                                } else {
                                        newMap.addPacket(data);
                                }
                                newMap.handledID = packetType.fileID;
                                newMap.packetsReceived++;
                        }

                        if(allPackets.size() == 0) {
                                newMap = new ReceivedFile();
                                if(isHead) {
                                        newMap.addPacket(head);
                                } else {
                                        newMap.addPacket(data);
                                }
                                allPackets.add(newMap);
                                newMap.handledID = packetType.fileID;
                        }
                        System.out.println("size of allPackets " + allPackets.size());
                        System.out.println("number of full maps " + numOfFullMaps);
                } while(numOfFullMaps != allPackets.size());

                System.out.println("finished the loop");


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

        public void writeToFiles() throws IOException, FileNotFoundException {
                for(ReceivedFile received : allPackets) {
                        //creates an output stream with the given file name
                        output = new FileOutputStream(new File(received.getHeaderPacket().fileName));
                        //goes through each packet and writes the data
                        for(int i = 0; i < received.files.size(); i++) {
                                Packet currentPacket = received.files.get(i);
                                output.write(currentPacket.data);
                        }
                }
                output.close();
        }

}
