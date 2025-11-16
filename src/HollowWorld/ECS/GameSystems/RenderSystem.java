package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import Engine.Core.Renderer;
import Engine.Logger;
import Engine.Math.Vector;
import HollowWorld.ECS.Components.Core.Animation;
import HollowWorld.ECS.Components.Core.Animator;
import HollowWorld.ECS.Components.Core.SpriteComponent;
import HollowWorld.ECS.Components.Core.Transform;
import HollowWorld.ECS.Components.Player.CameraFollow;
import HollowWorld.ECS.Components.Terraria.BlockType;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.WorldMap;

import java.util.List;

public class RenderSystem extends GameSystem{
    private Vector cameraPosition = new Vector(0,0);

    @Override
    public void initialize() {

    }

    @Override
    public void render(GameContainer gc, Renderer renderer, List<GameObject> gameObjects, WorldMap map) {
        //Logger.info(cameraPosition.toString());
        updateCamera(gameObjects);
        renderObjectsWithAnimator(renderer,gameObjects);
        renderObjectsWithSprite(renderer,gameObjects);
        renderMap(gc,renderer,map);

    }

    private void renderObjectsWithSprite(Renderer renderer, List<GameObject> gameObjects){
        // ObjectRendering     @TODO animationen verarbeiten
        for(GameObject obj: getObjectsWithComponent(SpriteComponent.class, gameObjects)){
            if(obj.hasComponent(Animator.class)){continue;}

            SpriteComponent spriteComponent = obj.getComponent(SpriteComponent.class);
            Transform transform = obj.getTransform();
            if(transform == null){
                Logger.warn("Object: " + obj.getName() + "cannot be Rendered due to missing Transform");
                continue;
            }
            renderer.drawImage(spriteComponent.image,(int)(transform.x - cameraPosition.x), (int)(transform.y - cameraPosition.y), false);
        }
    }
    private void renderObjectsWithAnimator(Renderer renderer, List<GameObject> gameObjects){
        for (GameObject obj : gameObjects) {
            Animator animator = obj.getComponent(Animator.class);
            Transform transform = obj.getTransform();
            SpriteComponent sprite = obj.getComponent(SpriteComponent.class);
            if(transform == null){Logger.warn("Object: " + obj.getName() + "cannot be Rendered due to missing Transform"); continue;}
            if (animator != null) {
                Animation currentAnim = animator.animations.get(animator.currentState);
                if (currentAnim == null) {Logger.warn("current Animation for Object: " + obj.getName() + " not existing");}
                renderer.drawImage(currentAnim.getCurrentFrame(),(int)(transform.x - cameraPosition.x), (int)(transform.y - cameraPosition.y), animator.flipX);

            } else if (sprite != null) {
                // fallback falls animation nicht existiert
                Logger.info("used FallbackSprite for Object: " + obj.getName());
                renderer.drawImage(sprite.image, (int)(transform.x - cameraPosition.x), (int)(transform.y - cameraPosition.y), false);
            }
        }
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
