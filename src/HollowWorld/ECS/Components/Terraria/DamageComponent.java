package HollowWorld.ECS.Components.Terraria;

import HollowWorld.ECS.Components.Component;

public class DamageComponent extends Component {
    public int damagescore;
    public DamageComponent(int damage){
        damagescore=damage;
    }
    public int DamageComponentWertGeben() {
        return damagescore;
    }
}
