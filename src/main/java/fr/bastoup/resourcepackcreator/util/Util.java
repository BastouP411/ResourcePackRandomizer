package fr.bastoup.resourcepackcreator.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Util {
    public static void replaceImage(File img, String search) {
        if(img.isDirectory() || !img.exists())
            return;

        try {
            BufferedImage image = ImageIO.read(img);
            int w = image.getWidth();
            int h = image.getHeight();

            if(img.delete()) {
                GoogleImageHTTP.dlRandomImage(search, w, h, img);
            }

        } catch (IOException e) {
            return;
        }

    }

    public static void browseAndReplace(File f) {
        if(f.isFile()) {
            String[] s = f.getName().split("\\.");
            if(s[s.length - 1].equalsIgnoreCase("png")) {
                String search = String.join( " ", s[0].split("_"));
                System.out.println("Treating \"" + f.getPath() + "\" with search \"" + search + "\"");
                replaceImage(f, search);
            }
        } else if(f.isDirectory()) {
            for (File sub: f.listFiles()) {
                browseAndReplace(sub);
            }
        }
    }
}
