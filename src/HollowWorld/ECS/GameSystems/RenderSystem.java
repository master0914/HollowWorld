package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import Engine.Core.Renderer;
import Engine.Logger;
import Engine.Math.Vector;
import HollowWorld.ECS.Components.Core.*;
import HollowWorld.ECS.Components.Player.CameraFollow;
import HollowWorld.ECS.Components.Terraria.BlockType;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.WorldMap;
import HollowWorld.GameCode.GameData.*;

import java.util.List;

import static HollowWorld.GameCode.GameData.cameraPosition;

public class RenderSystem extends GameSystem{
//    private Vector cameraPosition = new Vector(0,0);

    @Override
    public void initialize() {

    }

    @Override
    public void render(GameContainer gc, Renderer renderer, List<GameObject> gameObjects, WorldMap map) {
        //Logger.info(cameraPosition.toString());
        updateCamera(gameObjects);
        renderObjects(renderer,gameObjects);
        renderMap(gc,renderer,map);
        renderCollider(renderer,gameObjects);
    }

    private void renderCollider(Renderer renderer, List<GameObject> gameObjects) {
        for(GameObject obj: getObjectsWithComponent(Collider.class,gameObjects)){
            Transform tf = obj.getTransform();
            Collider col = obj.getComponent(Collider.class);
            renderer.drawRect((int)(tf.x - cameraPosition.x), (int)(tf.y - cameraPosition.y), col.width,col.height,0xffff0000);
        }
    }

    private void renderObjects(Renderer renderer, List<GameObject> gameObjects){
        for (GameObject obj: gameObjects){
            if(obj.hasComponent(Animator.class)){
                renderObjectWithAnimator(renderer, obj);
            }
            else if(obj.hasComponent(SpriteComponent.class)){
                renderObjectWithSprite(renderer,obj);
            }
        }
    }


    private void renderObjectWithAnimator(Renderer renderer, GameObject obj){

        Animator animator = obj.getComponent(Animator.class);
        Transform transform = obj.getTransform();
        SpriteComponent sprite = obj.getComponent(SpriteComponent.class);
        if (transform == null) {
            Logger.warn("Object: " + obj.getName() + "cannot be Rendered due to missing Transform");
            return;
        }
        if (animator != null) {
            Animation currentAnim = animator.animations.get(animator.currentState);
            if (currentAnim == null) {
                Logger.warn("current Animation for Object: " + obj.getName() + " not existing");
                return;
            }
            renderer.drawImage(currentAnim.getCurrentFrame(), (int) (transform.x - cameraPosition.x), (int) (transform.y - cameraPosition.y), animator.flipX);

        } else if (sprite != null) {
            // fallback falls animation nicht existiert
            Logger.info("used FallbackSprite for Object: " + obj.getName());
            renderer.drawImage(sprite.image, (int) (transform.x - cameraPosition.x), (int) (transform.y - cameraPosition.y), false);
        }
    }
    private void renderObjectWithSprite(Renderer renderer, GameObject obj){
            SpriteComponent spriteComponent = obj.getComponent(SpriteComponent.class);
            Transform transform = obj.getTransform();
            if(transform == null){
                Logger.warn("Object: " + obj.getName() + "cannot be Rendered due to missing Transform");
                return;
            }
            renderer.drawImage(spriteComponent.image,(int)(transform.x - cameraPosition.x), (int)(transform.y - cameraPosition.y), false);

    }

    private void renderMap(GameContainer gc, Renderer renderer, WorldMap map){
        int w = map.getWidth();
        int h = map.getHeight();
        BlockType[][] blocks = map.getBlocks();
        for(int gridX=0; gridX < w;gridX++){
            for (int gridY=0; gridY < h;gridY++){
                // hier fehlt natürlich noch kamera folgen etc.
                BlockType block = blocks[gridX][gridY];
                if(block == BlockType.AIR){continue;}
                if(block.sprite == null){Logger.error("Tried to render Block: " + block.name() + ". Blocktype has no sprite!"); return;}
                renderer.drawImage(block.sprite, (int) (gridX * 32 - cameraPosition.x), (int) (gridY * 32 - cameraPosition.y), false);
                renderer.drawRect((int) (gridX * 32 - cameraPosition.x), (int) (gridY *32 - cameraPosition.y), 32,32,0xff111111);
            }
        }
    }

    private void updateCamera(List<GameObject> gameObjects){
        for(GameObject obj: getObjectsWithComponent(CameraFollow.class,gameObjects)){
            CameraFollow cameraFollow = obj.getComponent(CameraFollow.class);
            if(cameraFollow != null) {
                Transform targetTransform = cameraFollow.getGameObject().getTransform();
                if(targetTransform != null) {
                    float targetX = targetTransform.x + cameraFollow.offset.x;
                    float targetY = targetTransform.y + cameraFollow.offset.y;

                    cameraPosition.x = lerp(cameraPosition.x, targetX, cameraFollow.smoothSpeed);
                    cameraPosition.y = lerp(cameraPosition.y, targetY, cameraFollow.smoothSpeed);

                    // @TODO bounds an Map grenzen
                }
            }
        }

    }

    private float lerp(float a, float b, float t) {
        // Lineare Inperpolation
        // gibt einen wert zwischen a und b anhand von t zurück
        // bsp a = 5, b = 9, t = 0.5   ->  7 da 7 auf der hälft zw 5 und 9 liegt
        // wenn t = 0.25 -> 6; wenn t = 0.75 -> 8
        return a + (b - a) * t;
    }
}


//private void renderObjectsWithSprite(Renderer renderer, List<GameObject> gameObjects){
//    for(GameObject obj: getObjectsWithComponent(SpriteComponent.class, gameObjects)){
//        if(obj.hasComponent(Animator.class)){continue;}
//
//        SpriteComponent spriteComponent = obj.getComponent(SpriteComponent.class);
//        Transform transform = obj.getTransform();
//        if(transform == null){
//            Logger.warn("Object: " + obj.getName() + "cannot be Rendered due to missing Transform");
//            continue;
//        }
//        renderer.drawImage(spriteComponent.image,(int)(transform.x - cameraPosition.x), (int)(transform.y - cameraPosition.y), false);
//    }
//}
//private void renderObjectsWithAnimator(Renderer renderer, List<GameObject> gameObjects){
//    for (GameObject obj : getObjectsWithComponent(Animator.class, gameObjects)) {
//
//        Animator animator = obj.getComponent(Animator.class);
//        Transform transform = obj.getTransform();
//        SpriteComponent sprite = obj.getComponent(SpriteComponent.class);
//        if (transform == null) {
//            Logger.warn("Object: " + obj.getName() + "cannot be Rendered due to missing Transform");
//            continue;
//        }
//        if (animator != null) {
//            Animation currentAnim = animator.animations.get(animator.currentState);
//            if (currentAnim == null) {
//                Logger.warn("current Animation for Object: " + obj.getName() + " not existing");
//            }
//            renderer.drawImage(currentAnim.getCurrentFrame(), (int) (transform.x - cameraPosition.x), (int) (transform.y - cameraPosition.y), animator.flipX);
//
//        } else if (sprite != null) {
//            // fallback falls animation nicht existiert
//            Logger.info("used FallbackSprite for Object: " + obj.getName());
//            renderer.drawImage(sprite.image, (int) (transform.x - cameraPosition.x), (int) (transform.y - cameraPosition.y), false);
//        }
//    }
//}
