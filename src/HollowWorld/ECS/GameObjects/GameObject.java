package HollowWorld.ECS.GameObjects;

import Engine.Renderer;
import HollowWorld.ECS.Components.Component;
import HollowWorld.ECS.Components.Core.Transform;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameObject {
    private String name;
    private String tag = "untagged";
    private boolean active = true;
    private Map<Class<? extends Component>, Component> components = new HashMap<>();

    public GameObject(String name){
        this.name = name;
    }

    // updated alle components
    public void update(float deltaTime) {
        if (!active) return; // wenn nicht active macht nix

        for (Component component : components.values()) {
            component.update(deltaTime);
        }
    }
    public void render(Renderer renderer) {
        if (!active) return;
        for (Component component : components.values()) {
            component.render(renderer);
        }
    }



    // Component Handling
    public <T extends Component> T addComponent(T component) {
        components.put(component.getClass(), component);
        component.setGameObject(this);
        component.start();
        return component;
    }

    public <T extends Component> T getComponent(Class<T> type) {
        Component component = components.get(type);
        if (type.isInstance(component)) {
            return type.cast(component);
        }
        for (Component comp : components.values()) {
            if (type.isInstance(comp)) {
                return type.cast(comp);
            }
        }
        //@TODO logging
        return null;
    }

    public boolean hasComponent(Class<? extends Component> type) {
        return getComponent(type) != null;
    }

    public void removeComponent(Class<? extends Component> type) {
        Component component = components.get(type);
        if (component != null) {
            component.cleanup();
            components.remove(type);
        }
    }

    public Transform getTransform(){
        return (Transform) components.get(Transform.class);
    }

    // Einfache Getter/Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Collection<Component> getComponents() { return components.values(); }
}
