package fr.bastoup.resourcepackcreator;

import fr.bastoup.resourcepackcreator.util.GoogleImageHTTP;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResourcePackCreator {

    public static void main(String[] args) {
        try {
            File dir = new File("/home/bpascal/Bureau/");
            GoogleImageHTTP.dlRandomImage("_Sulray_", dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
