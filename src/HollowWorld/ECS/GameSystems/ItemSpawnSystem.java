package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import HollowWorld.ECS.Components.Terraria.ItemType;
import HollowWorld.ECS.Events.MousePressedEvent;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.EventManager;
import HollowWorld.GameCode.WorldGeneration.WorldMap;

import java.util.List;


import static HollowWorld.GameCode.EntityFactory.makeItem;

public class ItemSpawnSystem extends GameSystem {


    @Override
    public void initialize() {}


    @Override
    public void update(GameContainer gc, float dt,
                       List<GameObject> gameObjects,
                       WorldMap worldMap) {


        // MousePressedEvent auswerten
        for (MousePressedEvent e :
                EventManager.getEvents(MousePressedEvent.class)) {


                gameObjects.add(makeItem(ItemType.DIRT,100,100));

            }
        }
}

