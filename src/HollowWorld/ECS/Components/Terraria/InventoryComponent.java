package HollowWorld.ECS.Components.Terraria;

import HollowWorld.ECS.Components.Component;



import HollowWorld.ECS.Components.Terraria.ItemType;

public class InventoryComponent extends Component {
    public Slot[] inventory;

    public InventoryComponent(int slots){
        inventory = new Slot[slots];
        for(int i = 0; i < slots; i++){
            inventory[i] = new Slot();
        }
    }


    public void collectItem(ItemType item){
        boolean gleichesItem = false;

        for (int i = 0; ( i < inventory.length) && (!gleichesItem); i++){
            if(inventory[i].getItem() == item){
                gleichesItem = true;
                inventory[i].addCount(1);
            }
        }

        if (!gleichesItem){
            boolean leerenSlotGefunden = false;
            for(int i = 0; (i < inventory.length) && (!leerenSlotGefunden); i++){
                if(inventory[i].getItem() == ItemType.AIR){
                    leerenSlotGefunden = true;
                    inventory[i].setItem(item);
                    inventory[i].setCount(1);

                }
            }


        }
        inventarAusgeben();

    }


    public void inventarAusgeben(){
        System.out.println();
        for(int i = 0; i < inventory.length; i++){
            System.out.print(inventory[i].getItem() + " [" + inventory[i].getCount() + "x] | ");

        }
    }

    public int getSlotCount(){
        return inventory.length;
    }

    public Slot getSlot(int slot){
        return inventory[slot];
    }
}

