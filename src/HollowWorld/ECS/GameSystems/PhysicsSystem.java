package HollowWorld.ECS.GameSystems;

import Engine.Core.GameContainer;
import Engine.Logger;
import HollowWorld.ECS.Components.Core.Collider;
import HollowWorld.ECS.Components.Core.RigidBody;
import HollowWorld.ECS.Components.Core.Transform;
import HollowWorld.ECS.Components.Terraria.ItemComponent;
import HollowWorld.ECS.Components.Terraria.ItemType;
import HollowWorld.ECS.GameObjects.GameObject;
import HollowWorld.GameCode.WorldGeneration.WorldMap;

import java.util.List;

public class PhysicsSystem extends GameSystem{
    @Override
    public void initialize() {

    }

    @Override
    public void update(GameContainer gc, float dt, List<GameObject> gameObjects, WorldMap map) {
        List<GameObject> rigidBodies = getObjectsWithComponent(RigidBody.class, gameObjects);
        updatePhysics(gc,dt,rigidBodies,map);
        resolveGameObjectCollisions(rigidBodies);
    }

    // physics and world Collision
    private void updatePhysics(GameContainer gc, float dt, List<GameObject> rigidBodies, WorldMap map){
        for(GameObject obj: rigidBodies){
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

            // später vllt noch grounded checken
            rb.velocity.y += 800.0f * rb.gravityScale * dt;


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

    // obj,obj collision
    private void resolveGameObjectCollisions(List<GameObject> rigidBodies){
        // kollisionspaare finden
        for (int i = 0; i < rigidBodies.size(); i++){
            for(int j = i + 1; j < rigidBodies.size(); j++){
                GameObject A = rigidBodies.get(i);
                GameObject B = rigidBodies.get(j);
                if(doesCollide(A,B)){
                    handleCollision(A,B);
                    // wenn nötig das resolveCollision erst in handleCollision aufrufen, da man nicht immer die Objekte
                    // zurück Bewegen will. ZB wenn man nur eine TriggerBox hat
                    resolveCollision(A,B);
                }
            }
        }
    }

    private void handleCollision(GameObject a, GameObject b) {

        // items aufsammeln
        if(b.hasComponent(ItemComponent.class) && (a.getTag().equals("Player"))){
            System.out.println("+1 " + b.getName());
            b.setActive(false); // Item in Welt löschen
        }
    }

    private boolean doesCollide(GameObject A, GameObject B){
        Transform tfA = A.getTransform();
        Transform tfB = B.getTransform();
        Collider colA = A.getComponent(Collider.class);
        Collider colB = B.getComponent(Collider.class);

        if(tfA == null){Logger.error("Object: " + A.getName() + " has no Transform!!"); return false;}
        if(tfB == null){Logger.error("Object: " + B.getName() + " has no Transform!!"); return false;}
        if(colA == null){Logger.error("Object: " + A.getName() + " has no Collider!!"); return false;}
        if(colB == null){Logger.error("Object: " + B.getName() + " has no Collider!!"); return false;}

        // bisher nur für BOX type collider
        if(colA.type != Collider.ColliderType.BOX || colB.type != Collider.ColliderType.BOX){
            Logger.error("Collision check only implemented for BOX colliders!");return false;}

        int ax = tfA.x;
        int ay = tfA.y;
        int aw = colA.width;
        int ah = colA.height;

        int bx = tfB.x;
        int by = tfB.y;
        int bw = colB.width;
        int bh = colB.height;

        // aabb collision
        boolean overlapX = ax < bx + bw && ax + aw > bx;
        boolean overlapY = ay < by + bh && ay + ah > by;

        return overlapX && overlapY;
    }
    private void resolveCollision(GameObject A, GameObject B){
        // bitte funktionier endlich richtig

        RigidBody rbA = A.getComponent(RigidBody.class);
        RigidBody rbB = B.getComponent(RigidBody.class);
        Transform tfA = A.getTransform();
        Transform tfB = B.getTransform();
        Collider colA = A.getComponent(Collider.class);
        Collider colB = B.getComponent(Collider.class);

        int leftA   = tfA.x;
        int rightA  = tfA.x + colA.width;
        int topA    = tfA.y;
        int bottomA = tfA.y + colA.height;

        int leftB   = tfB.x;
        int rightB  = tfB.x + colB.width;
        int topB    = tfB.y;
        int bottomB = tfB.y + colB.height;

        int overlapX = Math.min(rightA - leftB, rightB - leftA);
        int overlapY = Math.min(bottomA - topB, bottomB - topA);

        // falls keine wird abgebrochen. sollte eig nie passieren
        if (overlapX <= 0 || overlapY <= 0) return;

        if (overlapX < overlapY) {  // horizontale collision
            boolean AisMoving = (Math.abs(rbA.velocity.x) > 0.1f);
            boolean BisMoving = (Math.abs(rbB.velocity.x) > 0.1f);

            if (AisMoving && !BisMoving) {
                // nur a bewegt sich
                if (leftA < leftB) {
                    tfA.x = leftB - colA.width;
                } else {
                    tfA.x = leftB + colB.width;
                }
            }
            else if (!AisMoving && BisMoving) {
                // nur b bewegt sich
                if (leftB < leftA) {
                    tfB.x = leftA - colB.width;
                } else {
                    tfB.x = leftA + colA.width;
                }
            }
            else if (AisMoving && BisMoving) {
                // beide bewegen sich: leicht auseinander schieben
                int push = (int)(overlapX / 2f);
                if (leftA < leftB) {
                    tfA.x -= push;
                    tfB.x += push;
                } else {
                    tfA.x += push;
                    tfB.x -= push;
                }
            }
            if (rbA.isKinematic) rbA.velocity.x = 0;
            if (rbB.isKinematic) rbB.velocity.x = 0;

        }
        else {          // vertikale Kollision
            boolean AisMovingDown = rbA.velocity.y > 0.1f;
            boolean AisMovingUp = rbA.velocity.y < -0.1f;
            boolean BisMovingDown = rbB.velocity.y > 0.1f;
            boolean BisMovingUp = rbB.velocity.y < -0.1f;

            if (AisMovingDown && !BisMovingDown) {
                // A fällt auf B
                tfA.y = topB - colA.height;
                rbA.velocity.y = 0;
                rbA.isGrounded = true;
            }
            else if (AisMovingUp && !BisMovingUp) {
                // A stößt von unten gegen B
                tfA.y = bottomB;
                rbA.velocity.y = 0;
            }
            else if (BisMovingDown && !AisMovingDown) {
                // B fällt auf A
                tfB.y = topA - colB.height;
                rbB.velocity.y = 0;
                rbB.isGrounded = true;
            }
            else if (BisMovingUp && !AisMovingUp) {
                // B stößt von unten gegen A
                tfB.y = bottomA;
                rbB.velocity.y = 0;
            }
        }
    }
}


