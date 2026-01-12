package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import Engine.Core.Renderer;
import Engine.Logger;
import HollowWorld.ECS.Components.Component;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.WorldGeneration.WorldMap;

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
    protected <T extends Component> GameObject getObjectsByName(String name, List<GameObject> allObjects){
        List<GameObject> list = allObjects.stream()//durchsucht alle  gameObjects nach dem Spieler
                .filter(obj -> obj.isActive() && name.equals(obj.getName()))
                .collect(Collectors.toList());
        if(list.size() != 1){
            if(list.isEmpty()){
                Logger.warn("Tried to get obj with name: " + name + ". This obj is not in the list");
                return null;
            }
            else {
                Logger.warn("Tried to get obj with name: " + name + ". This obj is in the list MULTIPLE times. Should not happen!!");
            }
        }
        return list.get(0);
    }
    protected <T extends Component> GameObject getObjectsByTag(String tag, List<GameObject> allObjects){
        List<GameObject> list = allObjects.stream()//durchsucht alle  gameObjects nach dem Spieler
                .filter(obj -> obj.isActive() && tag.equals(obj.getName()))
                .collect(Collectors.toList());
        if(list.isEmpty()){
            Logger.warn("Tried to get obj with tag: " + tag + ". This obj is not in the list");
            return null;
        }

        return list.get(0);
    }
    protected GameObject findPlayer(List<GameObject> objects) {
        return objects.stream()
                .filter(obj -> "Player".equals(obj.getName()))
                .findFirst()
                .orElse(null);
    }

    protected <T extends Component> boolean hasObjectsWithComponent(Class<T> componentType, List<GameObject> allObjects) {
        return allObjects.stream().anyMatch(obj -> obj.isActive() && obj.hasComponent(componentType));
    }
    protected <T extends Component> boolean hasObjectsByName(String name, List<GameObject> allObjects){
        return allObjects.stream()//durchsucht alle  gameObjects nach dem Spieler
                .anyMatch(obj -> obj.isActive() && name.equals(obj.getName()));
    }
    protected <T extends Component> boolean hasObjectsByTag(String tag, List<GameObject> allObjects){
        return allObjects.stream()//durchsucht alle  gameObjects nach dem Spieler
                .anyMatch(obj -> obj.isActive() && tag.equals(obj.getName()));
    }
}
