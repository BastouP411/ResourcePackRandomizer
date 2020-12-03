package fr.bastoup.resourcepackcreator;

import fr.bastoup.resourcepackcreator.util.GoogleImageHTTP;
import fr.bastoup.resourcepackcreator.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResourcePackCreator {

    public static void main(String[] args) {
        File dir = new File("C:/Users/basti/Desktop/resourcePack");
        Util.browseAndReplace(dir);
    }

}
