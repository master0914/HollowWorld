package HollowWorld.ECS.Components;

import Engine.Core.GameContainer;
import Engine.Core.Renderer;
import HollowWorld.ECS.GameObjects.GameObject;

public abstract class Component {
    protected GameObject gameObject;

    public void setGameObject(GameObject gameObject){
        this.gameObject = gameObject;
    }

    public GameObject getGameObject(){
        return gameObject;
    }

    // Muss immer z.B. beim hinzufügen eines Components aufgerufen werden
    public void start() {}
    // werden jeden frame aufgerufen
    public void update(GameContainer gc, float deltaTime){}
    public void render(Renderer renderer){}
    // Muss wenn das Obj nicht mehr benutzt wird aufgerufen werden
    public void cleanup(){}
}
