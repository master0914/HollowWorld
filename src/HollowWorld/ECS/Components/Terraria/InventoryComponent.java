package HollowWorld.ECS.Components.Terraria;

import HollowWorld.ECS.Components.Component;



import HollowWorld.ECS.Components.Terraria.ItemType;
import HollowWorld.ECS.Events.DropItemBlock;
import HollowWorld.GameCode.EventManager;

public class InventoryComponent extends Component {
    public Slot[] inventory;
    public int selectedSlot;

    public InventoryComponent(int slots){
        inventory = new Slot[slots];
        for(int i = 0; i < slots; i++){
            inventory[i] = new Slot();
        }
        selectedSlot = 0;
        inventory[0].setSelected(true);
    }


    public void collectItem(ItemType item){
        boolean gleichesItem = false;

        for (int i = 0; ( i < inventory.length) && (!gleichesItem); i++){
            if(inventory[i].getItem() == item){
                gleichesItem = true;
                inventory[i].addCount(1);
            }
        }

        if (!gleichesItem) {
            boolean leerenSlotGefunden = false;
            for (int i = 0; (i < inventory.length) && (!leerenSlotGefunden); i++) {
                if (inventory[i].getItem() == ItemType.AIR) {
                    leerenSlotGefunden = true;
                    inventory[i].setItem(item);
                    inventory[i].setCount(1);
                }
            }
        }
    }


    public void inventarAusgeben(){
        System.out.println();
        for(int i = 0; i < inventory.length; i++){
            System.out.print(inventory[i].getItem() + " [" + inventory[i].getCount() + "x] | ");

        }
    }

    public void dropItem(int x, int y, ItemType item){
        if(item != ItemType.AIR){
            EventManager.addEvent(new DropItemBlock(item,1,x,y));
            inventory[getSelectedSlot()].addCount(-1);

            if(inventory[getSelectedSlot()].getCount() <= 0){
                inventory[getSelectedSlot()].setCount(0);
                inventory[getSelectedSlot()].setItem(ItemType.AIR);
                System.out.println(getSelectedSlot());

            }
        }


    }

    public int getSlotCount(){
        return inventory.length;
    }

    public Slot getSlot(int slot){
        return inventory[slot];
    }

    public void scrollSelected(){
        resetSelected();
        if(selectedSlot < 8){
            selectedSlot++;
        }
        else{
            selectedSlot = 0;
        }
        inventory[selectedSlot].setSelected(true);
    }

    public void setSelectedSlot(int slot){
        resetSelected();
        inventory[slot].setSelected(true);
    }

    private void resetSelected(){
        for(int i = 0; i < inventory.length; i++){
            inventory[i].setSelected(false);
        }
    }

    public int getSelectedSlot(){
        int selected = 0;
        for(int i = 0; i < inventory.length; i++){
            if(inventory[i].getSelected()){
                selected = i;
            }
        }
        return selected;
    }

    public ItemType getSelectedSlotItem(){
        ItemType item;
        item = inventory[getSelectedSlot()].getItem();
        return item;
    }
}

