package HollowWorld.ECS.Components.Core;

import Engine.Renderer;
import Engine.gfx.Image;
import HollowWorld.ECS.Components.Component;
import HollowWorld.ECS.Logger;

public class SpriteRenderer extends Component {
    public SpriteRenderer(String path){
        image = new Image(path);
    }
    Transform transform;
    Image image;
    @Override
    public void start(){
        transform = gameObject.getTransform();
        if(transform == null){
            Logger.error("SpriteRenderer requires Transform(needs to be added First)");
        }
    }
    @Override
    public void render(Renderer r){
        r.drawImage(image, transform.x, transform.y);
    }
}
