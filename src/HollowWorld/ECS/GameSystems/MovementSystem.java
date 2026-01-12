package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import Engine.Logger;
import HollowWorld.ECS.Components.Core.RigidBody;
import HollowWorld.ECS.Components.Player.PlatformerMovement;
import HollowWorld.ECS.Components.Player.PlayerInput;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.WorldGeneration.WorldMap;

import java.util.List;

public class MovementSystem extends GameSystem{
    @Override
    public void initialize() {

    }

    @Override
    public void update(GameContainer gc, float dt, List<GameObject> gameObjects, WorldMap map) {
        // Hier wird PlayerMovementVerarbeitet.      Später hier auch Enemy Movement etc.
        GameObject player = findPlayer(gameObjects);
        if (player == null) {
            Logger.warn("no Player in Game!!! Should not Happen");
            return;
        }

        PlatformerMovement playerMovement = player.getComponent(PlatformerMovement.class);
        RigidBody playerRigidBody = player.getComponent(RigidBody.class);
        PlayerInput input = player.getComponent(PlayerInput.class);

        if (playerMovement == null || playerRigidBody == null || input == null) {
            Logger.warn("Player does not have All Components!!! Should not Happen");
        } else {
            playerRigidBody.velocity.x = input.getHorizontal() * playerMovement.moveSpeed;

            if (input.isJumpPressed() && playerRigidBody.isGrounded) {
                playerRigidBody.velocity.y = -playerMovement.jumpForce;
                playerRigidBody.isGrounded = false; // das hier vllt in physics automatisch berechnen
            }
        }

    }



}
