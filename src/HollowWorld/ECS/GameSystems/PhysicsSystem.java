package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import Engine.Logger;
import HollowWorld.ECS.Components.Core.Collider;
import HollowWorld.ECS.Components.Core.RigidBody;
import HollowWorld.ECS.Components.Core.Transform;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.WorldMap;

import java.util.List;

public class PhysicsSystem extends GameSystem{
    @Override
    public void initialize() {

    }

    @Override
    public void update(GameContainer gc, float dt, List<GameObject> gameObjects, WorldMap map) {

        for(GameObject obj: getObjectsWithComponent(RigidBody.class, gameObjects)){
            RigidBody rb = obj.getComponent(RigidBody.class);
            Transform tf = obj.getTransform();
            Collider col = obj.getComponent(Collider.class);

            if(rb == null || tf == null || col == null) {
                Logger.warn("Could not calculate Physics for obj: " + obj.getName() + ". Missing Component");
                continue;
            }
            if(!rb.isKinematic){
                Logger.info("No MovementCacluation for notKinematic obj: " + obj.getName());
                continue;
            }
            rb.isGrounded = false;
            // Gravitation hinzufügen wenn nicht am boden
            if(!rb.isGrounded){
                rb.velocity.y += 800.0f * rb.gravityScale * dt;
            }

//                Logger.info(rb.velocity.x  + "  "+rb.velocity.y);

            // Bewegung
            float newX = tf.x + (rb.velocity.x * dt);
            float newY = tf.y + (rb.velocity.y * dt);

            resolveMapCollisions(tf, rb, col, newX, newY, map);

//                Logger.info(tf.x  + "  "+tf.y);

            tf.x = (int) newX;
            tf.y = (int) newY;

        }
    }

    private void resolveMapCollisions(Transform tf, RigidBody rb, Collider collider,
                                      float newX, float newY, WorldMap map) {
        // TODO collisions richtig für kreis collider berechnen
        int tileSize = 32;

        // collider sizes setzn
        float width = (collider.type == Collider.ColliderType.BOX) ? collider.width : collider.radius * 2;
        float height = (collider.type == Collider.ColliderType.BOX) ? collider.height : collider.radius * 2;

        // horizontale Collisions
        if(rb.velocity.x != 0) {
            int gridYBottom = (int)((newY + height - 1) / tileSize);
            int gridYTop = (int)(newY / tileSize);

            if(rb.velocity.x > 0) { // Rechts
                int gridX = (int)((newX + width) / tileSize);
                if(isAreaSolid(gridX, gridYTop, gridX, gridYBottom, map)) {
                    newX = gridX * tileSize - width;
                    rb.velocity.x = 0;
                }
            } else { // Links
                int gridX = (int)(newX / tileSize);
                if(isAreaSolid(gridX, gridYTop, gridX, gridYBottom, map)) {
                    newX = (gridX + 1) * tileSize;
                    rb.velocity.x = 0;
                }
            }
        }

        // vertikale collisions
        if(rb.velocity.y != 0) {
            int gridXLeft = (int)(newX / tileSize);
            int gridXRight = (int)((newX + width - 1) / tileSize);

            if(rb.velocity.y > 0) { // Unten
                int gridY = (int)((newY + height) / tileSize);
                if(isAreaSolid(gridXLeft, gridY, gridXRight, gridY, map)) {
                    newY = gridY * tileSize - height;
                    rb.velocity.y = 0;
                    rb.isGrounded = true;
                }
            } else { // Oben
                int gridY = (int)(newY / tileSize);
                if(isAreaSolid(gridXLeft, gridY, gridXRight, gridY, map)) {
                    newY = (gridY + 1) * tileSize;
                    rb.velocity.y = 0;
                }
            }
        }
    }
    private boolean isAreaSolid(int startX, int startY, int endX, int endY, WorldMap map) {
        for(int gridX = startX; gridX <= endX; gridX++) {
            for(int gridY = startY; gridY <= endY; gridY++) {
                if(gridX < 0 || gridX >= map.getWidth() ||
                        gridY < 0 || gridY >= map.getHeight()) {
                    return false;
                }
                if(map.isSolid(gridX, gridY)) return true;
            }
        }
        return false;
    }

}
