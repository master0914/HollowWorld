package HollowWorld.ECS.Components.Terraria;

import HollowWorld.ECS.Components.Component;

public class HealthComponent extends Component {
    public int healthscore;

    public HealthComponent(int health) {
        healthscore = health;
    }

    public int HealthComponentWertGeben() {
        return healthscore;
    }

    public void DamageVonHealthAbziehen(int damage) {//zieht Schaden ab
        if (healthscore > 0) {//kein negativer Healthwert
            healthscore -= damage;
            if (healthscore <= 0) {
                healthscore = 0;
                System.out.println("Player/Enemy ist gestorben! ");//muss noch unterschieden werden
            }
        }
    }
}