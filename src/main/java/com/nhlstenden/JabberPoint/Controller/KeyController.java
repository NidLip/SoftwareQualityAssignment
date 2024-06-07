package com.nhlstenden.JabberPoint.Controller;

import com.nhlstenden.JabberPoint.Command.Command;
import com.nhlstenden.JabberPoint.Command.NextSlideCommand;
import com.nhlstenden.JabberPoint.Command.PrevSlideCommand;
import com.nhlstenden.JabberPoint.Command.QuitCommand;
import com.nhlstenden.JabberPoint.Presentation.Presentation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.HashMap;
import java.util.Map;

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
        keyCommands.put((int) 'q', new QuitCommand(presentation));
        keyCommands.put((int) 'Q', new QuitCommand(presentation));
    }

    public void keyPressed(KeyEvent keyEvent) {
        Command command = keyCommands.get(keyEvent.getKeyCode());
        if (command != null) {
            command.execute();
        } else if (keyEvent.getKeyChar() == 'q' || keyEvent.getKeyChar() == 'Q') {
            new QuitCommand(null).execute();
        }
    }
}
