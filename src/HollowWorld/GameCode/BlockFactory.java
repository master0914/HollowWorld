package HollowWorld.GameCode;

import HollowWorld.ECS.Components.Core.Collider;
import HollowWorld.ECS.Components.Core.SpriteComponent;
import HollowWorld.ECS.Components.Core.Transform;
import HollowWorld.ECS.Components.Terraria.BlockType;
import HollowWorld.ECS.GameObjects.GameObject;

public class BlockFactory {
//    public static GameObject createBlock(int gridX, int gridY, BlockType type){
//        GameObject block = new GameObject("Block_"+gridX+"_"+gridY);
//        block.setTag("Block");
//
//        Transform transform = new Transform(gridX*32,gridY*32);
//        block.addComponent(transform);
//
//        SpriteComponent spriteComponent = new SpriteComponent(type.texturePath);
//        block.addComponent(spriteComponent);
//
//        BlockComponent blockComp = new BlockComponent(type);
//        block.addComponent(blockComp);
//
//        if (type != BlockType.AIR) {
//            Collider collider = new Collider(32,32);
//            collider.isSolid = true;
//            block.addComponent(collider);
//        }
//
//        return block;
//    }
}
