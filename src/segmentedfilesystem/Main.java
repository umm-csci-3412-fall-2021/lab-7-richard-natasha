package segmentedfilesystem;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    
    // If there's one command line argument, it is assumed to
    // be the server. If there are two, the second is assumed
    // to be the port to use.
    public static void main(String[] args) {
        String server = "localhost";
        // CHANGE THIS DEFAULT PORT TO THE PORT NUMBER PROVIDED
        // BY THE INSTRUCTOR.
        int port = 6014;
        
        if (args.length >= 1) {
            server = args[0];
        }
        if (args.length >= 2) {
            port = Integer.parseInt(args[1]);
        }

        FileRetriever fileRetriever = new FileRetriever(server, port);
        fileRetriever.downloadFiles();
        try {
            fileRetriever.writeToFiles();
        } catch(FileNotFoundException e) {
            System.out.println("The file you're trying to write to doesn't exist");
        } catch(IOException e) {
            System.out.println("There was an issue with reading/writing");
        }
    }

}
