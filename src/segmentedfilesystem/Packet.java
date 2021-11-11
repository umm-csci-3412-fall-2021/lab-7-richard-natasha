package segmentedfilesystem;

import java.net.DatagramPacket;

public class Packet{

    public int statusByte;
    public int fileID;
    byte[] data;
    
    public Packet(DatagramPacket packet) {
        data = packet.getData();
        statusByte = data[0];
        fileID = data[1];
    }

    public int getFileID(Packet packet){
        return packet.fileID;
    }

    public int getStatus(Packet packet){
        return packet.statusByte;
    }
}