package HollowWorld.ECS.Components.Terraria;

import Engine.gfx.Image;

public enum BlockType {
    DIRT(1.0f,ItemType.Test, "/Blocks/MinecraftDirtTexture.png", true),
    STONE(3.0f,ItemType.Test, "/Blocks/MinecraftStoneTexture.png", true),
    AIR(0.0f,null,null, false);

    public final float hardness;
    public final ItemType dropItem;
    public final String texturePath;
    public final boolean isSolid;
    public final Image sprite;

    BlockType(float hardness, ItemType dropItem, String texturePath, boolean isSolid) {
        this.hardness = hardness;
        this.dropItem = dropItem;
        this.texturePath = texturePath;
        this.isSolid = isSolid;
        if(texturePath != null) {
            sprite = new Image(texturePath);
        }else {sprite = null;}
    }
}
