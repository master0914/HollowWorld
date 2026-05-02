package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import Engine.Math.IVector;
import HollowWorld.ECS.Components.Player.PlayerInput;
import HollowWorld.ECS.Components.Terraria.BlockType;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.EventManager;
import HollowWorld.GameCode.GameData;
import HollowWorld.GameCode.WorldGeneration.WorldMap;



import HollowWorld.ECS.Events.DropItemBlock;

import java.util.List;
import java.util.concurrent.*;


public class MiningSystem extends GameSystem {

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    boolean mining = false;
    float current_block_hp = -1;
    IVector miningBlockpos;

    @Override
    public void initialize() {


    }

    @Override
    public void cleanup() {
    }


    @Override
    public void update(GameContainer gc, float dt,
                       List<GameObject> gameObjects,
                       WorldMap worldMap) {

        GameObject player = findPlayer(gameObjects);
        PlayerInput input = player.getComponent(PlayerInput.class);



        if (input.isMouseLeft()){
            IVector blockpos = GameData.screenXYtoBlockXY(input.getMouseX(), input.getMouseY());

            if(blockpos.x < worldMap.getWidth() && blockpos.x >= 0.0 && blockpos.y >= 0.0 && blockpos.y < worldMap.getHeight()){
                BlockType p = worldMap.getBlock(blockpos.x, blockpos.y);

                if(p != BlockType.AIR){

                    if(current_block_hp < 0){
                        current_block_hp = p.hardness;

                    }

                    Runnable scheduleMineBlock = () -> mineBlock();

                    if(!mining){
                        if(current_block_hp <= 0){
                            current_block_hp = -1;
                            EventManager.addEvent(new DropItemBlock(p.dropItem, 1, input.getMouseX(), input.getMouseY()));
                            worldMap.removeBlock(blockpos.x, blockpos.y);

                        }else{
                            mining = true;
                            ScheduledFuture<?> schedFuture = scheduler.schedule(scheduleMineBlock,1,TimeUnit.SECONDS);
                        }
                    }
                }
            }
        }
    }

    public void mineBlock(){
        current_block_hp -= 0.5f;
        mining = false;
        System.out.println(current_block_hp);
    }
}

