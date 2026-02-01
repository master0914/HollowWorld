package HollowWorld.ECS.Components.Terraria;

import HollowWorld.ECS.Components.Component;



import HollowWorld.ECS.Components.Terraria.ItemType;

public class InventoryComponent extends Component {
    public ItemType[] inventory = new ItemType[3];
    public int zaehler = 0;

public void collectItem(ItemType item){
    if (zaehler < inventory.length){
        inventory[zaehler] = item;
        zaehler ++;
    }
}
}

