package HollowWorld.ECS.Components.Terraria;

import HollowWorld.ECS.Components.Component;



import HollowWorld.ECS.Components.Terraria.ItemType;

public class InventoryComponent extends Component {
    public ItemType[] inventory;
    public int zaehler = 0;

    public void collectItem(ItemType item){
        boolean gleichesItem = false;
        for (int i = 0;( i < zaehler) && (gleichesItem == false); i++){
            if(inventory[i] == item){
                gleichesItem = true;

            }
        }
        if (zaehler < inventory.length){
            inventory[zaehler] = item;
            zaehler ++;

        }

    }
    public InventoryComponent(int slots){
        inventory = new ItemType[slots];
    }

    public void inventarAusgeben(){
        for(int i = 0; i < zaehler; i++){
            System.out.println(i + " " + inventory[i].id);

        }
    }
}

