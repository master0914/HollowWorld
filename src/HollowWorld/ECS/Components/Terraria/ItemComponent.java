package HollowWorld.ECS.Components.Terraria;

import Engine.Core.Renderer;
import Engine.gfx.Image;
import HollowWorld.ECS.Components.Component;
import HollowWorld.ECS.Components.Terraria.ItemType;
import HollowWorld.ECS.Components.Core.*;
import HollowWorld.GameCode.EntityFactory;



public class ItemComponent extends Component {
    ItemType item;

    public ItemComponent(ItemType nItem){
        item = nItem;
    }

    public ItemType getItem(){
        return item;
    }
}
