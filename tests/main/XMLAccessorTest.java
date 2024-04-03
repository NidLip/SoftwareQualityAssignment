package main;

import org.junit.jupiter.api.Test;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.*;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class XMLAccessorTest {

    @Test
    public void testLoadFile_loadValidPresentation_valid() throws Exception {
        XMLAccessor accessor = new XMLAccessor();
        Presentation presentation = new Presentation();

        String xml = "<presentation>" +
                "<showtitle>Test Title</showtitle>" +
                "<slide>" +
                "<title>Slide Title</title>" +
                "<item level=\"1\" kind=\"text\">Item Text</item>" +
                "</slide>" +
                "</presentation>";

        File tempFile = Files.createTempFile("test", ".xml").toFile();
        try (PrintWriter out = new PrintWriter(tempFile)) {
            out.println(xml);
        }

        accessor.loadFile(presentation, tempFile.getAbsolutePath());

        assertEquals("Test Title", presentation.getTitle());
        assertEquals(1, presentation.getSize());
        assertEquals("Slide Title", presentation.getSlide(0).getTitle());

        tempFile.delete();
    }

    @Test
    public void testLoadFile_loadPresentationWithNoSlides_doesNotThrowAndPrintsError() throws Exception {
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        final PrintStream originalErr = System.err;

        System.setErr(new PrintStream(errContent));

        XMLAccessor accessor = new XMLAccessor();
        Presentation presentation = new Presentation();

        String xml = "<presentation>" +
                "<showtitle>Test Title</showtitle>" +
                "</presentation>";

        File tempFile = Files.createTempFile("test", ".xml").toFile();
        try (PrintWriter out = new PrintWriter(tempFile)) {
            out.println(xml);
        }

        accessor.loadFile(presentation, tempFile.getAbsolutePath());

        assertEquals("Test Title", presentation.getTitle());
        assertDoesNotThrow(presentation::getSize);
        assertEquals("No slides found in the presentation\n", errContent.toString());


        System.setErr(originalErr);
        tempFile.delete();
    }

    @Test
    public void testLoadSlideItem_LoadValidSlideItem_Valid() {
        // Arrange
        XMLAccessor accessor = new XMLAccessor();
        Slide slide = new Slide("text");

        SlideItemFactory factory = mock(SlideItemFactory.class);
        SlideItem slideItem = mock(SlideItem.class);

        Element item = mock(Element.class);
        NamedNodeMap attributes = mock(NamedNodeMap.class);
        Node node = mock(Node.class);

        when(item.getAttributes()).thenReturn(attributes);
        when(attributes.getNamedItem(anyString())).thenReturn(node);
        when(node.getTextContent()).thenReturn("text");
        when(factory.createSlideItem(1, "test")).thenReturn(slideItem); // corrected line

        // Act
        accessor.loadSlideItem(slide, item, factory);

        // Assert
        assertEquals(1, slide.getSize());
    }
}