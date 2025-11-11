package HollowWorld.GameCode;

import HollowWorld.ECS.Components.Core.SpriteRenderer;
import HollowWorld.ECS.Components.Core.Transform;
import HollowWorld.ECS.GameObjects.GameObject;

public class EntityFactory {
    public GameObject makePlayer(){
        GameObject player = new GameObject("Player");
        player.addComponent(new Transform(100,100));
        player.addComponent(new SpriteRenderer("/Test/TestSmily.png"));
        return player;
    }
}
