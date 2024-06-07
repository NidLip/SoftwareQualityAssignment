package com.nhlstenden.JabberPoint.Command;

import com.nhlstenden.JabberPoint.Presentation.Presentation;

public class QuitCommand extends Command {
    public QuitCommand(Presentation presentation) {
        super(presentation);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
