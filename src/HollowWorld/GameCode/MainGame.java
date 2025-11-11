package HollowWorld.GameCode;

import HollowWorld.ECS.AbstractBoilerPlateGame;
import HollowWorld.ECS.Components.Core.SpriteRenderer;
import HollowWorld.ECS.Components.Core.Transform;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.ECS.Logger;

public class MainGame extends AbstractBoilerPlateGame {
    EntityFactory entityFactory = new EntityFactory();
    @Override
    public void init() {
        GameObject player = entityFactory.makePlayer();
        addGameObject(player);


        System.out.println(getGameObjects().get(0).getName());


    }
}
