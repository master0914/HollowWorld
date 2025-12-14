package HollowWorld.ECS.Components.Terraria;

import HollowWorld.ECS.Components.Component;
import HollowWorld.ECS.Components.Terraria.ItemType;
import HollowWorld.ECS.Components.Core.*;
import HollowWorld.GameCode.EntityFactory;


public class ItemComponent extends Component {
    public ItemComponent(ItemType nItem){
        ItemType item = nItem;
        System.out.println(item.getTexturePath(item));


    }
}
