package Engine;

public abstract class MenuEntity extends GameEntity{
    protected int width;
    protected int height;
    public MenuEntity(int x, int y, int w, int h) {
        super(x, y);
        width = w;
        height = h;
    }

    public boolean hoversOver(Input input){
        int x = input.getMouseX();int y = input.getMouseY();
        return !(x < offX || x > offX + width || y < offY || y > offY + height);
    }
}
