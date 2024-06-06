package Accessors;

import com.nhlstenden.JabberPoint.Accessor.XMLAccessor;
import com.nhlstenden.JabberPoint.Factory.SlideItemFactory;
import com.nhlstenden.JabberPoint.Factory.TextItemFactory;
import com.nhlstenden.JabberPoint.Presentation.Presentation;
import com.nhlstenden.JabberPoint.Slide.Slide;
import org.junit.jupiter.api.Test;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.*;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;


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
    public void testLoadSlideItem_LoadValidSlideItem_Valid() throws ParserConfigurationException
    {
        XMLAccessor accessor = new XMLAccessor();
        Slide slide = new Slide("text");

        SlideItemFactory factory = new TextItemFactory();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element item = document.createElement("item");
        item.setAttribute("level", "1");
        item.setAttribute("kind", "text");
        item.setTextContent("text");

        accessor.loadSlideItem(slide, item, factory);

        assertEquals(1, slide.getSize());
    }

    @Test
    public void testLoadSlideItem_LoadSlideItemWithoutAttributes_Throws() throws ParserConfigurationException
    {
        XMLAccessor accessor = new XMLAccessor();
        Slide slide = new Slide("text");

        SlideItemFactory factory = new TextItemFactory();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element item = document.createElement("item");

        assertThrows(Exception.class, () -> accessor.loadSlideItem(slide, item, factory));
    }

    @Test
    public void testSaveFile_ValidPresentation_SavesCorrectly() throws IOException {
        XMLAccessor accessor = new XMLAccessor();
        Presentation presentation = new Presentation();
        presentation.setTitle("Test Title");
        Slide slide = new Slide("text");
        slide.setTitle("Slide Title");
        presentation.append(slide);

        File tempFile = Files.createTempFile("test", ".xml").toFile();

        accessor.saveFile(presentation, tempFile.getAbsolutePath());

        String content = new String(Files.readAllBytes(tempFile.toPath()));
        assertTrue(content.contains("Test Title"));
        assertTrue(content.contains("Slide Title"));

        tempFile.delete();
    }
}
