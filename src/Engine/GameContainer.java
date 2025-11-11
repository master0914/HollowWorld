package Engine;


import HollowWorld.ECS.Logger;

public class GameContainer implements Runnable{

    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;
    private AbstractGame game;

    private boolean running = false;
    private final double UPDATE_CAP = (1.0/60.0);
    private boolean paused;

    double lastTime = System.nanoTime() / 1e9; //nanosekunde in sekunde

    private int width = 1920, height = 1080;
    private float scale = 2;
    private String title = "MorpheusEngine v0.1";


    public GameContainer(AbstractGame game, int width, int height){
        this.width = width;
        this.height = height;
        this.game = game;
    }

    public GameContainer(AbstractGame game){
        this.game = game;
    }

    public void start(){
        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input(this);
        game.init();

        thread = new Thread(this);
        thread.run();
    }

    public void stop(){
        running = false;
    }

    public void run(){
        running = true;

        boolean render = false;
        double firstTime = 0;
        double passedTime = 0;
        double unprocessedTime = 0;
        lastTime = System.nanoTime() / 1e9;
        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while (running){
            if (paused) {
                try {
                    Thread.sleep(100); // Sleep longer when paused
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lastTime = System.nanoTime() / 1e9;
                continue;
            }

            render = false;

            firstTime = System.nanoTime() / 10E8;
            passedTime = firstTime - lastTime; //berechnet die für den letzten loop gebrauchte zeit

            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while (unprocessedTime >= UPDATE_CAP){
                unprocessedTime -= UPDATE_CAP;
                render = true;

                game.update(this, (float)UPDATE_CAP);

                input.update();
                if(frameTime >= 1){
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }

            }

            if(render){
                renderer.clear();

                game.render(this, renderer);
                window.update();
                frames++;
            }
            else {
                try {
                    Thread.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

        }
        dispose();
    }

    private void dispose(){
        try {
            thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // @TODO weiß nicht ob das hier ganz richtig ist sollte aber gehen
        Logger.shutdown();
        System.exit(0);
    }

    private void pause(){
        paused = true;
    }

    private void unpause(){
        paused = false;
    }

    public void pauseGame(long durationMillis) {
        paused = true;
        long startTime = System.currentTimeMillis();

        // Warten bis durationMillis erreicht wurde
        while (System.currentTimeMillis() - startTime < durationMillis) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // stellt vorherigen Threadzustand her um nicht abzustürzen
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Reset timing variables to avoid a large delta on resume
        lastTime = System.nanoTime() / 1e9;
        paused = false;
    }





    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Window getWindow() {
        return window;
    }

    public Input getInput() {
        return input;
    }

    public Renderer getRenderer(){
        return renderer;
    }
}
