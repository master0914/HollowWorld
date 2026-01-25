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
                       WorldMap worldMap) {}
}

