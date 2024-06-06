package com.nhlstenden.Controller;

import com.nhlstenden.JabberPoint.Controller.MenuController;
import com.nhlstenden.JabberPoint.Presentation.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

class MenuControllerTest {

    private Frame testFrame;
    private Presentation testPresentation;
    private MenuController menuController;

    @BeforeEach
    void setUp() {
        testFrame = new Frame();
        testPresentation = Presentation.getInstance();
        menuController = new MenuController(testFrame, testPresentation);
    }

    @Test
    void testMenuController_InitializesMenuItemsCorrectly() {
        assertEquals(3, menuController.getMenuCount()); // File, View, Help

        Menu fileMenu = menuController.getMenu(0);
        assertEquals("File", fileMenu.getLabel());
        assertEquals(5, fileMenu.getItemCount()); // Open, Next, Save, Separator, Exit

        Menu viewMenu = menuController.getMenu(1);
        assertEquals("View", viewMenu.getLabel());
        assertEquals(3, viewMenu.getItemCount()); // Next, Previous, Go to

        Menu helpMenu = menuController.getMenu(2);
        assertEquals("Help", helpMenu.getLabel());
        assertEquals(1, helpMenu.getItemCount()); // About
    }

    @Test
    void testOpenMenuItem_HasActionListener() {
        Menu fileMenu = menuController.getMenu(0);
        MenuItem openItem = fileMenu.getItem(0);
        ActionListener[] listeners = openItem.getActionListeners();
        assertEquals(1, listeners.length);
        assertTrue(listeners[0] instanceof ActionListener);
    }

    @Test
    void testNextMenuItem_HasActionListener() {
        Menu fileMenu = menuController.getMenu(0);
        MenuItem nextItem = fileMenu.getItem(1);
        ActionListener[] listeners = nextItem.getActionListeners();
        assertEquals(1, listeners.length);
        assertTrue(listeners[0] instanceof ActionListener);
    }

    @Test
    void testSaveMenuItem_HasActionListener() {
        Menu fileMenu = menuController.getMenu(0);
        MenuItem saveItem = fileMenu.getItem(2);
        ActionListener[] listeners = saveItem.getActionListeners();
        assertEquals(1, listeners.length);
        assertTrue(listeners[0] instanceof ActionListener);
    }

    @Test
    void testExitMenuItem_HasActionListener() {
        Menu fileMenu = menuController.getMenu(0);
        MenuItem exitItem = fileMenu.getItem(4);
        ActionListener[] listeners = exitItem.getActionListeners();
        assertEquals(1, listeners.length);
        assertTrue(listeners[0] instanceof ActionListener);
    }

    @Test
    void testNextViewMenuItem_HasActionListener() {
        Menu viewMenu = menuController.getMenu(1);
        MenuItem nextItem = viewMenu.getItem(0);
        ActionListener[] listeners = nextItem.getActionListeners();
        assertEquals(1, listeners.length);
        assertTrue(listeners[0] instanceof ActionListener);
    }

    @Test
    void testPreviousViewMenuItem_HasActionListener() {
        Menu viewMenu = menuController.getMenu(1);
        MenuItem prevItem = viewMenu.getItem(1);
        ActionListener[] listeners = prevItem.getActionListeners();
        assertEquals(1, listeners.length);
        assertTrue(listeners[0] instanceof ActionListener);
    }

    @Test
    void testGoToViewMenuItem_HasActionListener() {
        Menu viewMenu = menuController.getMenu(1);
        MenuItem goToItem = viewMenu.getItem(2);
        ActionListener[] listeners = goToItem.getActionListeners();
        assertEquals(1, listeners.length);
        assertTrue(listeners[0] instanceof ActionListener);
    }

    @Test
    void testAboutHelpMenuItem_HasActionListener() {
        Menu helpMenu = menuController.getMenu(2);
        MenuItem aboutItem = helpMenu.getItem(0);
        ActionListener[] listeners = aboutItem.getActionListeners();
        assertEquals(1, listeners.length);
        assertTrue(listeners[0] instanceof ActionListener);
    }
}
