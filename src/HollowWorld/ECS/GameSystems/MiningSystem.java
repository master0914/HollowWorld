package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
//import HollowWorld.ECS.Events.MousePressedEvent;
import Engine.Math.IVector;
import HollowWorld.ECS.Components.Player.PlayerInput;
import HollowWorld.ECS.Components.Terraria.BlockType;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.EventManager;
import HollowWorld.GameCode.GameData;
import HollowWorld.GameCode.WorldGeneration.WorldMap;

import java.util.List;

import HollowWorld.ECS.Events.DropItemBlock;

import static HollowWorld.GameCode.EntityFactory.makeHit;


public class MiningSystem extends GameSystem {

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


            IVector blockpos = GameData.screenXYtoBlockXY(input.getMouseX(), input.getMouseY());
            System.out.println("Servus test");
            //worldMap.getBlock(blockpos.x, blockpos.y);
            BlockType p =  worldMap.getBlock(blockpos.x, blockpos.y);
            EventManager.addEvent(new DropItemBlock(p.dropItem, 1,input.getMouseX(), input.getMouseY()));
            worldMap.removeBlock(blockpos.x, blockpos.y);

        }


    }
}

