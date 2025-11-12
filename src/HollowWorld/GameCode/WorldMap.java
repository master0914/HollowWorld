package HollowWorld.GameCode;

import HollowWorld.ECS.Components.Terraria.BlockType;
import HollowWorld.ECS.GameObjects.GameObject;



public class WorldMap {
    private BlockType[][] blocks;
    private int width;
    private int height;
    private int tileSize;

    public WorldMap(int width, int height){
        this.width = width;
        this.height = height;
        this.blocks = new BlockType[width][height];
        generateTerrain();
    }

    private void generateTerrain() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                BlockType type = BlockType.AIR;

                if (y > height / 2) {
                    type = BlockType.DIRT; // Boden
                }
                if (y > height / 2 + 3) {
                    type = BlockType.STONE; // Untergrund
                }

                blocks[x][y] = type;
            }
        }
    }

    public boolean isSolid(int gridX, int gridY){
        return blocks[gridX][gridY].isSolid;
    }


    public void placeBlock(int gridX, int gridY, BlockType type) {
        if (type == BlockType.AIR) {
            blocks[gridX][gridY] = type;
        }
    }
    public BlockType[][] getBlocks(){
        return blocks;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
