package segmentedfilesystem;

import java.util.SortedMap;
import java.util.TreeMap;
import java.nio.ByteBuffer;

public class ReceivedFile{

    public static SortedMap<Integer, Packet> files;
    public static int packetsReceived;
    public ReceivedFile(){
        files = new TreeMap<Integer, Packet>();
        packetsReceived = 0;
    }

    public static boolean allPacketsReceived(){
        return packetsReceived == ByteBuffer.wrap(FileRetriever.lastPackNum).getInt();
    }
}