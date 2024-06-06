package com.nhlstenden.JabberPoint.Slide;

import com.nhlstenden.JabberPoint.Factory.BitmapItemFactory;
import com.nhlstenden.JabberPoint.Factory.SlideItemFactory;
import com.nhlstenden.JabberPoint.Factory.TextItemFactory;
import com.nhlstenden.Style.Style;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

public class Slide {
	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;
	protected String title;
	protected Vector<SlideItem> items;
	protected SlideItemFactory factory;

	public Slide(String factoryType) {
		this.items = new Vector<>();
		if(factoryType.equals("bitmap")){
			this.factory = new BitmapItemFactory();
		} else if (factoryType.equals("text")) {
			this.factory = new TextItemFactory();
		} else {
			throw new IllegalArgumentException("Invalid factory type: " + factoryType);
		}
	}

	public void append(SlideItem anItem) {
		items.addElement(anItem);
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String newTitle) {
		this.title = newTitle;
	}

	public void append(int level, String message) {
		SlideItem item = factory.createSlideItem("Text", level, message);
		items.addElement(item);
	}

	public SlideItem getSlideItem(int number) {
		if (number < 0 || number >= getSize()) {
			return null;
		}
		return items.elementAt(number);
	}

	public Vector<SlideItem> getAllSlideItems() {
		return this.items;
	}

	public int getSize() {
		return items.size();
	}

	public void draw(Graphics graphics, Rectangle area, ImageObserver view) {
		float scale = getScale(area);
		int y = area.y;
		y += drawTitle(graphics, area, view, scale, y);
		drawSlideItems(graphics, area, view, scale, y);
	}

	private int drawTitle(Graphics graphics, Rectangle area, ImageObserver view, float scale, int y) {
		SlideItem slideItem = new TextItem(0, getTitle());
		Style style = Style.getStyle(slideItem.getLevel());
		slideItem.draw(area.x, y, scale, graphics, style, view);
		y += slideItem.getBoundingBox(graphics, view, scale, style).height;
		return y;
	}

	private void drawSlideItems(Graphics graphics, Rectangle area, ImageObserver view, float scale, int y) {
		for (int number=0; number<getSize(); number++) {
			SlideItem slideItem = getAllSlideItems().elementAt(number);
			Style style = Style.getStyle(slideItem.getLevel());
			slideItem.draw(area.x, y, scale, graphics, style, view);
			y += slideItem.getBoundingBox(graphics, view, scale, style).height;
		}
	}

	private float getScale(Rectangle area) {
		return Math.min(((float)area.width) / ((float)WIDTH), ((float)area.height) / ((float)HEIGHT));
	}
}
