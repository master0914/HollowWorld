package HollowWorld.ECS.Components.Terraria;

public class Slot {
    private int count;
    private ItemType item;
    private boolean selected;

    public Slot(){
        count = 0;
        item = ItemType.AIR;
        selected = false;
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

    public void setSelected(boolean nSelected){
        selected = nSelected;
    }

    public boolean getSelected(){
        return selected;
    }

}
