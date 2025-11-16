package HollowWorld.GameCode;

import HollowWorld.ECS.AbstractBoilerPlateGame;
import HollowWorld.ECS.Components.Combat.Combat;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.ECS.GameSystems.AnimationSystem;
import HollowWorld.ECS.GameSystems.MovementSystem;
import HollowWorld.ECS.GameSystems.PhysicsSystem;
import HollowWorld.ECS.GameSystems.RenderSystem;

import static HollowWorld.GameCode.EntityFactory.*;
import static HollowWorld.Main.Main.SCREENHEIGHT;
import static HollowWorld.Main.Main.SCREENWIDTH;


public class MainGame extends AbstractBoilerPlateGame {

    @Override
    public void init() {
        // Systeme Registrieren
        addSystem(new PhysicsSystem());
        addSystem(new MovementSystem());
        addSystem(new AnimationSystem());

        setWorldMap(new WorldMap(SCREENWIDTH / 32,SCREENHEIGHT / 32));


        GameObject player = makePlayer();
        addGameObject(player);

    }
}
