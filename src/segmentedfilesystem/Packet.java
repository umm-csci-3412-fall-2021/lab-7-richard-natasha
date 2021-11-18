package segmentedfilesystem;

import java.net.DatagramPacket;

public class Packet{

    public int statusByte;
    public int packetNumber;
    public int fileID;
    byte[] data;
    
    public Packet(DatagramPacket packet){
        data = packet.getData();
        statusByte = data[0];
        fileID = data[1];
    }

    public int getFileID(){
        return this.fileID;
    }

    public int getStatus(){
        return this.statusByte;
    }

    public boolean isHeader(){
        return this.getStatus() == 0;
    }
}