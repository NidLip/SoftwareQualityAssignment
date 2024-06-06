package com.nhlstenden.JabberPoint.Presentation;

import com.nhlstenden.JabberPoint.Slide.Slide;

import java.util.ArrayList;

public class Presentation {
	private String title; // title of the presentation
	private ArrayList<Slide> showList = null; // an ArrayList with Slides
	private int currentSlideNumber = 0; // the slidenummer of the current main.com.nhlstenden.JabberPoint.Slide.Slide
	private SlideViewerComponent slideViewComponent; // the viewcomponent of the Slides

	// Singleton instance
	private static Presentation instance;

	// Private constructor to prevent instantiation
	private Presentation() {
		this.slideViewComponent = null;
		clear();
	}

	private Presentation(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
		clear();
	}

	// Public method to provide access to the singleton instance
	public static synchronized Presentation getInstance() {
		if (instance == null) {
			instance = new Presentation();
		}
		return instance;
	}

	// Optionally, provide another method to get an instance with a SlideViewerComponent
	public static synchronized Presentation getInstance(SlideViewerComponent slideViewerComponent) {
		if (instance == null) {
			instance = new Presentation(slideViewerComponent);
		}
		return instance;
	}

	public int getSize() {
		return showList.size();
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// give the number of the current slide
	public int getSlideNumber() {
		return this.currentSlideNumber;
	}

	// change the current slide number and signal it to the window
	public void setSlideNumber(int number) {
		this.currentSlideNumber = number;
		if (slideViewComponent != null) {
			slideViewComponent.update(this, getCurrentSlide());
		}
	}

	public SlideViewerComponent getSlideViewComponent()
	{
		return this.slideViewComponent;
	}

	public void setSlideViewComponent(SlideViewerComponent slideViewComponent)
	{
		this.slideViewComponent = slideViewComponent;
	}

	// go to the previous slide unless your at the beginning of the presentation
	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
		}
	}

	// go to the next slide unless your at the end of the presentation.
	public void nextSlide() {
		if (currentSlideNumber < (showList.size()-1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	// Delete the presentation to be ready for the next one.
	public void clear() {
		showList = new ArrayList<>();
		setSlideNumber(-1);
	}

	// Add a slide to the presentation
	public void append(Slide slide) {
		showList.add(slide);
	}

	// Get a slide with a certain slidenumber
	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()){
			return null;
		}
		return showList.get(number);
	}

	// Give the current slide
	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}
}
