package segmentedfilesystem;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.nio.ByteBuffer;

public class ReceivedFile{

    public SortedMap<Integer, Packet> files;
    private HeaderPacket headerPacket;
    public int packetsReceived;

    public ReceivedFile(){
        files = new TreeMap<Integer, Packet>();
        packetsReceived = 0;
    }

    public void addPacket(HeaderPacket headerPacket) {
        this.headerPacket = headerPacket;
    }

    public void addPacket(DataPacket dataPacket) {
        files.put(dataPacket.packetNumber, dataPacket);
    }

    public boolean allPacketsReceived(){
        return this.packetsReceived == ByteBuffer.wrap(FileRetriever.lastPackNum).getInt();
    }
}

