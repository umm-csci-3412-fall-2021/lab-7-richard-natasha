package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class HeaderPacket extends Packet{

    String fileName;

    public HeaderPacket(DatagramPacket packet){
        super(packet);
        packetNumber = -1;
        int nameLength = packet.getLength();
        byte[] byteName = Arrays.copyOfRange(data, 2, nameLength);
        fileName = new String(byteName);
    }

    public String getFileName(){
        return this.fileName;
    }
}