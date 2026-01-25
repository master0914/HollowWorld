package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import HollowWorld.ECS.Components.Terraria.BlockType;
import HollowWorld.ECS.Components.Terraria.ItemType;
//import HollowWorld.ECS.Events.MousePressedEvent;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.ECS.Components.Player.PlayerInput;
import HollowWorld.GameCode.EventManager;
import HollowWorld.GameCode.WorldGeneration.WorldMap;

import java.util.List;


import static HollowWorld.GameCode.EntityFactory.makeHit;
import static HollowWorld.GameCode.EntityFactory.makeItem;

public class ItemSpawnSystem extends GameSystem {


    @Override
    public void initialize() {}


    @Override
    public void update(GameContainer gc, float dt,
                       List<GameObject> gameObjects,
                       WorldMap worldMap) {


        GameObject player = findPlayer(gameObjects);
        PlayerInput input = player.getComponent(PlayerInput.class);


        // MousePressedEvent auswerten
        if (input.isMouseLeftJustPressed()) {

            gameObjects.add(makeItem(ItemType.PLANKS, player.getTransform().x, 100));


        }


    }
}

