package HollowWorld.ECS.Components.Player;

import HollowWorld.ECS.Components.Component;

public class PlatformerMovement extends Component {
    // Bewegung
    public float moveSpeed = 400f;
    public float jumpForce = 400f;
    public float wallJumpForce = 10f;
    public int maxAirJumps = 1;
    public float wallSlideSpeed = 2f;
    // Zustand
    public int airJumpsRemaining;
    public boolean isWallSliding;
    public boolean wasGrounded;

}
