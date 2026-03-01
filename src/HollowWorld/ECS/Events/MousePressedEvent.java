package HollowWorld.ECS.Events;

public class MousePressedEvent implements Event {//wird benutzt, um auf den Mausklick zu reagieren

    private final int x;
    private final int y;
    private final int button;

    public MousePressedEvent(int x, int y, int button) {
        this.x = x;
        this.y = y;
        this.button = button;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getButton() { return button; }

    //Abbau des Blockes an der angeklickten Position
    /*public void Abbau(int x, int y){
        int p = 0;
        int o = 0;
        renderMap(x,y);     //soll Methode renderMap() aus RenderSystem.java aufgerufen werden
        removeBlock(int x,int y;;);     //soll Methode removeBlock() aus WorldMap.java aufgerufen werden mit den von renderMap() berechneten Koordinaten
    }*/
}

