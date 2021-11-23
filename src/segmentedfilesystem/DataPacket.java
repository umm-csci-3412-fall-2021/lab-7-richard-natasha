package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class DataPacket extends Packet{

    public byte[] packetNum;

    public DataPacket(DatagramPacket packet) {
        super(packet);
        int nameLength = packet.getLength();
        packetNum = Arrays.copyOfRange(packet.getData(), 2, 4);
        packetNumber = calculatePacketNumber();
        data = Arrays.copyOfRange(packet.getData(), 4, 4 + nameLength);
    }

    public byte[] getData() {
        return this.data;
    }

    public boolean isLastPacket() {
        return this.statusByte % 4 == 3;
    }

    public int calculatePacketNumber() {
        int part1 = this.getData()[2];
        int part2 = this.getData()[3];

        if(part1 < 0) {
            part1 += 256;
        }
        if(part2 < 0) {
            part2 += 256;
        }
        System.out.println(part1 + " " + part2);
        return 256 * part1 + part2;
    }
}