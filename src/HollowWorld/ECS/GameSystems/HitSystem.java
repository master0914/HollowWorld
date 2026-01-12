package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
//import HollowWorld.ECS.Events.MousePressedEvent;
import HollowWorld.ECS.Components.Player.PlayerInput;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.EventManager;
import HollowWorld.GameCode.WorldGeneration.WorldMap;

import java.util.List;

import static HollowWorld.GameCode.EntityFactory.makeHit;


public class HitSystem extends GameSystem {

    private float hitLifetime = 0f;

    @Override
    public void initialize() {}

    @Override
    public void cleanup() {}

    @Override
    public void update(GameContainer gc, float dt,
                       List<GameObject> gameObjects,
                       WorldMap worldMap) {

        GameObject player = findPlayer(gameObjects);
        PlayerInput input = player.getComponent(PlayerInput.class);

        GameObject hit = null;
        if(hasObjectsByName("Hit",gameObjects)) {
            hit = getObjectsByName("Hit", gameObjects);
        }

        // MousePressedEvent auswerten
        if(input.isMouseLeftJustPressed()) {
            // player kann nicht mehr null sein da sonst das player.getComponent vorher einen fehler werden würde
            if (hit == null) {
                hit = makeHit();
                gameObjects.add(hit);
                hitLifetime = 0.3f;//steht für die Dauer der Existenz der hitrange bei einem Schlag (Mausklick) kann bei bedarf verändert werden
            }else if(hitLifetime <= 0){
                // das heißt man kann nur angreifen wenn der vorherige schon vorbei ist
                hit.setActive(true);
                hitLifetime = 0.3f;
            }
        }

        //  Hit folgt dem Spieler
        if (hit != null) {
            hit.getTransform().x = player.getTransform().x-16;
            hit.getTransform().y = player.getTransform().y-16;

            hitLifetime -= dt;
            if (hitLifetime <= 0) {
                hit.setActive(false);
            }
        }
    }
}

