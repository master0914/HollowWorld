package HollowWorld.ECS.Components.Terraria;

import Engine.gfx.Image;

public enum ItemType {
    DIRT(001f,"Einfacher Baustoff","/Items/DirtItem.png"),
    STONE(002f,"Grundbestandteil der Welt","/Items/StoneItem.png"),
    PLANKS(003f,"Planks","/Items/PlanksItem.png"),
    COAL(004f,"Coal","/Items/CoalItem.png");


    public final float id;
    public final String description;
    public final String texturePath;
    public final Image sprite;



    ItemType(float id, String description, String texturePath){
        this.id = id;
        this.description = description;
        this.texturePath = texturePath;

        if(texturePath != null) {
            sprite = new Image(texturePath);
        }else {sprite = null;}
    }


    // hier muss natürlich noch viel hin


}


