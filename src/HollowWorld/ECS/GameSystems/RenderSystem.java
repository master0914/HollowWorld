package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import Engine.Core.Renderer;
import Engine.Logger;
import HollowWorld.ECS.Components.Core.RigidBody;
import HollowWorld.ECS.Components.Core.SpriteComponent;
import HollowWorld.ECS.Components.Core.Transform;
import HollowWorld.ECS.Components.Terraria.BlockType;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.WorldMap;

import java.util.List;

public class RenderSystem extends GameSystem{
    @Override
    public void initialize() {

    }

    @Override
    public void render(GameContainer gc, Renderer renderer, List<GameObject> gameObjects, WorldMap map) {
        renderObjects(gc,renderer,gameObjects);
        renderMap(gc,renderer,map);

    }

    public void renderObjects(GameContainer gc, Renderer renderer, List<GameObject> gameObjects){

        // ObjectRendering     @TODO animationen verarbeiten
        for(GameObject obj: getObjectsWithComponent(SpriteComponent.class, gameObjects)){
            SpriteComponent spriteComponent = obj.getComponent(SpriteComponent.class);
            Transform transform = obj.getTransform();
            if(transform == null){
                Logger.warn("Object: " + obj.getName() + "cannot be Rendered due to missing Transform");
                continue;
            }
            renderer.drawImage(spriteComponent.image,transform.x, transform.y);
        }
    }
    public void renderMap(GameContainer gc, Renderer renderer, WorldMap map){
        int w = map.getWidth();
        int h = map.getHeight();
        BlockType[][] blocks = map.getBlocks();
        for(int gridX=0; gridX < w;gridX++){
            for (int gridY=0; gridY < h;gridY++){
                // hier fehlt natürlich noch kamera folgen etc.
                BlockType block = blocks[gridX][gridY];
                if(block == BlockType.AIR){continue;}
                renderer.drawImage(block.sprite, gridX * 32, gridY *32);
                renderer.drawRect(gridX * 32, gridY *32, 32,32,0xff111111);
            }
        }
    }
}
