package Engine.Core;

import Engine.gfx.Font;
import Engine.gfx.Image;
import Engine.gfx.ImageTile;

import java.awt.image.DataBufferInt;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Renderer {
    private int pW, pH; // pixelWidth, pixelHeight
    private int[] p; //pixels Array



    private Font font = Font.STANDARD;

    public Renderer(GameContainer gc){
        pW = gc.getWidth();
        pH = gc.getHeight();
        p = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear(){
        for (int i = 0; i<p.length;i++){
            p[i] = 0;
        }
    }

    public void setPixel(int x, int y, int value){
        if((x < 0 || x >= pW || y < 0 || y >= pH)){ // 0xffff00ff ist hässlich also wird es unser Transparent
            return;
        }
        int alpha = (value >> 24) & 0xff;

        if((alpha == 0) || (value == 0xffff00ff)){
            return;
        }

        p[x + y * pW] = value;
    }

    public void drawBackground(int color){
        for(int x = 0; x<pW;x++){
            for(int y = 0; y<pH;y++){
                setPixel(x,y,color);
            }
        }
    }

    public void drawImage(Image image, int offX, int offY, boolean reversed){

        if(offX < -image.getW()) return;
        if(offY < -image.getH()) return;
        if(offX >= pW) return;
        if(offY >= pH) return;

        int newX = 0;
        int newY = 0;
        int newWidth = image.getW();
        int newHeight = image.getH();



        if(offX < 0){ newX -= offX; }
        if(offY < 0){ newY -= offY; }
        if(offX + newWidth > pW){ newWidth -= newWidth + offX - pW; }
        if(offY + newHeight > pH){ newHeight -= newHeight + offY - pH; }

        int imgW = image.getW();
        int[] pixels = image.getP();

        for(int y = newY; y < newHeight;y++){
            for(int x = newX; x < newWidth;x++){
                int srcX = reversed ? (imgW - 1 - x) : x;

                int col = pixels[srcX + y * imgW];

                setPixel(x + offX, y + offY, col);
            }
        }
    }

    public void drawImage(Image image, int offX, int offY, boolean reversed, float scale) {
        if (scale <= 0) return;

        int scaledWidth = (int)(image.getW() * scale);
        int scaledHeight = (int)(image.getH() * scale);

        if (offX < -scaledWidth) return;
        if (offY < -scaledHeight) return;
        if (offX >= pW) return;
        if (offY >= pH) return;

        int startX = 0;
        int startY = 0;
        int renderWidth = scaledWidth;
        int renderHeight = scaledHeight;

        if (offX < 0) {
            startX = -offX;
            renderWidth = scaledWidth + offX;
        }
        if (offY < 0) {
            startY = -offY;
            renderHeight = scaledHeight + offY;
        }
        if (offX + renderWidth > pW) {
            renderWidth = pW - offX;
        }
        if (offY + renderHeight > pH) {
            renderHeight = pH - offY;
        }

        if (renderWidth <= 0 || renderHeight <= 0) return;

        int imgW = image.getW();
        int imgH = image.getH();
        int[] pixels = image.getP();

        for (int y = startY; y < renderHeight; y++) {
            for (int x = startX; x < renderWidth; x++) {
                // Quelle-Pixel im Originalbild berechnen
                int srcX = (int)(x / scale);
                int srcY = (int)(y / scale);

                // Sicherstellen, dass wir innerhalb des Originalbildes bleiben
                if (srcX >= 0 && srcX < imgW && srcY >= 0 && srcY < imgH) {
                    // Wenn reversed, X-Koordinate spiegeln
                    int finalSrcX = reversed ? (imgW - 1 - srcX) : srcX;

                    int col = pixels[finalSrcX + srcY * imgW];
                    setPixel(x + offX, y + offY, col);
                }
            }
        }
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY){

        // Nicht Render bedingungen
        if(offX < -image.getTileW()) return;
        if(offY < -image.getTileH()) return;
        if(offX >= pW) return;
        if(offY >= pH) return;
        //----------------------------------
        int newX = 0;
        int newY = 0;
        int newWidth = image.getTileW();
        int newHeight = image.getTileH();



        if(offX < 0){ newX -= offX; }
        if(offY < 0){ newY -= offY; }
        if(offX + newWidth > pW){ newWidth -= newWidth + offX - pW; }
        if(offY + newHeight > pH){ newHeight -= newHeight + offY - pH; }

        for(int y = newY; y < newHeight;y++){
            for(int x = newX; x < newWidth;x++){
                setPixel(x + offX, y +offY, image.getP()[(x + tileX * image.getTileW())  +  (y + tileY * image.getTileH())  *  image.getW()]);
            }
        }
    }

    public void drawText(String text, int offX, int offY, int color){
        text = text.toUpperCase();
        int offset = 0;

        for(int i = 0; i<text.length();i++){
            int unicode = text.codePointAt(i) - 32;  //shift unicode to our Fontcode

            for(int y = 0; y < font.getFontImage().getH(); y++){
                for(int x = 0; x < font.getWidths()[unicode]; x++){
                    if(font.getFontImage().getP()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getW()] == 0xffffffff){
                        setPixel(x + offX + offset, y + offY, color);
                    }
                }
            }
            offset += font.getWidths()[unicode];
        }
    }

    public void drawRect(int offX, int offY, int width, int height, int color){
        for(int y = 0; y <= height; y++){
            setPixel(offX, y + offY, color);
            setPixel(offX + width, y + offY, color);
        }
        for(int x = 0; x <= width; x++){
            setPixel(x + offX, offY, color);
            setPixel(x+offX, offY + height, color);
        }
    }

    public void fillRect(int offX, int offY, int width, int height, int color){
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                setPixel(offX + x, y + offY, color);
                setPixel(x + offX, y + offY, color);
            }
        }
    }

    public void drawLine(int x0, int y0, int x1, int y1, int color) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;  // Schrittweite in x
        int sy = y0 < y1 ? 1 : -1;  // Schrittweite in y
        int err = dx - dy;          // Fehlerterm

        while (true) {
            // Setze aktuellen Punkt
            setPixel(x0, y0, color);

            // Wenn Endpunkt erreicht, abbrechen
            if (x0 == x1 && y0 == y1) {
                break;
            }

            // Entscheidung, ob wir in x- oder y-Richtung weitergehen
            int e2 = err * 2;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    public void drawCircle(int cenX, int cenY, int rad, int color){

        double i, angle, x1, y1;

        for(i = 0; i < 360; i += 0.1)
        {
            angle = i;
            x1 = rad * cos(angle * Math.PI / 180);
            y1 = rad * sin(angle * Math.PI / 180);
            setPixel((int)(cenX + x1), (int)(cenY + y1), color);
        }
    }

    public Font getFont() {
        return font;
    }
}
