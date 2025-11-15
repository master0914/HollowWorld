package HollowWorld.ECS.Components.Player;

import Engine.Math.Vector;
import HollowWorld.ECS.Components.Component;
import HollowWorld.Main.Main;

public class CameraFollow extends Component {
    public float smoothSpeed = 0.5f;
    // berechnungen sorgen dafür dass obj immer in der mitte des screens ist TODO tweaken dass zentrum des players in der mitte liegt
    public Vector offset = new Vector(-(Main.SCREENWIDTH - Main.SCREENWIDTH/2.0f),-(Main.SCREENHEIGHT - Main.SCREENHEIGHT/2.0f));
    public boolean bounded = false;
}
