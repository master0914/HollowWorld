package HollowWorld.GameCode.WorldGeneration;
import HollowWorld.ECS.Components.Terraria.BlockType;

import static java.lang.Math.random;
// wie die höhlen funktionieren https://code.tutsplus.com/generate-random-cave-levels-using-cellular-automata--gamedev-9664t


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
    // cave generation


//Returns the number of cells in a ring around (x,y) that are alive.

    public int countAliveNeighbours(boolean[][] map, int x, int y){
        int count = 0;
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                int neighbour_x = x+i;
                int neighbour_y = y+j;
                //If we're looking at the middle point
                if(i == 0 && j == 0){
                    //Do nothing, we don't want to add ourselves in!
                }
                //In case the index we're looking at it off the edge of the map
                else if(neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= map.length || neighbour_y >= map[0].length){
                    count = count + 1;
                }
                //Otherwise, a normal check of the neighbour
                else if(map[neighbour_x][neighbour_y]){
                    count = count + 1;
                }
            }
        }
        return count;
    }

    public boolean[][] doSimulationStep(boolean[][] oldMap){
        boolean[][] newMap = new boolean[width][height];
        int deathLimit = 0;
        int birthLimit = 0;
        //Loop over each row and column of the map
        for(int x=0; x<oldMap.length; x++){
            for(int y=0; y<oldMap[0].length; y++){
                int nbs = countAliveNeighbours(oldMap, x, y);
                //The new value is based on our simulation rules
                //First, if a cell is alive but has too few neighbours, kill it.
                if(oldMap[x][y]){
                    if(nbs < deathLimit){
                        newMap[x][y] = false;
                    }
                    else{
                        newMap[x][y] = true;
                    }
                } //Otherwise, if the cell is dead now, check if it has the right number of neighbours to be 'born'
                else{
                    if(nbs > birthLimit){
                        newMap[x][y] = true;
                    }
                    else{
                        newMap[x][y] = false;
                    }
                }
            }
        }
        return newMap;
    }

    float chanceToStartAlive = 0.45f;

    public boolean[][] initialiseMap(boolean[][] map){

        for(int x=0; x<width; x++){

            for(int y=0; y<height; y++){

                if(random() < chanceToStartAlive){

                    map[x][y] = true;

                }

            }

        }

        return map;

    }

    public boolean[][] generateMap(){
        //Create a new map
        boolean[][] cellmap = new boolean[width][height];
        //Set up the map with random values
        cellmap = initialiseMap(cellmap);
        //And now run the simulation for a set number of steps
        int numberOfSteps = 3;
        for(int i = 0; i<numberOfSteps; i++){
            cellmap = doSimulationStep(cellmap);
        }
        return cellmap;
    }

    public Enum<BlockType> BlockType(float noiseValue) {
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

        boolean[][] cellmap = new boolean[width][height];

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
                    //if (y < surfaceY + 5) {
                    //    type = BlockType.DIRT;
                    //} else {
                    //    type = BlockType.STONE;
                    //}

                    // cave generation
                    // TODO generate caves
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