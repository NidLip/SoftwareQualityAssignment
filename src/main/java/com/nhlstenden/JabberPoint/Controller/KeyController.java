package com.nhlstenden.JabberPoint.Controller;

import com.nhlstenden.JabberPoint.Command.Command;
import com.nhlstenden.JabberPoint.Command.NextSlideCommand;
import com.nhlstenden.JabberPoint.Presentation.Presentation;
import com.nhlstenden.JabberPoint.Command.PrevSlideCommand;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.HashMap;
import java.util.Map;

/** <p>This is the main.com.nhlstenden.JabberPoint.Controller.KeyController (KeyListener)</p>
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

    public KeyController(Presentation presentation) {
        keyCommands = new HashMap<>();
        keyCommands.put(KeyEvent.VK_PAGE_DOWN, new NextSlideCommand(presentation));
        keyCommands.put(KeyEvent.VK_DOWN, new NextSlideCommand(presentation));
        keyCommands.put(KeyEvent.VK_ENTER, new NextSlideCommand(presentation));
        keyCommands.put((int) '+', new NextSlideCommand(presentation));
        keyCommands.put(KeyEvent.VK_PAGE_UP, new PrevSlideCommand(presentation));
        keyCommands.put(KeyEvent.VK_UP, new PrevSlideCommand(presentation));
        keyCommands.put((int) '-', new PrevSlideCommand(presentation));
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