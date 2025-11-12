package HollowWorld.ECS.Components.Core;

import Engine.Math.Vector;
import HollowWorld.ECS.Components.Component;

public class RigidBody extends Component {
    // Vector ist in float deshalb muss in Movementsystem die Bewegung in Int rasterisiert werden
    public Vector velocity = new Vector(0,0);  // bewegungsVector wird dann von PhysicsSystem verwaltet
    public float gravityScale = 1.0f;
    public boolean isGrounded = false; // steht am boden oder nicht
    public boolean isKinematic = true; // wird von PhysicsSystem bewegt?
}
