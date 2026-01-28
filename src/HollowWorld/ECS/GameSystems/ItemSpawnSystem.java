package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import HollowWorld.ECS.Components.Terraria.ItemType;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.ECS.Components.Player.PlayerInput;
import HollowWorld.GameCode.WorldGeneration.WorldMap;

import java.util.List;
import java.util.Random;


import static HollowWorld.GameCode.EntityFactory.makeHit;
import static HollowWorld.GameCode.EntityFactory.makeItem;

public class ItemSpawnSystem extends GameSystem {


    @Override
    public void initialize() {

    }


    @Override
    public void update(GameContainer gc, float dt,
                       List<GameObject> gameObjects,
                       WorldMap worldMap) {


        GameObject player = findPlayer(gameObjects);
        PlayerInput input = player.getComponent(PlayerInput.class);


        // MousePressedEvent auswerten
        if (input.isMouseLeftJustPressed()) {
            Random rand = new Random();
            int randomNumber = rand.nextInt(4);
            if(randomNumber == 0){
                gameObjects.add(makeItem(ItemType.PLANKS, player.getTransform().x, player.getTransform().y - 200));
            }
            else if (randomNumber == 1) {
                gameObjects.add(makeItem(ItemType.STONE, player.getTransform().x, player.getTransform().y - 200));
            }
            else if(randomNumber == 2){
                gameObjects.add(makeItem(ItemType.DIRT, player.getTransform().x, player.getTransform().y - 200));
            }
            else{
                gameObjects.add(makeItem(ItemType.COAL, player.getTransform().x, player.getTransform().y - 200));
            }


        }


    }
}

