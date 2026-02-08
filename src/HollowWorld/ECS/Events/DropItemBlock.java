package HollowWorld.ECS.Events;

import HollowWorld.ECS.Components.Terraria.ItemType;



public class DropItemBlock implements Event {

    private final ItemType item;
    private final int count;
    private final int x;
    private final int y;


    public DropItemBlock(ItemType item, int count, int x, int y){
        this.item = item;
        this.count = count;
        this.x = x;
        this.y = y;
    }

    public int getCount(){ return count; }
    public ItemType getItem(){ return item; }
    public int getX(){ return x; }
    public int getY(){ return y; }
}
