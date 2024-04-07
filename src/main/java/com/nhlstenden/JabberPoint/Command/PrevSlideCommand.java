package com.nhlstenden.JabberPoint.Command;

import com.nhlstenden.JabberPoint.Presentation.Presentation;

public class PrevSlideCommand extends Command {
    public PrevSlideCommand(Presentation presentation) {
        super(presentation);
    }

    @Override
    public void execute() {
        presentation.prevSlide();
    }
}