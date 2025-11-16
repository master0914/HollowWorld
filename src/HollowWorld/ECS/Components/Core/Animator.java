package HollowWorld.ECS.Components.Core;

import Engine.Logger;
import HollowWorld.ECS.Components.Component;

import java.util.HashMap;
import java.util.Map;

public class Animator extends Component {
    public Map<AnimationState, Animation> animations = new HashMap<>();
    public AnimationState currentState = AnimationState.IDLE;
    public AnimationState previousState = AnimationState.IDLE;
    public boolean flipX = false;

    public void addAnimation(AnimationState state, Animation animation){
        animations.put(state,animation);
    }
    public void setState(AnimationState newState) {
        if(!animations.containsKey(newState)){
            Logger.warn("this Animator does not have an Animation for state: " + newState);
            return;
        }
        if (!newState.equals(currentState)) {
            previousState = currentState;
            currentState = newState;
            animations.get(currentState).reset();
        }
    }
    public enum AnimationState{
        IDLE(),
        RUN();
    }
}
