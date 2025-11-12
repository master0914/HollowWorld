package HollowWorld.Main;

import Engine.Core.GameContainer;
import HollowWorld.GameCode.MainGame;

public class Main {
    public static int SCREENWIDTH = 1024;
    public static int SCREENHEIGHT = 512;


    public static void main(String[] args) {
        // am besten so lassen. in Datei "Logger" mehr details
        // Logger.logToFile();



        GameContainer gc = new GameContainer(new MainGame(), SCREENWIDTH,SCREENHEIGHT);
        // Settings einstellen

        gc.setScale(1);


        // Setting einstellen ende
        gc.start();

    }
}