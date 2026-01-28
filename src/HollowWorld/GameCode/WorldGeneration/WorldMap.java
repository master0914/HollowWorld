package HollowWorld.GameCode.WorldGeneration;
import HollowWorld.ECS.Components.Terraria.BlockType;


public class WorldMap {
    private final BlockType[][] blocks;
    private final int width;
    private final int height;
    // private int tileSize;

    public WorldMap(int width, int height){
        this.width = width;
        this.height = height;
        this.blocks = new BlockType[width][height];
        generateTerrain();
    }

    public BlockType getBlockType(float noiseValue) {
        if (noiseValue < -0.4f) {
            return BlockType.DIRT;
        } else if (noiseValue < -0.2f) {
            return BlockType.STONE;
        } else {
            return BlockType.AIR;
        }
    }
    private void generateTerrain() {
        // noise setup for 1d noise, also die oberste linie
        FastNoiseLite surfaceNoise = new FastNoiseLite();
        surfaceNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        surfaceNoise.SetSeed(1337);
        surfaceNoise.SetFrequency(0.02f);

        // noise setup for 2d noise, also alles unter 1d noise linie
        FastNoiseLite caveNoise = new FastNoiseLite();
        caveNoise.SetNoiseType(FastNoiseLite.NoiseType.Value);
        caveNoise.SetSeed(420);
        caveNoise.SetFrequency(0.08f);

        // chatgpt hat die brechnungen gemacht
        int middleY = height / 2;

        for (int x = 0; x < width; x++) {
            float surfaceNoiseValue = surfaceNoise.GetNoise(x, 0);

            // chatgpt hat die brechnungen gemacht
            int surfaceHeightVariance = 5; // ich glaube wie hoch berge werden können bzw der unterschied zum standard level
            int surfaceY = middleY + (int) (surfaceNoiseValue * surfaceHeightVariance);

            for (int y = 0; y < height; y++) {
                BlockType type = BlockType.AIR;


                if (y < surfaceY){
                    type = BlockType.AIR;
                } else if (y == surfaceY) {
                    type = BlockType.DIRT;
                } else {

                    // untergrund
                    if (y < surfaceY + 5) {
                        type = BlockType.DIRT;
                    } else {
                        type = BlockType.STONE;
                    }

                    float caveNoiseValue = caveNoise.GetNoise(x, y);
                    // höhlen dichte
                    if (caveNoiseValue > 0.1f) {
                        type = BlockType.AIR;
                    }
                }
                blocks[10][6] = BlockType.PLANKS;
                blocks[10][5] = BlockType.PLANKS;
                blocks[10][4] = BlockType.PLANKS;
                blocks[9][5] = BlockType.PLANKS;
                blocks[11][6] = BlockType.PLANKS;
                blocks[11][4] = BlockType.PLANKS;
                blocks[12][6] = BlockType.PLANKS;
                blocks[12][5] = BlockType.PLANKS;
                blocks[12][4] = BlockType.PLANKS;

                blocks[12][15] = BlockType.COAL_ORE;
                blocks[13][15] = BlockType.COAL_ORE;
                blocks[14][15] = BlockType.COAL_ORE;

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

    public BlockType getBlock(int x, int y){
        return blocks[x][y];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
