package HollowWorld.GameCode;

import HollowWorld.ECS.Components.Core.Collider;
import HollowWorld.ECS.Components.Core.RigidBody;
import HollowWorld.ECS.Components.Core.SpriteComponent;
import HollowWorld.ECS.Components.Core.Transform;
import HollowWorld.ECS.Components.Player.CameraFollow;
import HollowWorld.ECS.Components.Player.PlatformerMovement;
import HollowWorld.ECS.Components.Player.PlayerInput;
import HollowWorld.ECS.GameObjects.GameObject;

public class EntityFactory {
    public static GameObject makePlayer(){
        GameObject player = new GameObject("Player");
        player.setTag("Player");
        // core
        player.addComponent(new Transform(100,100));
        player.addComponent(new SpriteComponent("/Test/TestSmiley.png"));
        // physics
        player.addComponent(new RigidBody());
        player.addComponent(new Collider(32,32));
        // movement
        player.addComponent(new PlayerInput());
        player.addComponent(new PlatformerMovement());
        // camera
        player.addComponent(new CameraFollow());
        return player;
    }
}
