package HollowWorld.ECS.Components.Player;

import Engine.Core.GameContainer;
import Engine.Logger;
import HollowWorld.ECS.Components.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.event.KeyEvent;

public class PlayerInput extends Component {
    private boolean left, right, jump;
    private boolean mouseLeft;  // das wird immer true sein solange der knopf runtergedrückt wird.
    private boolean mouseLeftJustPressed; // das wird nur true sein wenn der knopf eben runtergedrückt wird.
    // also nur einmal pro tasten druck true
    private int mouseX, mouseY;
    private boolean inventory;
    private boolean[] numberPressed;
    private boolean dropItem;

    @Override
    public void update(GameContainer gc, float dt) {

        left = gc.getInput().isKey(KeyEvent.VK_A);
        right = gc.getInput().isKey(KeyEvent.VK_D);
        jump = gc.getInput().isKey(KeyEvent.VK_SPACE);
        mouseLeft = gc.getInput().isButton(MouseEvent.BUTTON1);
        mouseLeftJustPressed = gc.getInput().isButtonDown(MouseEvent.BUTTON1);
        mouseX = gc.getInput().getMouseX();
        mouseY = gc.getInput().getMouseY();
        //Logger.info("PlayerInputs: l:" + left + "; r:" + right + "; jump:" + jump);
        //Logger.info("PlayerMouseInputs: x: " + mouseX + ",  y: " + mouseY + ", mouseLeftDown: "
        //+ mouseLeft + ",  mouseLeftJustDown: " + mouseLeftJustPressed);
        inventory = gc.getInput().isKey(KeyEvent.VK_E);
        dropItem = gc.getInput().isKey(KeyEvent.VK_Q);

        numberPressed = new boolean[10];
        numberPressed[0] = gc.getInput().isKey(KeyEvent.VK_0);
        numberPressed[2] = gc.getInput().isKey(KeyEvent.VK_2);
        numberPressed[3] = gc.getInput().isKey(KeyEvent.VK_3);
        numberPressed[1] = gc.getInput().isKey(KeyEvent.VK_1);
        numberPressed[4] = gc.getInput().isKey(KeyEvent.VK_4);
        numberPressed[5] = gc.getInput().isKey(KeyEvent.VK_5);
        numberPressed[6] = gc.getInput().isKey(KeyEvent.VK_6);
        numberPressed[7] = gc.getInput().isKey(KeyEvent.VK_7);
        numberPressed[8] = gc.getInput().isKey(KeyEvent.VK_8);
        numberPressed[9] = gc.getInput().isKey(KeyEvent.VK_9);


    }

    public float getHorizontal() {
        return (left ? -1 : 0) + (right ? 1 : 0);
    }

    public boolean isJumpPressed() {
        return jump;
    }

    public boolean isMouseLeft(){return mouseLeft;}

    public boolean isMouseLeftJustPressed(){return mouseLeftJustPressed;}

    public int getMouseX(){return mouseX;}

    public int getMouseY(){return mouseY;}

    public boolean isInventory(){return inventory;}

    public boolean[] numberPressed(){return numberPressed;}

    public boolean getDropItem(){return dropItem;}
}





