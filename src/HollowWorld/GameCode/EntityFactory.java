package HollowWorld.GameCode;

import Engine.gfx.ImageTile;
import HollowWorld.ECS.Components.Core.*;
import HollowWorld.ECS.Components.Player.CameraFollow;
import HollowWorld.ECS.Components.Player.PlatformerMovement;
import HollowWorld.ECS.Components.Player.PlayerInput;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.ECS.Components.Terraria.ItemComponent;

public class EntityFactory {
    public static GameObject makePlayer() {
        GameObject player = new GameObject("Player");
        player.setTag("Player");
        // core
        player.addComponent(new Transform(100, 100));
        ImageTile spriteSheet = new ImageTile("/DarkSamurai (64x64).png", 64, 64);
        // animator
//        Animator animator = new Animator();
//
//        Animation idle = new Animation(0.2f,spriteSheet.getImagesFromRange(0,7,0));
//        animator.addAnimation(Animator.AnimationState.IDLE, idle);
//
//        Animation run = new Animation(0.2f,spriteSheet.getImagesFromRange(0,7,1));
//        animator.addAnimation(Animator.AnimationState.RUN, run);
//
//        player.addComponent(animator);

        player.addComponent(new SpriteComponent("/Test/TestSmiley.png"));
        // physics
        player.addComponent(new RigidBody());
        player.addComponent(new Collider(32, 32));
        // movement
        player.addComponent(new PlayerInput());
        player.addComponent(new PlatformerMovement());
        // camera
        player.addComponent(new CameraFollow());
        return player;
    }

    public static GameObject makeChest() {
        GameObject chest = new GameObject("Chest");
        chest.addComponent(new Transform(190, 100));
        chest.addComponent(new SpriteComponent("/Chest.png"));
        // physics
        chest.addComponent(new RigidBody());
        chest.addComponent(new Collider(32, 32));
        return chest;
    }

    public static GameObject makeItem(){
        GameObject item = new GameObject("Item");
        item.addComponent(new Transform(190, 100));
        item.addComponent(new ItemComponent(001));

        return item;

    }
}
