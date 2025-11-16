package HollowWorld.ECS.Components.Core;

import Engine.Core.GameContainer;
import Engine.gfx.Image;

public class Animation {
    public Image[] frames;
    float frameDuration;
    // vllt ein bool loop
    private float currentTime = 0;
    private int currentFrame = 0;


    public Animation(float frameDuration, Image[] animationImages){
        this.frameDuration = frameDuration;
        frames = animationImages;
    }

    public void update(float deltaTime) {
        currentTime += deltaTime;
        if(currentTime >= frameDuration){
            currentTime = 0;
            currentFrame++;
            if(currentFrame >= frames.length){
                currentFrame = 0;
            }
        }
    }

    public Image getCurrentFrame(){
        return frames[currentFrame];
    }

    public void reset(){
        currentTime = 0;
        currentFrame = 0;
    }

}
