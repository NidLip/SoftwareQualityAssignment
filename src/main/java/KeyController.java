import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.HashMap;
import java.util.Map;

/** <p>This is the main.KeyController (KeyListener)</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class KeyController extends KeyAdapter {
    private Map<Integer, Command> keyCommands;

    public KeyController(Presentation p) {
        keyCommands = new HashMap<>();
        keyCommands.put(KeyEvent.VK_PAGE_DOWN, new NextSlideCommand(p));
        keyCommands.put(KeyEvent.VK_DOWN, new NextSlideCommand(p));
        keyCommands.put(KeyEvent.VK_ENTER, new NextSlideCommand(p));
        keyCommands.put((int) '+', new NextSlideCommand(p));
        keyCommands.put(KeyEvent.VK_PAGE_UP, new PrevSlideCommand(p));
        keyCommands.put(KeyEvent.VK_UP, new PrevSlideCommand(p));
        keyCommands.put((int) '-', new PrevSlideCommand(p));
    }

    public void keyPressed(KeyEvent keyEvent) {
        Command command = keyCommands.get(keyEvent.getKeyCode());
        if (command != null) {
            command.execute();
        } else if (keyEvent.getKeyChar() == 'q' || keyEvent.getKeyChar() == 'Q') {
            System.exit(0);
        }
    }
}