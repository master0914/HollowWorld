package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import HollowWorld.ECS.Events.MousePressedEvent;
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

        GameObject player = gameObjects.stream()//durchsucht alle  gameObjects nach dem Spieler
                .filter(o -> "Player".equals(o.getName()))
                .findFirst()
                .orElse(null);

        GameObject hit = gameObjects.stream()//durchsucht alle  gameObjects nach der hitrangeh (hit)
                .filter(o -> "Hit".equals(o.getName()))
                .findFirst()
                .orElse(null);

        // MousePressedEvent auswerten
        for (MousePressedEvent e :
                EventManager.getEvents(MousePressedEvent.class)) {

            if (player != null && hit == null) {
                hit = makeHit();
                gameObjects.add(hit);
                hitLifetime = 0.3f;//steht für die Dauer der Existenz der hitrange bei einem Schlag (Mausklick) kann bei bedarf verändert werden
            }
        }

        //  Hit folgt dem Spieler
        if (player != null && hit != null) {
            hit.getTransform().x = player.getTransform().x-16;
            hit.getTransform().y = player.getTransform().y-16;

            hitLifetime -= dt;
            if (hitLifetime <= 0) {
                hit.setActive(false);
            }
        }
    }
}

