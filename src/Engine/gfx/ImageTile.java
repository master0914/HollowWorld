package Engine.gfx;

public class ImageTile extends Image{
    private int tileW,tileH;


    public ImageTile(String path, int tileW, int tileH){
        super(path);
        this.tileH = tileH;
        this.tileW = tileW;
    }

    public Image getImageFromPosition(int tileX, int tileY){
        int[] tilePixels = new int[tileW * tileH];

        int startX = tileX * tileW;
        int startY = tileY * tileH;

        int[] sheetPixels = this.getP();
        for(int y = 0; y < tileH; y++){
            for(int x = 0; x < tileW; x++){
                int sheetIndex = (startX + x) + ((startY + y) * getW());
                int tileIndex = x + (y * tileW);

                tilePixels[tileIndex] = sheetPixels[sheetIndex];
            }
        }
        Image tileImage = new Image("");
        tileImage.setW(tileW);
        tileImage.setH(tileH);
        tileImage.setP(tilePixels);

        return tileImage;
    }

    public Image[] getImagesFromRange(int startX, int endX, int y){
        int count = (endX - startX) + 1;
        Image[] images = new Image[count];
        for (int tX = 0; tX < count; tX++){
            images[tX] = getImageFromPosition(tX+startX,y);
        }
        return images;
    }

    public int getTileW() {
        return tileW;
    }

    public void setTileW(int tileW) {
        this.tileW = tileW;
    }

    public int getTileH() {
        return tileH;
    }

    public void setTileH(int tileH) {
        this.tileH = tileH;
    }
}
