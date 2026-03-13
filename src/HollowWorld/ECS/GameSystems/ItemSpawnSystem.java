package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import HollowWorld.ECS.Components.Terraria.ItemType;
import HollowWorld.ECS.Events.DropItemBlock;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.ECS.Components.Player.PlayerInput;
import HollowWorld.GameCode.EventManager;
import HollowWorld.GameCode.GameData;
import HollowWorld.GameCode.WorldGeneration.WorldMap;

import java.util.List;

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

        //System.out.println(EventManager.getEvents(DropItemBlock.class).isEmpty());
        if (!EventManager.getEvents(DropItemBlock.class).isEmpty()){
            for(int i = 0; i < EventManager.getEvents(DropItemBlock.class).size(); i++){
                DropItemBlock itemDrop = EventManager.getEvents(DropItemBlock.class).remove(i); // Event aus Liste entfernen und speichern

                for(int j = 0; j < itemDrop.getCount(); j++){ // für jeden count ein item spawnen

                    int xItem = GameData.screenXYtoWorldXY(itemDrop.getX(), itemDrop.getY()).x;
                    int yItem = GameData.screenXYtoWorldXY(itemDrop.getX(), itemDrop.getY()).y;

                    gameObjects.add(makeItem(itemDrop.getItem(),xItem, yItem)); // item spawnen
                    System.out.println("Item erzeugt");
                }



            }
        }
    }
}

