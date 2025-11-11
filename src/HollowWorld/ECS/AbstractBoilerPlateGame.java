package HollowWorld.ECS;

import Engine.AbstractGame;
import Engine.GameContainer;
import Engine.Renderer;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.ECS.GameSystems.GameSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractBoilerPlateGame extends AbstractGame {
    private List<GameObject> gameObjects = new ArrayList<>();
    private List<GameSystem> systems = new ArrayList<>();



    @Override
    public void update(GameContainer gc, float dt) {
        for (GameSystem system : systems) {
            if (system.isEnabled()) {
                system.update(gc, dt, gameObjects);
            }
        }
        for (GameObject obj : gameObjects) {
            obj.update(dt);
        }
        gameObjects.removeIf(obj -> !obj.isActive());
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        for (GameObject obj : gameObjects) {
            obj.render(r);
        }
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
        //@TODO logging
        return null;
    }
}
