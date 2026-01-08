package HollowWorld.GameCode;

import HollowWorld.ECS.AbstractBoilerPlateGame;
import HollowWorld.ECS.Components.Terraria.ItemType;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.ECS.GameSystems.*;
import Engine.Core.GameContainer;
import HollowWorld.ECS.Events.MousePressedEvent;
import HollowWorld.GameCode.WorldGeneration.WorldMap;


import java.awt.event.MouseEvent;

import static HollowWorld.GameCode.EntityFactory.*;
import static HollowWorld.Main.Main.SCREENHEIGHT;
import static HollowWorld.Main.Main.SCREENWIDTH;

public class MainGame extends AbstractBoilerPlateGame {
    private GameObject hit;  // Das Hit-Objekt
    private float hitLifetime = 0f; // Timer für Hit


    @Override
    public void init() {
        // Systeme Registrieren
        addSystem(new PhysicsSystem());
        addSystem(new MovementSystem());
        addSystem(new AnimationSystem());
        addSystem(new ActivateOnMouseClickSystem());
        addSystem(new HitSystem());



        setWorldMap(new WorldMap(SCREENWIDTH / 32, SCREENHEIGHT / 32));

        GameObject player = makePlayer();
        addGameObject(player);
        addGameObject(makeChest());



        // Hit erst leer, wird beim Klicken erstellt
        hit = null;
    }


    @Override
    public void update(GameContainer gc, float dt) {//überprüft, ob die linke Maustaste gedrückt wurde und löst MousePressedEvent aus
        if (gc.getInput().isButtonDown(MouseEvent.BUTTON1)) {
            EventManager.addEvent(
                    new MousePressedEvent(
                            gc.getInput().getMouseX(),
                            gc.getInput().getMouseY(),
                            MouseEvent.BUTTON1
                    )
            );
        }
        super.update(gc, dt);
    }

}
