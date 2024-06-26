package com.nhlstenden.JabberPoint.Slide;

import com.nhlstenden.Style.Style;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/** <p>A tekst item.</p>
 * <p>A main.com.nhlstenden.JabberPoint.Slide.TextItem has drawingfunctionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem {
	private String text;

// a textitem of level, with the text string
	public TextItem(int level, String string) {
		super(level);
		if (string == null || string.isEmpty()) {
			this.text = "No Text Given";
		} else {
			this.text = string;
		}
	}


// give the text
	public String getText() {
		return this.text == null ? "" : this.text;
	}

	public void setText(String textItem){
		this.text = textItem;
	}

// geef de AttributedString voor het item
public AttributedString getAttributedString(Style style, float scale) {
	String textContent = getText(); // Get the text content, ensuring it's not null
	int textLength = textContent.length(); // Calculate the length of the text content
	AttributedString attrStr = new AttributedString(textContent); // Create AttributedString with text content

	// Add font attribute only if text content is not null and has a positive length
	if (textLength > 0) {
		Font font = style.getFont(scale);
		attrStr.addAttribute(TextAttribute.FONT, font, 0, textLength);
	}

	return attrStr;
}

// give the bounding box of the item
	public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale, Style myStyle)
	{
		List<TextLayout> layouts = getLayouts(graphics, myStyle, scale);
		int xsize = 0, ysize = (int) (myStyle.getLeading() * scale);
		Iterator<TextLayout> iterator = layouts.iterator();
		while (iterator.hasNext()) {
			TextLayout layout = iterator.next();
			Rectangle2D bounds = layout.getBounds();
			if (bounds.getWidth() > xsize) {
				xsize = (int) bounds.getWidth();
			}
			if (bounds.getHeight() > 0) {
				ysize += bounds.getHeight();
			}
			ysize += layout.getLeading() + layout.getDescent();
		}
		return new Rectangle((int) (myStyle.getIndent()*scale), 0, xsize, ysize );
	}

// draw the item
	public void draw(int x, int y, float scale, Graphics graphics,
			Style myStyle, ImageObserver observer) {
		if (text == null || text.length() == 0) {
			return;
		}
		List<TextLayout> layouts = getLayouts(graphics, myStyle, scale);
		Point pen = new Point(x + (int)(myStyle.getIndent() * scale),
				y + (int) (myStyle.getLeading() * scale));
		Graphics2D g2d = (Graphics2D)graphics;
		g2d.setColor(myStyle.getColor());
		Iterator<TextLayout> it = layouts.iterator();
		while (it.hasNext()) {
			TextLayout layout = it.next();
			pen.y += layout.getAscent();
			layout.draw(g2d, pen.x, pen.y);
			pen.y += layout.getDescent();
		}
	  }

	private List<TextLayout> getLayouts(Graphics graphics, Style style, float scale) {
		List<TextLayout> layouts = new ArrayList<>();
		AttributedString attrStr = getAttributedString(style, scale);
    	Graphics2D g2d = (Graphics2D) graphics;
    	FontRenderContext frc = g2d.getFontRenderContext();
    	LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
    	float wrappingWidth = (Slide.WIDTH - style.getIndent()) * scale;
    	while (measurer.getPosition() < getText().length()) {
    		TextLayout layout = measurer.nextLayout(wrappingWidth);
    		layouts.add(layout);
    	}
    	return layouts;
	}

	public String toString() {
		return "TextItem[" + getLevel()+","+getText()+"]";
	}
}
