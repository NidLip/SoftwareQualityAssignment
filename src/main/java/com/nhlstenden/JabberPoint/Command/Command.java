package com.nhlstenden.JabberPoint.Command;

import com.nhlstenden.JabberPoint.Presentation.Presentation;

public abstract class Command {
    protected Presentation presentation;

    public Command(Presentation presentation) {
        this.presentation = presentation;
    }

    public abstract void execute();
}