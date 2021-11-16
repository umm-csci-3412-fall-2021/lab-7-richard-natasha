package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class DataPacket extends Packet{

    public byte[] packetNum;

    public DataPacket(DatagramPacket packet) {
        super(packet);
        int nameLength = packet.getLength();
        packetNum = Arrays.copyOfRange(packet.getData(), 2, 4);
        data = Arrays.copyOfRange(packet.getData(), 4, 4 + nameLength);
    }

    public byte[] getData() {
        return this.data;
    }

    public boolean isLastPacket() {
        return this.statusByte % 4 == 3;
    }
}