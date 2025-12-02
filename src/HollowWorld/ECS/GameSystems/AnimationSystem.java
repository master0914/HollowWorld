package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import Engine.Logger;
import HollowWorld.ECS.Components.Core.Animation;
import HollowWorld.ECS.Components.Core.Animator;
import HollowWorld.ECS.Components.Core.RigidBody;
import HollowWorld.ECS.Components.Core.Transform;
import HollowWorld.ECS.Components.Player.PlatformerMovement;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.WorldMap;

import java.util.List;

public class AnimationSystem extends GameSystem{
    @Override
    public void initialize() {

    }

    @Override
    public void update(GameContainer gc, float dt, List<GameObject> gameObjects, WorldMap map) {
        for(GameObject obj: getObjectsWithComponent(Animator.class, gameObjects)){
            Animator animator = obj.getComponent(Animator.class);

            updateAnimationState(obj, animator);

            Animation currentAnimation = animator.animations.get(animator.currentState);
            if(currentAnimation == null){
                Logger.warn("tried to update non existing animation for state: " + animator.currentState.toString());
            }else {
                currentAnimation.update(dt);
                //Logger.info("Current Animation: " + animator.currentState + " frame: " +currentAnimation.getCurrentFrame());
            }
        }
    }

    public void updateAnimationState(GameObject obj, Animator animator){
        // bisher nur für Player funktionierend
        RigidBody rb = obj.getComponent(RigidBody.class);
        PlatformerMovement movement = obj.getComponent(PlatformerMovement.class);

        if(rb == null) return;

        if(Math.abs(rb.velocity.x) > 0f){
            animator.setState(Animator.AnimationState.RUN);
        }else {
            animator.setState(Animator.AnimationState.IDLE);
        }

        if (rb.velocity.x > 0.1f) animator.flipX = false;
        if (rb.velocity.x < -0.1f) animator.flipX = true;
    }
}
