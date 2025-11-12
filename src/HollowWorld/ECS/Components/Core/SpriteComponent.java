package HollowWorld.ECS.Components.Core;

import Engine.Core.Renderer;
import Engine.gfx.Image;
import HollowWorld.ECS.Components.Component;
import Engine.Logger;

public class SpriteComponent extends Component {
    public SpriteComponent(String path){
        image = new Image(path);
    }

    public Image image;
}
