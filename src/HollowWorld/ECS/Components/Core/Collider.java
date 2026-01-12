package HollowWorld.ECS.Components.Core;

import HollowWorld.ECS.Components.Component;

public class Collider extends Component {
//    public Collider(int radius){
//        this.radius = radius;
//        this.type = ColliderType.CIRCLE;
//    }
    public Collider(int w, int h){
        this.width = w;
        this.height = h;
        this.type = ColliderType.BOX;
    }
    public ColliderType type;
    public int width, height; // für BOX
//    public float radius;        // für CIRCLE
    public boolean isTrigger = false;
    public boolean isSolid = true;
    public enum ColliderType {
        BOX,
        CIRCLE;
    }

    public void setSolid(boolean solid){
        isSolid = solid;
    }

    public boolean getSolid(){
        return isSolid;
    }
}
