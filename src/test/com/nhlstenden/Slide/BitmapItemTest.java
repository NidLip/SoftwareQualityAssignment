package com.nhlstenden.Slide;

import com.nhlstenden.JabberPoint.Slide.BitmapItem;
import com.nhlstenden.Style.Style;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class BitmapItemTest {

    private BitmapItem bitmapItem;
    private Style testStyle;
    private BufferedImage testImage;
    private Graphics testGraphics;
    private ImageObserver testObserver;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        bitmapItem = new BitmapItem(1, "JabberPoint.jpg");

        // Create an instance of Style with required parameters
        testStyle = new Style(10, Color.BLACK, 12, 10);

        testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        testGraphics = testImage.getGraphics();
        testObserver = (img, infoflags, x, y, width, height) -> false;
    }

    @Test
    void testConstructor_WithValidImage_SetsBufferedImage() {
        assertNotNull(bitmapItem.getBufferedImage());
        assertEquals("JabberPoint.jpg", bitmapItem.getName());
    }

    @Test
    void testConstructor_WithInvalidImage_PrintsErrorMessage() {
        // Redirect stderr to capture the error message
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        try {
            new BitmapItem(1, "nonexistent-image.jpg");
        } catch (FileNotFoundException e) {
            // Exception expected, do nothing
        }

        // Restore stderr
        System.setErr(System.err);

        // Verify that the error message is printed
        String errorMessage = errContent.toString();
        assertTrue(errorMessage.contains("File nonexistent-image.jpg not found"));
    }

    @Test
    void testGetName_WhenCalled_ReturnsImageName() {
        assertEquals("JabberPoint.jpg", bitmapItem.getName());
    }

    @Test
    void testSetName_WhenCalled_ChangesImageName() {
        bitmapItem.setName("new-image.jpg");
        assertEquals("new-image.jpg", bitmapItem.getName());
    }

    @Test
    void testGetBufferedImage_WhenCalled_ReturnsBufferedImage() {
        assertNotNull(bitmapItem.getBufferedImage());
    }

    @Test
    void testSetBufferedImage_WhenCalled_ChangesBufferedImage() {
        BufferedImage newImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        bitmapItem.setBufferedImage(newImage);
        assertEquals(newImage, bitmapItem.getBufferedImage());
    }

    @Test
    void testGetBoundingBox_WhenCalled_ReturnsCorrectRectangle() {
        BufferedImage newImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        bitmapItem.setBufferedImage(newImage);

        Rectangle boundingBox = bitmapItem.getBoundingBox(testGraphics, testObserver, 1.0f, testStyle);

        assertNotNull(boundingBox);
        assertEquals(new Rectangle(10, 0, 100, 110), boundingBox);
    }
}
