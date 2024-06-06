package com.nhlstenden.JabberPoint.Controller;

import com.nhlstenden.JabberPoint.Accessor.Accessor;
import com.nhlstenden.JabberPoint.Command.NextSlideCommand;
import com.nhlstenden.JabberPoint.Command.OpenCommand;
import com.nhlstenden.JabberPoint.Command.PrevSlideCommand;
import com.nhlstenden.JabberPoint.Presentation.AboutBox;
import com.nhlstenden.JabberPoint.Presentation.Presentation;
import com.nhlstenden.JabberPoint.Accessor.XMLAccessor;

import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {
	
	private Frame parent; // the frame, only used as parent for the Dialogs
	private Presentation presentation; // Commands are given to the presentation

	public MenuController(Frame frame, Presentation presentationObject) {
        parent = frame;
        presentation = presentationObject;
        OpenCommand openCommand = new OpenCommand(presentation, parent);
		NextSlideCommand nextSlideCommand = new NextSlideCommand(presentation);
		PrevSlideCommand prevSlideCommand = new PrevSlideCommand(presentation);

        MenuItem menuItem;
        Menu fileMenu = new Menu("File");
        fileMenu.add(menuItem = mkMenuItem("Open"));
        menuItem.addActionListener(actionEvent -> openCommand.execute());
		fileMenu.add(menuItem = mkMenuItem("Next"));
		menuItem.addActionListener(actionEvent ->
		{
			presentation.clear();
			parent.repaint();
		});
		
		fileMenu.add(menuItem = mkMenuItem("Save"));
		menuItem.addActionListener(actionEvent ->
		{
			Accessor xmlAccessor = new XMLAccessor();
			try {
				xmlAccessor.saveFile(presentation, "dump.xml");
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(parent, "IO Exception: " + exc,
						"Save Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		fileMenu.addSeparator();
		fileMenu.add(menuItem = mkMenuItem("Exit"));
		menuItem.addActionListener(actionEvent -> presentation.exit(0));
		add(fileMenu);
		Menu viewMenu = new Menu("View");
		viewMenu.add(menuItem = mkMenuItem("Next"));
		menuItem.addActionListener(actionEvent -> nextSlideCommand.execute());
		viewMenu.add(menuItem = mkMenuItem("Previous"));
		menuItem.addActionListener(actionEvent -> prevSlideCommand.execute());
		viewMenu.add(menuItem = mkMenuItem("Go to"));
		menuItem.addActionListener(actionEvent ->
		{
			String pageNumberStr = JOptionPane.showInputDialog("Page number?");
			int pageNumber = Integer.parseInt(pageNumberStr);
			presentation.setSlideNumber(pageNumber - 1);
		});
		add(viewMenu);
		Menu helpMenu = new Menu("Help");
		helpMenu.add(menuItem = mkMenuItem("About"));
		menuItem.addActionListener(actionEvent -> AboutBox.show(parent));
		setHelpMenu(helpMenu);		// needed for portability (Motif, etc.).
	}

// create a menu item
	public MenuItem mkMenuItem(String name) {
		return new MenuItem(name, new MenuShortcut(name.charAt(0)));
	}
}
