package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import Engine.Logger;
import Engine.Math.Vector;
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
            float correctedX = resolveHorizontal(rb,col,newX,tf.y,map);
            tf.x = (int) correctedX;

            float newY = tf.y + (rb.velocity.y * dt);
            float correctedY = resolveVertical(rb,col,tf.x,newY,map);
            tf.y = (int) correctedY;

        }
    }



    private float resolveHorizontal(RigidBody rb, Collider col, float newX, float currentY, WorldMap map){
        int tileSize = 32;

        float width = col.width;
        float height = col.height;

        int top = (int)(currentY / tileSize);
        int bottom = (int)((currentY + height - 1) / tileSize);

        if (rb.velocity.x > 0) {
            int gridX = (int)((newX + width) / tileSize);
            if (isAreaSolid(gridX, top, gridX, bottom, map)) {
                newX = gridX * tileSize - width;
                rb.velocity.x = 0;
            }
        } else if (rb.velocity.x < 0) {
            int gridX = (int)(newX / tileSize);
            if (isAreaSolid(gridX, top, gridX, bottom, map)) {
                newX = (gridX + 1) * tileSize;
                rb.velocity.x = 0;
            }
        }
        return newX;
    }
    private float resolveVertical(RigidBody rb, Collider col, float currentX, float newY, WorldMap map){
        int tileSize = 32;

        float width = col.width;
        float height = col.height;

        int left = (int)(currentX / tileSize);
        int right = (int)((currentX + width - 1) / tileSize);

        if (rb.velocity.y > 0) { // down
            int gridY = (int)((newY + height) / tileSize);
            if (isAreaSolid(left, gridY, right, gridY, map)) {
                newY = gridY * tileSize - height;
                rb.velocity.y = 0;
                rb.isGrounded = true;
            }
        } else if (rb.velocity.y < 0) { // up
            int gridY = (int)(newY / tileSize);
            if (isAreaSolid(left, gridY, right, gridY, map)) {
                newY = (gridY + 1) * tileSize;
                rb.velocity.y = 0;
            }
        }
        return newY;
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

    //    private Vector resolveMapCollisions(RigidBody rb, Collider collider,
//                                        float newX, float newY, WorldMap map) {
//        // TODO collisions richtig für kreis collider berechnen
//        Vector correctedPosition = new Vector(newX,newY);
//
//        int tileSize = 32;
//
//        // collider sizes setzn
//        float width = (collider.type == Collider.ColliderType.BOX) ? collider.width : collider.radius * 2;
//        float height = (collider.type == Collider.ColliderType.BOX) ? collider.height : collider.radius * 2;
//
//        // horizontale Collisions
//        if(rb.velocity.x != 0) {
//            int gridYBottom = (int)((newY + height - 1) / tileSize);
//            int gridYTop = (int)(newY / tileSize);
//
//            if(rb.velocity.x > 0) { // Rechts
//                int gridX = (int)((newX + width) / tileSize);
//                if(isAreaSolid(gridX, gridYTop, gridX, gridYBottom, map)) {
//                    correctedPosition.x = gridX * tileSize - width;
//                    rb.velocity.x = 0;
//                }
//            } else { // Links
//                int gridX = (int)(newX / tileSize);
//                if(isAreaSolid(gridX, gridYTop, gridX, gridYBottom, map)) {
//                    correctedPosition.x = (gridX + 1) * tileSize;
//                    rb.velocity.x = 0;
//                }
//            }
//        }
//
//        // vertikale collisions
//        if(rb.velocity.y != 0) {
//            int gridXLeft = (int)(newX / tileSize);
//            int gridXRight = (int)((newX + width - 1) / tileSize);
//
//            if(rb.velocity.y > 0) { // Unten
//                int gridY = (int)((newY + height) / tileSize);
//                if(isAreaSolid(gridXLeft, gridY, gridXRight, gridY, map)) {
//                    correctedPosition.y = gridY * tileSize - height;
//                    rb.velocity.y = 0;
//                    rb.isGrounded = true;
//                }
//            } else { // Oben
//                int gridY = (int)(newY / tileSize);
//                if(isAreaSolid(gridXLeft, gridY, gridXRight, gridY, map)) {
//                    correctedPosition.y = (gridY + 1) * tileSize;
//                    rb.velocity.y = 0;
//                }
//            }
//        }
//        return correctedPosition;
//    }
}
