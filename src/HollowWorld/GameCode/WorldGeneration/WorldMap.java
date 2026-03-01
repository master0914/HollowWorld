package HollowWorld.GameCode.WorldGeneration;
import HollowWorld.ECS.Components.Terraria.BlockType;
import static java.lang.Math.random;

public class WorldMap {

    private final BlockType[][] blocks;
    private final int width;
    private final int height;

    public WorldMap(int width, int height){
        this.width = width;
        this.height = height;
        this.blocks = new BlockType[width][height];
        generateTerrain();
    }

    // Implementierung von der Cellular Automata Section
    // für nähere infos
    // https://code.tutsplus.com/generate-random-cave-levels-using-cellular-automata--gamedev-9664t
    // chatgpt hat geholfen den algo richtig zu implementieren
    // (ich hatte echt keine lust, mich lange damit auseinanderzusetzen)

    public int countAliveNeighbours(boolean[][] map, int x, int y){
        int count = 0;

        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){

                int neighbour_x = x + i;
                int neighbour_y = y + j;

                if(i == 0 && j == 0){
                    continue;
                }
                else if(neighbour_x < 0 || neighbour_y < 0 ||
                        neighbour_x >= map.length ||
                        neighbour_y >= map[0].length){
                    count++;
                }
                else if(map[neighbour_x][neighbour_y]){
                    count++;
                }
            }
        }
        return count;
    }

    public boolean[][] doSimulationStep(boolean[][] oldMap){
        boolean[][] newMap = new boolean[oldMap.length][oldMap[0].length];

        for(int x = 0; x < oldMap.length; x++){
            for(int y = 0; y < oldMap[0].length; y++){

                int nbs = countAliveNeighbours(oldMap, x, y);

                if(oldMap[x][y]){
                    int deathLimit = 4;
                    newMap[x][y] = nbs >= deathLimit;
                } else {
                    int birthLimit = 4;
                    newMap[x][y] = nbs > birthLimit;
                }
            }
        }
        return newMap;
    }

    public boolean[][] initialiseMap(boolean[][] map){
        for(int x = 0; x < map.length; x++){
            for(int y = 0; y < map[0].length; y++){
                // Cellular automata settings
                float chanceToStartAlive = 0.45f;
                map[x][y] = random() < chanceToStartAlive;
            }
        }
        return map;
    }

    public boolean[][] generateCaveMap(int caveHeight){
        boolean[][] cellmap = new boolean[width][caveHeight];

        cellmap = initialiseMap(cellmap);

        int numberOfSteps = 4;
        for(int i = 0; i < numberOfSteps; i++){
            cellmap = doSimulationStep(cellmap);
        }

        return cellmap;
    }

    // hier werden dann sozusagen die blöcke platziert

    private void generateTerrain() {

        // 1D surface noise
        FastNoiseLite surfaceNoise = new FastNoiseLite();
        surfaceNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        surfaceNoise.SetSeed(1337);
        surfaceNoise.SetFrequency(0.02f);

        int middleY = height / 2;
        int surfaceHeightVariance = 6;

        // höhe unter der höhlen generieren
        int caveStartY = height / 3;
        int caveHeight = height - caveStartY;

        boolean[][] cellmap = generateCaveMap(caveHeight);

        for (int x = 0; x < width; x++) {

            float surfaceNoiseValue = surfaceNoise.GetNoise(x, 0);
            int surfaceY = middleY + (int)(surfaceNoiseValue * surfaceHeightVariance);

            for (int y = 0; y < height; y++) {

                BlockType type;

                // himmel
                if (y < surfaceY) {
                    type = BlockType.AIR;
                }
                // oberste linie
                else if (y == surfaceY) {
                    type = BlockType.DIRT;
                }
                // dickere obere linie
                else if (y <= surfaceY + 3) {
                    type = BlockType.DIRT;
                }
                // höhlen
                else {
                    // hoch bis tiefpunkt der höhlen
                    int caveY = y - caveStartY;

                    if (caveY >= 0 && caveY < cellmap[0].length) {
                        if (cellmap[x][caveY]) {
                            type = BlockType.STONE;
                        } else {
                            type = BlockType.AIR;
                        }
                    } else {
                        type = BlockType.STONE;
                    }
                }

                blocks[x][y] = type;
            }
        }
    }



    public boolean isSolid(int gridX, int gridY){
        return blocks[gridX][gridY].isSolid;
    }

    public void placeBlock(int gridX, int gridY, BlockType type){
        blocks[gridX][gridY] = type;
    }

    public BlockType[][] getBlocks(){
        return blocks;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }
}