package com.nhlstenden.JabberPoint.Command;

import com.nhlstenden.JabberPoint.Accessor.Accessor;
import com.nhlstenden.JabberPoint.Presentation.Presentation;
import com.nhlstenden.JabberPoint.Accessor.XMLAccessor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class OpenCommand extends Command {
    private Frame parent;

    public OpenCommand(Presentation presentation, Frame parent) {
        super(presentation);
        this.parent = parent;
    }

    @Override
    public void execute() {
        presentation.clear();
        Accessor xmlAccessor = new XMLAccessor();
        try {
            xmlAccessor.loadFile(presentation, "test.xml");
            presentation.setSlideNumber(0);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(parent, "IO Exception: " + exc,
                    "Load Error", JOptionPane.ERROR_MESSAGE);
        }
        parent.repaint();
    }
}