package com.nhlstenden.JabberPoint.Decorator;

import com.nhlstenden.JabberPoint.Slide.Slide;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AnimationDecorator extends BaseDecorator {
    private Image gifImage;

    public AnimationDecorator(Slide wrappe) {
        super(wrappe);
    }

    public Image getGifImage()
    {
        return gifImage;
    }

    public void setGifImage(Image gifImage)
    {
        this.gifImage = gifImage;
    }

    public void loadGif(String gifFileName) throws IOException {
        InputStream in = getClass().getResourceAsStream("/main.com.nhlstenden.JabberPoint.Resources/" + gifFileName);
        if (in == null) {
            throw new FileNotFoundException("GIF file not found: " + gifFileName);
        }
        gifImage = ImageIO.read(in);
    }

    @Override
    public void draw(Graphics graphics, Rectangle area, ImageObserver observer) {
        super.draw(graphics, area, observer);
        if (getGifImage() != null) {
            graphics.drawImage(getGifImage(), area.x, area.y, observer);
        }
    }
}