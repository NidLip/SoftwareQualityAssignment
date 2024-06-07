package com.nhlstenden.JabberPoint.Controller;

import com.nhlstenden.JabberPoint.Command.NextSlideCommand;
import com.nhlstenden.JabberPoint.Command.OpenCommand;
import com.nhlstenden.JabberPoint.Command.PrevSlideCommand;
import com.nhlstenden.JabberPoint.Command.QuitCommand;
import com.nhlstenden.JabberPoint.Presentation.AboutBox;
import com.nhlstenden.JabberPoint.Presentation.Presentation;
import com.nhlstenden.JabberPoint.Accessor.XMLAccessor;

import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.io.IOException;

import javax.swing.JOptionPane;

public class MenuController extends MenuBar {

	private Frame parent;
	private Presentation presentation;

	public MenuController(Frame frame, Presentation presentationObject) {
		parent = frame;
		presentation = presentationObject;

		OpenCommand openCommand = new OpenCommand(presentation, parent);
		NextSlideCommand nextSlideCommand = new NextSlideCommand(presentation);
		PrevSlideCommand prevSlideCommand = new PrevSlideCommand(presentation);
		QuitCommand quitCommand = new QuitCommand(presentation);

		MenuItem menuItem;
		Menu fileMenu = new Menu("File");
		fileMenu.add(menuItem = mkMenuItem("Open"));
		menuItem.addActionListener(actionEvent -> openCommand.execute());

		fileMenu.add(menuItem = mkMenuItem("Save"));
		menuItem.addActionListener(actionEvent -> {
			XMLAccessor xmlAccessor = new XMLAccessor();
			try {
				xmlAccessor.saveFile(presentation, "dump.xml");
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(parent, "IO Exception: " + exc, "Save Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		fileMenu.addSeparator();
		fileMenu.add(menuItem = mkMenuItem("Exit"));
		menuItem.addActionListener(actionEvent -> quitCommand.execute());
		add(fileMenu);

		Menu viewMenu = new Menu("View");
		viewMenu.add(menuItem = mkMenuItem("Next"));
		menuItem.addActionListener(actionEvent -> nextSlideCommand.execute());

		viewMenu.add(menuItem = mkMenuItem("Previous"));
		menuItem.addActionListener(actionEvent -> prevSlideCommand.execute());

		viewMenu.add(menuItem = mkMenuItem("Go to"));
		menuItem.addActionListener(actionEvent -> {
			String pageNumberStr = JOptionPane.showInputDialog("Page number?");
			int pageNumber = Integer.parseInt(pageNumberStr);
			presentation.setSlideNumber(pageNumber - 1);
		});
		add(viewMenu);

		Menu helpMenu = new Menu("Help");
		helpMenu.add(menuItem = mkMenuItem("About"));
		menuItem.addActionListener(actionEvent -> AboutBox.show(parent));
		setHelpMenu(helpMenu);
	}

	public MenuItem mkMenuItem(String name) {
		return new MenuItem(name, new MenuShortcut(name.charAt(0)));
	}
}
