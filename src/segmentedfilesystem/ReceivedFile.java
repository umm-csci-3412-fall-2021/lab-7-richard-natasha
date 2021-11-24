package segmentedfilesystem;

import java.util.SortedMap;
import java.util.TreeMap;

public class ReceivedFile{

    public SortedMap<Integer, Packet> files;
    public int handledID;
    private HeaderPacket headerPacket;
    public int packetsReceived;
    public int maxPackets;

    public ReceivedFile(){
        this.files = new TreeMap<Integer, Packet>();
        this.packetsReceived = 0;
    }

    public void addPacket(HeaderPacket headerPacket) {
        this.headerPacket = headerPacket;
    }

    public void addPacket(DataPacket dataPacket) {
        this.files.put(dataPacket.packetNumber, dataPacket);
    }

    public HeaderPacket getHeaderPacket() {
        return this.headerPacket;
    }

    public boolean allPacketsReceived(){
        if(files.size() == 0) {
            return false;
        }
        return this.maxPackets == this.files.size();
    }
}

