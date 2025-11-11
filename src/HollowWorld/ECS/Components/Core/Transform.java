package HollowWorld.ECS.Components.Core;

import HollowWorld.ECS.Components.Component;

public class Transform extends Component {
    // Immer als erstes Hinzufügen
    public Transform(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int x, y;
}
