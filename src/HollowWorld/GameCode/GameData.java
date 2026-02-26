package HollowWorld.GameCode;

import Engine.Math.IVector;
import Engine.Math.Vector;
import static HollowWorld.Main.Main.SCREENHEIGHT;
import static HollowWorld.Main.Main.SCREENWIDTH;

public class GameData {
    // camera
    public static Vector cameraPosition = new Vector(0,0);

    public static IVector screenXYtoBlockXY(int screenX, int screenY){
        float worldX = cameraPosition.x + (screenX);
        float worldY = cameraPosition.y + (screenY);

        int blockX = (int) Math.floor(worldX / 32);
        int blockY = (int) Math.floor(worldY / 32);

        return new IVector(blockX, blockY);
    }

}
