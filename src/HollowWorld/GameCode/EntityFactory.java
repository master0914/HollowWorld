package HollowWorld.GameCode;

import Engine.gfx.ImageTile;
import HollowWorld.ECS.Components.Core.*;
import HollowWorld.ECS.Components.Player.CameraFollow;
import HollowWorld.ECS.Components.Player.PlatformerMovement;
import HollowWorld.ECS.Components.Player.PlayerInput;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.ECS.Components.Terraria.ItemComponent;
import HollowWorld.ECS.Components.Terraria.ItemType;

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

    public static GameObject makeItem(ItemType nItem, int x, int y){
        GameObject item = new GameObject(nItem.name());
        item.addComponent(new Transform(x, y));
        item.addComponent(new ItemComponent(nItem));
        item.addComponent(new SpriteComponent(nItem.texturePath));

        item.addComponent(new RigidBody());
        item.addComponent(new Collider(24, 24));
        Collider collider = item.getComponent(Collider.class);
        collider.setSolid(false);
        return item;

    }

    public static GameObject makeHit(){//hit soll sich auf den Bereich beziehen, auf welchem der Spieler mit einer Wafe schlägt und somit den dem Gegner Schaden hinzufügen kann
        GameObject hit = new GameObject("Hit");
        hit.addComponent(new Transform (84, 145));
        hit.addComponent(new SpriteComponent("/hitrangePlayer.png"));//vorrübergehende hitrange für den Spieler(zur Visualizierung) außerdem sollte Z. 63 auscodiert werden, da die hitrange nur zum Testen der Fuktionen sichtbar sein sollte.
        hit.addComponent(new Collider(64,80));//vorrübergehend, Größe muss noch auf den Spieler angepasst werden
        return hit;
    }


}
