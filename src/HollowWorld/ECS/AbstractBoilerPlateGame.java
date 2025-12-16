package HollowWorld.ECS;

import Engine.Interfaces.AbstractGame;
import Engine.Core.GameContainer;
import Engine.Logger;
import Engine.Core.Renderer;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.ECS.GameSystems.GameSystem;
import HollowWorld.ECS.GameSystems.RenderSystem;
import HollowWorld.GameCode.EventManager;
import HollowWorld.GameCode.WorldGeneration.WorldMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractBoilerPlateGame extends AbstractGame {
    private List<GameObject> gameObjects = new ArrayList<>();
    private List<GameSystem> systems = new ArrayList<>();
    private RenderSystem renderSystem = new RenderSystem();
    private WorldMap worldMap;



    @Override
    public void update(GameContainer gc, float dt) {
        if(worldMap == null){
            Logger.error("WorldMap must be initialized in init()");
        }
        for (GameSystem system : systems) {
            if (system.isEnabled()) {
                system.update(gc, dt, gameObjects, worldMap);
            }
        }
        for (GameObject obj : gameObjects) {
            obj.update(gc,dt);
        }
        gameObjects.removeIf(obj -> !obj.isActive());
        EventManager.clear();
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawBackground(0xfff0ff0f);
        renderSystem.render(gc,r,gameObjects,worldMap);
        // evtl RenderSystems
    }

    // GameObject Management------------------------------------------------
    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
    }

    public void removeGameObject(GameObject obj) {
        gameObjects.remove(obj);
    }

    public List<GameObject> getGameObjects() {
        return Collections.unmodifiableList(gameObjects);
    }

    public GameObject findGameObjectByName(String name) {
        return gameObjects.stream()
                .filter(obj -> obj.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    public List<GameObject> findGameObjectsByTag(String tag) {
        return gameObjects.stream()
                .filter(obj -> obj.getTag().equals(tag))
                .collect(Collectors.toList());
    }

    // System Management--------------------------------------------------
    public void addSystem(GameSystem system) {
        systems.add(system);
        system.initialize();
    }

    public void removeSystem(GameSystem system) {
        system.cleanup();
        systems.remove(system);
    }

    public <T extends GameSystem> T getSystem(Class<T> type) {
        for (GameSystem system : systems) {
            if (type.isInstance(system)) {
                return type.cast(system);
            }
        }
        Logger.warn("requested System not available");
        return null;
    }

    public void setWorldMap(WorldMap map){
        worldMap = map;
    }
}
