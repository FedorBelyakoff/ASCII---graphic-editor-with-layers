package classes;

import ui.UiLocator;

import javax.swing.*;

import static classes.EditMenuItems.*;
import static enumerations.FileMenuItems.*;
import static enumerations.HelpMenuItems.ABOUT;
import static enumerations.HelpMenuItems.HELP;
import static enumerations.WindowComponents.*;
import static ui.UiResources.bundle;

public class MenuFactory {
    private final UiLocator locator;
    private JMenuBar menuBar;
    private JMenu editMenu;
    private JMenu helpMenu;

    public MenuFactory(UiLocator locator) {
        this.locator = locator;
    }

    public void createAllElements() {
        menuBar = createMenuBar();
        createFileMenu(menuBar);
        createEditMenu(menuBar);
        createHelpMenu(menuBar);
    }

    private JMenuBar createMenuBar() {
        menuBar = new JMenuBar();
        JFrame frame = (JFrame) locator.get(FRAME);
        frame.setJMenuBar(menuBar);
        locator.add(menuBar, MENU_BAR);
        return menuBar;
    }

    private void createFileMenu(JMenuBar menuBar) {
        JMenu fileMenu = new JMenu(bundle.getString("FileMenu.title"));

        locator.add(fileMenu, FILE_MENU);

        menuBar.add(fileMenu, 0);

        addItem(bundle.getString("FileMenu.create"), CREATE, fileMenu);

        addItem(bundle.getString("FileMenu.settings"), SETTINGS, fileMenu);

        addItem(bundle.getString("FileMenu.save"), SAVE, fileMenu);

        addItem(bundle.getString("FileMenu.saveAs"), SAVE_AS, fileMenu);

        addItem(bundle.getString("FileMenu.exit"), EXIT, fileMenu);
    }

    private void createEditMenu(JMenuBar menuBar) {
        editMenu = addEditMenu(menuBar);

        addEditMenuItems(editMenu);

        addFlipMenu(editMenu);

        addRotateMenu(editMenu);
    }

    private void createHelpMenu(JMenuBar menuBar) {
        helpMenu = new JMenu(bundle.getString("Help.title"));
        locator.add(helpMenu, HELP);
        menuBar.add(helpMenu, 2);

        addItem(bundle.getString("Help.about"), ABOUT, helpMenu);
    }

    private void addFlipMenu(JMenu editMenu) {
        JMenu flip = createSubMenu(bundle.getString("EditMenu.flip"), FLIP, editMenu);
        addItem(bundle.getString("EditMenu.flip.horizontally"), FLIP_HORIZONTALLY, flip);
        addItem(bundle.getString("EditMenu.flip.vertically"), FLIP_VERTICALLY, flip);
    }

    private void addRotateMenu(JMenu editMenu) {

        JMenu rotate = createSubMenu(bundle.getString("EditMenu.rotate"), ROTATE, editMenu);
        addItem(bundle.getString("EditMenu.rotate.right90"), ROTATE_RIGHT90, rotate);
        addItem(bundle.getString("EditMenu.rotate.right180"), ROTATE_RIGHT180, rotate);
        addItem(bundle.getString("EditMenu.rotate.left90"), ROTATE_LEFT90, rotate);
        addItem( bundle.getString("EditMenu.rotate.left180"), ROTATE_LEFT180, rotate);
    }

    private JMenu addEditMenu(JMenuBar menuBar) {
        editMenu = new JMenu(bundle.getString("EditMenu.title"));
        locator.add(editMenu, EDIT);
        menuBar.add(editMenu, 1);
        return editMenu;
    }

    private void addEditMenuItems(JMenu editMenu) {
        addItem(bundle.getString("EditMenu.undo"), UNDO, editMenu);
        addItem(bundle.getString("EditMenu.redo"), REDO, editMenu);
        addItem(bundle.getString("EditMenu.deselect"), DESELECT, editMenu);
        addItem(bundle.getString("EditMenu.delete"), DELETE, editMenu);
        addItem(bundle.getString("EditMenu.cut"), CUT, editMenu);
        addItem(bundle.getString("EditMenu.copy"), COPY, editMenu);
        addItem(bundle.getString("EditMenu.paste"), PASTE, editMenu);
    }



    private <K extends Enum<K>> void addItem(String text, K type, JMenu parent) {
        JMenuItem item = new JMenuItem(text);
        locator.add(item, type);
        parent.add(item);
    }


    private <K extends Enum<K>> JMenu createSubMenu(String text, K key, JMenu parent) {
        JMenu subMenu = new JMenu(text);
        locator.add(subMenu, key);
        parent.add(subMenu);
        return subMenu;
    }
}
