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
        packetNumber = getPacketNumber();
    }

    public int getFileID(){
        return this.fileID;
    }

    public int getStatus(){
        System.out.println(this.statusByte);
        return this.statusByte % 2;
    }

    public boolean isHeader(){
        return this.getStatus() == 0;
    }

    public int getPacketNumber() {
        int part1 = Byte.toUnsignedInt(data[2]);
        int part2 = Byte.toUnsignedInt(data[3]);
        return 256 * part1 + part2;
    }
}