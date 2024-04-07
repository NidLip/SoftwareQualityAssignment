package com.nhlstenden.JabberPoint.Command;

import com.nhlstenden.JabberPoint.Presentation.Presentation;

public class NextSlideCommand extends Command {
    public NextSlideCommand(Presentation presentation) {
        super(presentation);
    }

    @Override
    public void execute() {
        presentation.nextSlide();
    }
}