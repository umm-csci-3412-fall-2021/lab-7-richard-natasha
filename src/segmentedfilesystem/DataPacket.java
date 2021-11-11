package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class DataPacket extends Packet{

    public DataPacket(DatagramPacket packet) {
        super(packet);
        int nameLength = packet.getLength();
        data = Arrays.copyOfRange(packet.getData(), 4, 4 + nameLength);
    }

    public byte[] getData(DataPacket packet) {
        return packet.data;
    }

    public boolean isLastPacket(DataPacket packet) {
        return packet.statusByte % 4 == 3;
    }
}