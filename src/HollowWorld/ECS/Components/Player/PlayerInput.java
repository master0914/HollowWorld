package HollowWorld.ECS.Components.Player;

import Engine.Core.GameContainer;
import Engine.Logger;
import HollowWorld.ECS.Components.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.event.KeyEvent;

public class PlayerInput extends Component {
    private boolean left, right, jump;

    @Override
    public void update(GameContainer gc, float dt) {

        left = gc.getInput().isKey(KeyEvent.VK_A);
        right = gc.getInput().isKey(KeyEvent.VK_D);
        jump = gc.getInput().isKey(KeyEvent.VK_SPACE);
        //Logger.info("PlayerInputs: l:" + left + "; r:" + right + "; jump:" + jump);

    }

    public float getHorizontal() {
        return (left ? -1 : 0) + (right ? 1 : 0);
    }

    public boolean isJumpPressed() {
        return jump;
    }
}





