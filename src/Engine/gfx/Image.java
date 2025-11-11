package Engine.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image {

//    Der path muss auf eine Resource Package Datei zugreifen
//    Resource Package erstellen:
//          neue directory in Projektpfad erstellen
//          unterste option wählen (Mark directory as) -> Resources Root
//    dann Dateipfad als zB  "/Sprite/TestKiwi.png"
//    Datei muss als .png Vorliegen für gutes Funktionieren

    private int w,h;
    private int[] p;

    public Image(String path){
        if(path.isEmpty()){
            //System.out.println("ENGINE: IMAGE: NO PATH, STANDART 0xffff00ff transparent");
            w = 1;
            h = 1;
            p = new int[1];
            p[0] = 0xffff00ff;
        }else {
            BufferedImage image = null;

            try {
                image = ImageIO.read(Image.class.getResourceAsStream(path));
            } catch (IOException e) {
                e.printStackTrace();
            }

            w = image.getWidth();
            h = image.getHeight();
            p = image.getRGB(0, 0, w, h, null, 0, w);

            image.flush();
        }
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int[] getP() {
        return p;
    }

    public void setP(int[] p) {
        this.p = p;
    }
}
