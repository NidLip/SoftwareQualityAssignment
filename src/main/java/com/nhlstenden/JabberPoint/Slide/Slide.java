package com.nhlstenden.JabberPoint.Slide;

import com.nhlstenden.JabberPoint.Factory.BitmapItemFactory;
import com.nhlstenden.JabberPoint.Factory.SlideItemFactory;
import com.nhlstenden.JabberPoint.Factory.TextItemFactory;
import com.nhlstenden.Style.Style;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

/** <p>A slide. This class has a drawing functionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Slide {
	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;
	protected String title; // title is saved separately
	protected Vector<SlideItem> items; // slide items are saved in a Vector
	protected SlideItemFactory factory;
	
	public Slide(String factoryType) {
		items = new Vector<>();
		if(factoryType.equals("bitmap")){
			this.factory = new BitmapItemFactory();
		} else if (factoryType.equals("text")) {
			this.factory = new TextItemFactory();
		} else {
			throw new IllegalArgumentException("Invalid factory type: " + factoryType);
		}
	}

	// Add a slide item
	public void append(SlideItem anItem) {
		items.addElement(anItem);
	}

	// give the title of the slide
	public String getTitle() {
		return title;
	}

	// change the title of the slide
	public void setTitle(String newTitle) {
		title = newTitle;
	}

	// Create main.com.nhlstenden.JabberPoint.Slide.TextItem of String, and add the main.com.nhlstenden.JabberPoint.Slide.TextItem
	public void append(int level, String message) {
		SlideItem item = factory.createSlideItem(level, message);
		items.addElement(item);
	}

	// give the  main.com.nhlstenden.JabberPoint.Slide.SlideItem
	public SlideItem getSlideItem(int number) {
		return (SlideItem)items.elementAt(number);
	}

	// give all SlideItems in a Vector
	public Vector<SlideItem> getAllSlideItems() {
		return items;
	}

	// give the size of the main.com.nhlstenden.JabberPoint.Slide.Slide
	public int getSize() {
		return items.size();
	}

	// draw the slide
	public void draw(Graphics graphics, Rectangle area, ImageObserver view) {
        float scale = getScale(area);
        int y = area.y;
        drawTitle(graphics, area, view, scale, y);
        drawSlideItems(graphics, area, view, scale, y);
    }

	private void drawTitle(Graphics graphics, Rectangle area, ImageObserver view, float scale, int y) {
        SlideItem slideItem = new TextItem(0, getTitle());
        Style style = Style.getStyle(slideItem.getLevel());
        slideItem.draw(area.x, y, scale, graphics, style, view);
        y += slideItem.getBoundingBox(graphics, view, scale, style).height;
    }

    private void drawSlideItems(Graphics graphics, Rectangle area, ImageObserver view, float scale, int y) {
        for (int number=0; number<getSize(); number++) {
            SlideItem slideItem = (SlideItem) getAllSlideItems().elementAt(number);
            Style style = Style.getStyle(slideItem.getLevel());
            slideItem.draw(area.x, y, scale, graphics, style, view);
            y += slideItem.getBoundingBox(graphics, view, scale, style).height;
        }
    }

	// Give the scale for drawing
	private float getScale(Rectangle area) {
		return Math.min(((float)area.width) / ((float)WIDTH), ((float)area.height) / ((float)HEIGHT));
	}
}
