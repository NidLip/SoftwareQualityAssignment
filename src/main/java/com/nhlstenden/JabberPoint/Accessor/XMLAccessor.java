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

public class XMLAccessor extends Accessor {

	protected static final String SHOWTITLE = "showtitle";
	protected static final String SLIDETITLE = "title";
	protected static final String SLIDE = "slide";
	protected static final String ITEM = "item";
	protected static final String LEVEL = "level";
	protected static final String KIND = "kind";
	protected static final String PCE = "Parser Configuration Exception";
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
			Document document = builder.parse(new File(filename));
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

	private int parseLevel(Element item) {
		int level = 1;
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
		slide.append(factory.createSlideItem(type, level, item.getTextContent()));
	}

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
		writeSlideItems(out, slide.getAllSlideItems());
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
}
