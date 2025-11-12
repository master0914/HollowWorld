package Engine.Interfaces;


import Engine.Core.GameContainer;
import Engine.Core.Renderer;

public abstract class AbstractGame {
    //Wird als Vater für alle Spiele benutzt
    // update() und render() werden jeden frame aufgerufen

    public abstract void update(GameContainer gc, float dt);
    public abstract void render(GameContainer gc, Renderer r);
    public abstract void init();
}
