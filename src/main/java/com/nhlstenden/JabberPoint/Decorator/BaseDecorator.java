package com.nhlstenden.JabberPoint.Decorator;

import com.nhlstenden.JabberPoint.Slide.Slide;

public abstract class BaseDecorator extends Slide
{
    protected Slide wrappe;

    public BaseDecorator(Slide wrappe) {
        super("text");
        this.wrappe = wrappe;
    }
}
