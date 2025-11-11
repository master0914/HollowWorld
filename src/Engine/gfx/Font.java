package Engine.gfx;



public class Font {
    // Font structure:
    public static final Font STANDARD = new Font("/Fonts/StandardFont.png");

    private final float BACKGROUND = 0xffff00ff;//pink
    private final float CHARSTART = 0xff0000ff; //blue
    private final float CHAREND = 0xffffff00 ;  //yellow


    private Image fontImage;
    private int[] offsets;
    private int[] widths;

    public Font(String path){
        fontImage = new Image(path);

        offsets = new int[59];
        widths = new int[59];
        int unicode = 0;

        for(int i = 0; i < fontImage.getW(); i++){
            if(fontImage.getP()[i] == CHARSTART){
                offsets[unicode] = i;
            }

            if(fontImage.getP()[i] == CHAREND){
                widths[unicode] = i - offsets[unicode];
                unicode++;
            }
        }
    }

    public static int getTextWidth(Font font, String text){
        text = text.toUpperCase();
        int width = 0;
        for(int i = 0; i<text.length();i++) {

            int unicode = text.codePointAt(i) - 32;  //shift unicode to our Fontcode
            width += font.getWidths()[unicode];
        }
        return width;
    }


    public Image getFontImage() {
        return fontImage;
    }

    public void setFontImage(Image fontImage) {
        this.fontImage = fontImage;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public void setOffsets(int[] offsets) {
        this.offsets = offsets;
    }

    public int[] getWidths() {
        return widths;
    }

    public void setWidths(int[] widths) {
        this.widths = widths;
    }
}
