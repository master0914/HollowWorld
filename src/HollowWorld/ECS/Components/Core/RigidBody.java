package HollowWorld.ECS.Components.Core;

import Engine.Math.IVector;
import HollowWorld.ECS.Components.Component;

public class RigidBody extends Component {
    public IVector velocity = new IVector(0,0);  // bewegungsVector wird dann von PhysicsSystem verwaltet
    public float gravityScale = 1.0f;
    public boolean isGrounded = false; // steht am boden oder nicht
    public boolean isKinematic = false; // wird von PhysicsSystem bewegt?
}
