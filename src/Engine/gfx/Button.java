package Engine.gfx;

import Engine.Core.GameContainer;
import Engine.Interfaces.MenuEntity;
import Engine.Core.Renderer;

import java.awt.event.MouseEvent;

public abstract class Button extends MenuEntity {
    protected String text;
    protected int color;


    public Button(int offX, int offY, int width, int height, String text, int color) {
        super(offX, offY, width, height);
        this.text = text;
        this.color = color;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRect(offX,offY,width,height,color);
        int TextWidth = Font.getTextWidth(r.getFont(),text);
        int TextHeight = r.getFont().getFontImage().getH();
        r.drawText(text,offX + width/2-TextWidth/2,offY + height/2-TextHeight/2,0xffffffff);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if(hoversOver(gc.getInput())){
            if(gc.getInput().isButtonDown(MouseEvent.BUTTON1)){
                buttonMethode();
            }
        }
    }

    public abstract void buttonMethode();
}
