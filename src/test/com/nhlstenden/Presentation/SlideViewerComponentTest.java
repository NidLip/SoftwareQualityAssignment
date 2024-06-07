package com.nhlstenden.Presentation;

import com.nhlstenden.JabberPoint.Presentation.Presentation;
import com.nhlstenden.JabberPoint.Presentation.SlideViewerComponent;
import com.nhlstenden.JabberPoint.Slide.Slide;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class SlideViewerComponentTest {

    private Presentation presentation;
    private SlideViewerComponent component;
    private JFrame frame;

    @BeforeEach
    void setUp() {
        // Use reflection to reset the singleton instance for Presentation before each test
        resetPresentationInstance();

        presentation = Presentation.getInstance();
        frame = new JFrame();
        component = new SlideViewerComponent(presentation, frame);
    }

    @AfterEach
    void tearDown() {
        frame.dispose();
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
    void testGetSlide_WhenCalled_ReturnsCurrentSlide() {
        Slide slide = new Slide("text");
        component.setSlide(slide);
        assertEquals(slide, component.getSlide());
    }

    @Test
    void testSetSlide_WhenCalled_SetsCurrentSlide() {
        Slide slide = new Slide("text");
        component.setSlide(slide);
        assertEquals(slide, component.getSlide());
    }

    @Test
    void testGetLabelFont_WhenCalled_ReturnsCurrentFont() {
        Font font = new Font("Arial", Font.PLAIN, 12);
        component.setLabelFont(font);
        assertEquals(font, component.getLabelFont());
    }

    @Test
    void testSetLabelFont_WhenCalled_SetsCurrentFont() {
        Font font = new Font("Arial", Font.PLAIN, 12);
        component.setLabelFont(font);
        assertEquals(font, component.getLabelFont());
    }

    @Test
    void testGetPresentation_WhenCalled_ReturnsCurrentPresentation() {
        assertEquals(presentation, component.getPresentation());
    }

    @Test
    void testSetPresentation_WhenCalled_SetsCurrentPresentation() {
        Presentation newPresentation = Presentation.getInstance();
        component.setPresentation(newPresentation);
        assertEquals(newPresentation, component.getPresentation());
    }

    @Test
    void testGetFrame_WhenCalled_ReturnsCurrentFrame() {
        assertEquals(frame, component.getFrame());
    }

    @Test
    void testSetFrame_WhenCalled_SetsCurrentFrame() {
        JFrame newFrame = new JFrame();
        component.setFrame(newFrame);
        assertEquals(newFrame, component.getFrame());
    }

    @Test
    void testGetPreferredSize_WhenCalled_ReturnsPreferredSize() {
        Dimension preferredSize = component.getPreferredSize();
        assertEquals(new Dimension(Slide.WIDTH, Slide.HEIGHT), preferredSize);
    }

    @Test
    void testUpdate_WithSlide_UpdatesPresentationAndSlide() {
        Slide slide = new Slide("text");
        Presentation newPresentation = Presentation.getInstance();
        newPresentation.setTitle("Test Title");
        newPresentation.append(slide);

        component.update(newPresentation, slide);

        assertEquals(newPresentation, component.getPresentation());
        assertEquals(slide, component.getSlide());
        assertEquals("Test Title", frame.getTitle());
    }

    @Test
    void testUpdate_NoSlide_RepaintsComponent() {
        component.update(presentation, null);

        assertEquals(presentation, component.getPresentation());
        assertNull(component.getSlide());
    }

    @Test
    void testPaintComponent_NoSlide_PaintsBackground() {
        // Create an image buffer to paint on
        BufferedImage image = new BufferedImage(1200, 800, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        // Paint the component
        component.paintComponent(graphics);

        // Verify that the background is painted
        Color bgColor = new Color(image.getRGB(0, 0));
        assertEquals(Color.white, bgColor);
    }
}
