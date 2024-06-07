package com.nhlstenden.JabberPoint.Presentation;

import com.nhlstenden.JabberPoint.Presentation.Presentation;
import com.nhlstenden.JabberPoint.Presentation.SlideViewerComponent;
import com.nhlstenden.JabberPoint.Slide.Slide;
import org.junit.jupiter.api.*;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class PresentationTest {

    private Presentation presentation;

    @BeforeEach
    void setUp() {
        // Use reflection to reset the singleton instance for Presentation before each test
        resetPresentationInstance();
        presentation = Presentation.getInstance();
    }

    @AfterEach
    void tearDown() {
        resetPresentationInstance();
    }

    private void resetPresentationInstance() {
        try {
            java.lang.reflect.Field instance = Presentation.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSingletonInstance() {
        Presentation instance1 = Presentation.getInstance();
        Presentation instance2 = Presentation.getInstance();
        assertSame(instance1, instance2, "Both instances should be the same");
    }

    @Test
    void testGetSize_WhenEmpty_ReturnsZero() {
        assertEquals(0, presentation.getSize());
    }

    @Test
    void testSetTitle_WhenCalled_SetsTitle() {
        String title = "Test Title";
        presentation.setTitle(title);
        assertEquals(title, presentation.getTitle());
    }

    @Test
    void testSetAndGetSlideNumber() {
        presentation.setSlideNumber(1);
        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    void testSetSlideNumber_WithValidIndex_UpdatesCurrentSlide() {
        Slide slide1 = new Slide("text");
        Slide slide2 = new Slide("text");
        presentation.append(slide1);
        presentation.append(slide2);

        presentation.setSlideNumber(1);
        assertEquals(slide2, presentation.getCurrentSlide());
    }

    @Test
    void testSetSlideNumber_WithInvalidIndex_DoesNotUpdateCurrentSlide() {
        Slide slide = new Slide("text");
        presentation.append(slide);

        presentation.setSlideNumber(-1);
        assertNull(presentation.getCurrentSlide());

        presentation.setSlideNumber(2);
        assertNull(presentation.getCurrentSlide());
    }

    @Test
    void testGetSlide_WithValidIndex_ReturnsSlide() {
        Slide slide = new Slide("text");
        presentation.append(slide);
        assertEquals(slide, presentation.getSlide(0));
    }

    @Test
    void testGetSlide_WithInvalidIndex_ReturnsNull() {
        Slide slide = new Slide("text");
        presentation.append(slide);
        assertNull(presentation.getSlide(-1));
        assertNull(presentation.getSlide(2));
    }

    @Test
    void testPrevSlide_WhenCalled_UpdatesSlideNumber() {
        Slide slide1 = new Slide("text");
        Slide slide2 = new Slide("text");
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(1);

        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void testNextSlide_WhenCalled_UpdatesSlideNumber() {
        Slide slide1 = new Slide("text");
        Slide slide2 = new Slide("text");
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(0);

        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    void testPrevSlide_AtFirstSlide_DoesNotUpdateSlideNumber() {
        Slide slide = new Slide("text");
        presentation.append(slide);
        presentation.setSlideNumber(0);

        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void testNextSlide_AtLastSlide_DoesNotUpdateSlideNumber() {
        Slide slide = new Slide("text");
        presentation.append(slide);
        presentation.setSlideNumber(0);

        presentation.nextSlide();
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void testClear_WhenCalled_EmptiesPresentation() {
        Slide slide = new Slide("text");
        presentation.append(slide);

        presentation.clear();
        assertEquals(0, presentation.getSize());
        assertEquals(-1, presentation.getSlideNumber());
    }

    @Test
    void testAppend_WhenCalled_AddsSlide() {
        Slide slide = new Slide("text");
        presentation.append(slide);
        assertEquals(1, presentation.getSize());
        assertEquals(slide, presentation.getSlide(0));
    }

    @Test
    void testSetAndGetSlideViewComponent() {
        SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, new JFrame());
        presentation.setSlideViewComponent(slideViewerComponent);
        assertEquals(slideViewerComponent, presentation.getSlideViewComponent());
    }
}
