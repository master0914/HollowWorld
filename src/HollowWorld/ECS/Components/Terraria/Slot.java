package HollowWorld.ECS.Components.Terraria;

public class Slot {
    private int count;
    private ItemType item;

    public Slot(){
        count = 0;
        item = ItemType.AIR;
    }

    public int getCount(){
        return count;
    }

    public ItemType getItem(){
        return item;
    }

    public void setCount(int nCount){
        count = nCount;
    }

    public void addCount(int add){
        count += add;
    }

    public void setItem(ItemType nItem){
        item = nItem;
    }
}
