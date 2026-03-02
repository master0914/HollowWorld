package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import Engine.Math.IVector;
import HollowWorld.ECS.Components.Player.PlayerInput;
import HollowWorld.ECS.Components.Terraria.BlockType;
import HollowWorld.ECS.Components.Terraria.ItemType;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.EventManager;
import HollowWorld.GameCode.GameData;
import HollowWorld.GameCode.WorldGeneration.WorldMap;


import Engine.Core.GameContainer;
import HollowWorld.ECS.Events.MousePressedEvent;
import HollowWorld.ECS.Components.Player.PlayerInput;
import HollowWorld.ECS.Components.Terraria.BlockType;
import HollowWorld.ECS.Components.Terraria.ItemType;
import HollowWorld.ECS.Events.DropItemBlock;
import HollowWorld.ECS.Events.MousePressedEvent;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.EventManager;
import HollowWorld.ECS.GameSystems.RenderSystem;
import HollowWorld.GameCode.WorldGeneration.WorldMap;

import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;


import java.util.List;

import HollowWorld.ECS.Events.DropItemBlock;



public class MiningSystem extends GameSystem {


    @Override
    public void initialize() {

    }

    @Override
    public void cleanup() {}



    @Override
    public void update(GameContainer gc, float dt,
                       List<GameObject> gameObjects,
                       WorldMap worldMap) {

        GameObject player = findPlayer(gameObjects);
        PlayerInput input = player.getComponent(PlayerInput.class);



        // MousePressedEvent auswerten
        if(input.isMouseLeftJustPressed()) {

            IVector blockpos = GameData.screenXYtoBlockXY(input.getMouseX(), input.getMouseY());
            BlockType p =  worldMap.getBlock(blockpos.x, blockpos.y);

            if(p != BlockType.AIR) {
                EventManager.addEvent(new DropItemBlock(p.dropItem, 1, input.getMouseX(), input.getMouseY()));
                worldMap.removeBlock(blockpos.x, blockpos.y);
            }
        }


    }
}

