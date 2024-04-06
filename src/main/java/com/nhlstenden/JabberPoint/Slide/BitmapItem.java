package com.nhlstenden.JabberPoint.Slide;

import com.nhlstenden.Style.Style;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;

import java.io.IOException;


/** <p>De klasse voor een Bitmap item</p>
 * <p>Bitmap items have the responsibility to draw themselves.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class BitmapItem extends SlideItem {
  private BufferedImage bufferedImage;
  private String imageName;
  
  protected static final String FILE = "File ";
  protected static final String NOTFOUND = " not found";

// level is equal to item-level; name is the name of the file with the Image
	public BitmapItem(int level, String name) {
		super(level);
		imageName = name;
		try {
			bufferedImage = ImageIO.read(new File(imageName));
		}
		catch (IOException exception) {
			System.err.println(FILE + imageName + NOTFOUND) ;
		}
	}

// An empty bitmap-item
	public BitmapItem() {
		this(0, null);
	}

// getters and setters
	public String getName() {
		return imageName;
	}

	public void setName(String name){
		this.imageName = name;
	}

	public BufferedImage getBufferedImage()
	{
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage)
	{
		this.bufferedImage = bufferedImage;
	}

	// give the  bounding box of the image
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
		return new Rectangle((int) (myStyle.getIndent() * scale), 0,
				(int) (getBufferedImage().getWidth(observer) * scale),
				((int) (myStyle.getLeading() * scale)) +
				(int) (getBufferedImage().getHeight(observer) * scale));
	}

// draw the image
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
		int width = x + (int) (myStyle.getIndent() * scale);
		int height = y + (int) (myStyle.getLeading() * scale);
		g.drawImage(getBufferedImage(), width, height,(int) (getBufferedImage().getWidth(observer)*scale),
                (int) (getBufferedImage().getHeight(observer)*scale), observer);
	}

	public String toString() {
		return "main.com.nhlstenden.JabberPoint.Slide.BitmapItem[" + getLevel() + "," + imageName + "]";
	}
}