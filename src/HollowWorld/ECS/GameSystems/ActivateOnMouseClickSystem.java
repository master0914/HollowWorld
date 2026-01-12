package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
//import HollowWorld.ECS.Events.MousePressedEvent;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.EventManager;
import HollowWorld.GameCode.WorldGeneration.WorldMap;

import java.util.List;

public class ActivateOnMouseClickSystem extends GameSystem {

    @Override
    public void initialize() {
    }

    @Override
    public void update(GameContainer gc, float dt,
                       List<GameObject> gameObjects,
                       WorldMap worldMap) {
        // setzt die hitrange des Spielers beim drücken der linken Maustaste auf aktiv

        // wofür ist das hier genau?
        // weil damit würde ja einfach das erste nicht aktive objekt in der liste aktiv gesetzt werden
        // verstehe nich genau den zweck?
        // gerne wieder einfügen falls es wichtig ist habe es für jetzt mal auskommentiert
        // um schnell sachen aus und einzukommentieren kannst du:
        // CRTL + / drücken. (das geht nur mit dem / auf dem numpad)

//        for (MousePressedEvent event :
//                EventManager.getEvents(MousePressedEvent.class)) {
//
//            for (GameObject obj : gameObjects) {
//
//                if (!obj.isActive()) {
//                    obj.setActive(true);
//                    return;
//                }
//            }
//        }
    }
}

