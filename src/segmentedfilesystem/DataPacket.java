package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class DataPacket extends Packet{

    public byte[] packetNum;
    public int packetNumber;

    public DataPacket(DatagramPacket packet) {
        super(packet);
        int nameLength = packet.getLength();
        packetNumber = getPacketNumber();
        data = Arrays.copyOfRange(packet.getData(), 4, nameLength);
    }

    public byte[] getData() {
        return this.data;
    }

    public boolean isLastPacket() {
        return this.statusByte % 4 == 3;
    }

    public int getPacketNumber() {
        int part1 = Byte.toUnsignedInt(data[2]);
        int part2 = Byte.toUnsignedInt(data[3]);
        return 256 * part1 + part2;
    }
}