package com.nhlstenden.JabberPoint.Accessor;

import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.nhlstenden.JabberPoint.Factory.SlideItemFactory;
import com.nhlstenden.JabberPoint.Factory.TextItemFactory;
import com.nhlstenden.JabberPoint.Presentation.Presentation;
import com.nhlstenden.JabberPoint.Slide.BitmapItem;
import com.nhlstenden.JabberPoint.Slide.Slide;
import com.nhlstenden.JabberPoint.Slide.SlideItem;
import com.nhlstenden.JabberPoint.Slide.TextItem;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;


/** main.com.nhlstenden.JabberPoint.Accessor.XMLAccessor, reads and writes XML files
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor extends Accessor {
	
    /** Default API to use. */
    protected static final String DEFAULT_API_TO_USE = "dom";
    
    /** namen van xml tags of attributen */
    protected static final String SHOWTITLE = "showtitle";
    protected static final String SLIDETITLE = "title";
    protected static final String SLIDE = "slide";
    protected static final String ITEM = "item";
    protected static final String LEVEL = "level";
    protected static final String KIND = "kind";
    protected static final String TEXT = "text";
    protected static final String IMAGE = "image";
    
    /** tekst van messages */
    protected static final String PCE = "Parser Configuration Exception";
    protected static final String UNKNOWNTYPE = "Unknown Element type";
    protected static final String NFE = "Number Format Exception";
    
    
    private String getTitle(Element element, String tagName) {
    	NodeList titles = element.getElementsByTagName(tagName);
		if (titles.getLength() == 0) {
			throw new IllegalArgumentException("No " + tagName + " found in the presentation");
		}
    	return titles.item(0).getTextContent();
    }

	private void parseSlides(Presentation presentation, NodeList slides) throws IllegalArgumentException {
		int max = slides.getLength();

		 if (max == 0) {
		 	throw new IllegalArgumentException("No slides found in the presentation");
		 }

		for (int slideNumber = 0; slideNumber < max; slideNumber++) {
			Element xmlSlide = (Element) slides.item(slideNumber);
			SlideItemFactory factory = new TextItemFactory();
			Slide slide = new Slide("text");
			slide.setTitle(getTitle(xmlSlide, SLIDETITLE));
			presentation.append(slide);
			NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
			parseSlideItems(slide, slideItems, factory);
		}
	}
	
	private void parseSlideItems(Slide slide, NodeList slideItems, SlideItemFactory factory) {
		int maxItems = slideItems.getLength();
		for (int itemNumber = 0; itemNumber < maxItems; itemNumber++) {
			Element item = (Element) slideItems.item(itemNumber);
			loadSlideItem(slide, item, factory);
		}
	}

	public void loadFile(Presentation presentation, String filename) throws IOException, IllegalArgumentException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();    
			Document document = builder.parse(new File(filename)); // Create a JDOM document
			Element doc = document.getDocumentElement();
			presentation.setTitle(getTitle(doc, SHOWTITLE));
			NodeList slides = doc.getElementsByTagName(SLIDE);
			parseSlides(presentation, slides);
		} 
		catch (IOException iox) {
			System.err.println(iox.toString());
		}
		catch (SAXException | IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(null,
					"Error: " + ex, "Jabberpoint Error ",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		catch (ParserConfigurationException pcx) {
			System.err.println(PCE);
		}
	}
	// public void loadFile(com.nhlstenden.JabberPoint.Presentation.Presentation presentation, String filename) throws IOException {
	// 	int slideNumber, itemNumber, max = 0, maxItems = 0;
	// 	try {
	// 		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();    
	// 		Document document = builder.parse(new File(filename)); // Create a JDOM document
	// 		Element doc = document.getDocumentElement();
	// 		presentation.setTitle(getTitle(doc, SHOWTITLE));

	// 		NodeList slides = doc.getElementsByTagName(SLIDE);
	// 		max = slides.getLength();
	// 		for (slideNumber = 0; slideNumber < max; slideNumber++) {
	// 			Element xmlSlide = (Element) slides.item(slideNumber);
	// 			com.nhlstenden.JabberPoint.Factory.SlideItemFactory factory = new com.nhlstenden.JabberPoint.Factory.TextItemFactory();
	// 			com.nhlstenden.JabberPoint.Slide.Slide slide = new com.nhlstenden.JabberPoint.Slide.Slide("text");
	// 			slide.setTitle(getTitle(xmlSlide, SLIDETITLE));
	// 			presentation.append(slide);
				
	// 			NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
	// 			maxItems = slideItems.getLength();
	// 			for (itemNumber = 0; itemNumber < maxItems; itemNumber++) {
	// 				Element item = (Element) slideItems.item(itemNumber);
	// 				loadSlideItem(slide, item, factory);
	// 			}
	// 		}
	// 	} 
	// 	catch (IOException iox) {
	// 		System.err.println(iox.toString());
	// 	}
	// 	catch (SAXException sax) {
	// 		System.err.println(sax.getMessage());
	// 	}
	// 	catch (ParserConfigurationException pcx) {
	// 		System.err.println(PCE);
	// 	}	
	// }

	private int parseLevel(Element item) {
		int level = 1; // default
		NamedNodeMap attributes = item.getAttributes();
		String leveltext = attributes.getNamedItem(LEVEL).getTextContent();
		if (leveltext != null) {
			try {
				level = Integer.parseInt(leveltext);
			} catch(NumberFormatException x) {
				System.err.println(NFE);
			}
		}
		return level;
	}
	
	private String parseType(Element item) {
		NamedNodeMap attributes = item.getAttributes();
		return attributes.getNamedItem(KIND).getTextContent();
	}

	public void loadSlideItem(Slide slide, Element item, SlideItemFactory factory) {
		int level = parseLevel(item);
		String type = parseType(item);
	
		if (TEXT.equals(type)) {
			slide.append(new TextItem(level, item.getTextContent()));
		} else if (IMAGE.equals(type)) {
			slide.append(new BitmapItem(level, item.getTextContent()));
		} else {
			System.err.println(UNKNOWNTYPE);
		}
	}

	// protected void loadSlideItem(com.nhlstenden.JabberPoint.Slide.Slide slide, Element item, com.nhlstenden.JabberPoint.Factory.SlideItemFactory factory) {
	// 	int level = 1; // default
	// 	NamedNodeMap attributes = item.getAttributes();
	// 	String leveltext = attributes.getNamedItem(LEVEL).getTextContent();
	// 	if (leveltext != null) {
	// 		try {
	// 			level = Integer.parseInt(leveltext);
	// 		}
	// 		catch(NumberFormatException x) {
	// 			System.err.println(NFE);
	// 		}
	// 	}
	// 	String type = attributes.getNamedItem(KIND).getTextContent();
	// 	if (TEXT.equals(type)) {
	// 		slide.append(new com.nhlstenden.JabberPoint.Slide.TextItem(level, item.getTextContent()));
	// 	}
	// 	else {
	// 		if (IMAGE.equals(type)) {
	// 			slide.append(new com.nhlstenden.JabberPoint.Slide.BitmapItem(level, item.getTextContent()));
	// 		}
	// 		else {
	// 			System.err.println(UNKNOWNTYPE);
	// 		}
	// 	}
	// 	if (TEXT.equals(type)) {
	// 		slide.append(factory.createSlideItem(level, item.getTextContent()));
	// 	} else if (IMAGE.equals(type)) {
	// 		slide.append(factory.createSlideItem(level, item.getTextContent()));
	// 	} else {
	// 		System.err.println(UNKNOWNTYPE);
	// 	}
	// }
	private void writeHeader(PrintWriter out) {
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
        out.println("<presentation>");
    }

	private void writeTitle(PrintWriter out, String title) {
        out.print("<showtitle>");
        out.print(title);
        out.println("</showtitle>");
    }

    private void writeSlides(PrintWriter out, Presentation presentation) {
        for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
            Slide slide = presentation.getSlide(slideNumber);
            writeSlide(out, slide);
        }
    }

    private void writeSlide(PrintWriter out, Slide slide) {
        out.println("<slide>");
        out.println("<title>" + slide.getTitle() + "</title>");
        writeSlideItems(out, slide.getSlideItems());
        out.println("</slide>");
    }

    private void writeSlideItems(PrintWriter out, Vector<SlideItem> slideItems) {
        for (int itemNumber = 0; itemNumber < slideItems.size(); itemNumber++) {
            SlideItem slideItem = slideItems.elementAt(itemNumber);
            writeSlideItem(out, slideItem);
        }
    }

	private void writeSlideItem(PrintWriter out, SlideItem slideItem) {
        out.print("<item kind=");
        if (slideItem instanceof TextItem) {
            writeTextItem(out, (TextItem) slideItem);
        } else if (slideItem instanceof BitmapItem) {
            writeBitmapItem(out, (BitmapItem) slideItem);
        }
        out.println("</item>");
    }

    private void writeTextItem(PrintWriter out, TextItem textItem) {
        out.print("\"text\" level=\"" + textItem.getLevel() + "\">");
        out.print(textItem.getText());
    }

    private void writeBitmapItem(PrintWriter out, BitmapItem bitmapItem) {
        out.print("\"image\" level=\"" + bitmapItem.getLevel() + "\">");
        out.print(bitmapItem.getName());
    }
	
	public void saveFile(Presentation presentation, String filename) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        writeHeader(out);
        writeTitle(out, presentation.getTitle());
        writeSlides(out, presentation);
        out.close();
    }
	// public void saveFile(com.nhlstenden.JabberPoint.Presentation.Presentation presentation, String filename) throws IOException {
	// 	PrintWriter out = new PrintWriter(new FileWriter(filename));
	// 	out.println("<?xml version=\"1.0\"?>");
	// 	out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
	// 	out.println("<presentation>");
	// 	out.print("<showtitle>");
	// 	out.print(presentation.getTitle());
	// 	out.println("</showtitle>");
	// 	for (int slideNumber=0; slideNumber<presentation.getSize(); slideNumber++) {
	// 		com.nhlstenden.JabberPoint.Slide.Slide slide = presentation.getSlide(slideNumber);
	// 		out.println("<slide>");
	// 		out.println("<title>" + slide.getTitle() + "</title>");
	// 		Vector<com.nhlstenden.JabberPoint.Slide.SlideItem> slideItems = slide.getSlideItems();
	// 		for (int itemNumber = 0; itemNumber<slideItems.size(); itemNumber++) {
	// 			com.nhlstenden.JabberPoint.Slide.SlideItem slideItem = (com.nhlstenden.JabberPoint.Slide.SlideItem) slideItems.elementAt(itemNumber);
	// 			out.print("<item kind="); 
	// 			if (slideItem instanceof com.nhlstenden.JabberPoint.Slide.TextItem) {
	// 				out.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
	// 				out.print( ( (com.nhlstenden.JabberPoint.Slide.TextItem) slideItem).getText());
	// 			}
	// 			else {
	// 				if (slideItem instanceof com.nhlstenden.JabberPoint.Slide.BitmapItem) {
	// 					out.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
	// 					out.print( ( (com.nhlstenden.JabberPoint.Slide.BitmapItem) slideItem).getName());
	// 				}
	// 				else {
	// 					System.out.println("Ignoring " + slideItem);
	// 				}
	// 			}
	// 			out.println("</item>");
	// 		}
	// 		out.println("</slide>");
	// 	}
	// 	out.println("</presentation>");
	// 	out.close();
	// }
}
