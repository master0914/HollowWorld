import Engine.GameContainer;
import HollowWorld.ECS.Logger;
import HollowWorld.GameCode.MainGame;

public class Main {
    public static void main(String[] args) {
        // am besten so lassen. in Datei "Logger" mehr details
        // Logger.logToFile();



        GameContainer gc = new GameContainer(new MainGame(), 1000,800);
        // Settings einstellen

        gc.setScale(1);


        // Setting einstellen ende
        gc.start();

    }
}