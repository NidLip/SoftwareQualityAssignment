package com.nhlstenden.JabberPoint.Decorator;

import com.nhlstenden.JabberPoint.Slide.Slide;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class AnimationDecorator extends BaseDecorator {
    private Image gifImage;

    public AnimationDecorator(Slide wrappe) {
        super(wrappe);
    }

    public void loadGif(String gifFilePath) throws IOException {
        gifImage = ImageIO.read(new File(gifFilePath));
    }

    @Override
    public void draw(Graphics g, Rectangle area, ImageObserver observer) {
        super.draw(g, area, observer);
        if (gifImage != null) {
            g.drawImage(gifImage, area.x, area.y, observer);
        }
    }
}