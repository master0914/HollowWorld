package Engine;

public abstract class GameEntity {
    protected int offX, offY;

    public GameEntity(int x, int y){
        this.offX = x;
        this.offY = y;
    }

    public abstract void render(GameContainer gc, Renderer r);
    public abstract void update(GameContainer gc, float dt);

    public void setCoordinates(int x, int y){
        this.offX = x;
        this.offY = y;
    }

    public int getX(){
        return offX;
    }

    public int getY(){
        return offY;
    }
}
