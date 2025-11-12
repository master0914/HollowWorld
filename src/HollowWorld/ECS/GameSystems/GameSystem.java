package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import Engine.Core.Renderer;
import HollowWorld.ECS.Components.Component;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.WorldMap;

import java.util.List;
import java.util.stream.Collectors;

public abstract class GameSystem {
    protected boolean enabled = true;

    // Muss beim hinzufügen des Systems aufgerufen werden
    public abstract void initialize();

    // haupt update methode
    public void update(GameContainer gc, float dt, List<GameObject> gameObjects, WorldMap map) {}
    public void render(GameContainer gc, Renderer renderer, List<GameObject> gameObjects, WorldMap map) {}

    // Muss beim entfernen aufgerufen werden
    public void cleanup() {}

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public boolean isEnabled() { return enabled; }

    protected <T extends Component> List<GameObject> getObjectsWithComponent(Class<T> componentType, List<GameObject> allObjects) {
        return allObjects.stream()
                .filter(obj -> obj.isActive() && obj.hasComponent(componentType))
                .collect(Collectors.toList());
    }
}
