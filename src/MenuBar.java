import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        add(getFileMenu());
        add(getProjectMenu());
        add(getAboutMenu());
    }

    private JMenu getFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        openMenuItem.addActionListener(MenuBarControllers.getFileOpenController());
        saveMenuItem.addActionListener(MenuBarControllers.getFileSaveController());
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        return fileMenu;
    }

    private JMenu getProjectMenu() {
        JMenu projectMenu = new JMenu("Project");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem startMenuItem = new JMenuItem("Start");
        JMenuItem stopMenuItem = new JMenuItem("Stop");
        newMenuItem.addActionListener(MenuBarControllers.getProjectNewController());
        startMenuItem.addActionListener(MenuBarControllers.getProjectStartController());
        stopMenuItem.addActionListener(MenuBarControllers.getProjectStopController());
        projectMenu.add(newMenuItem);
        projectMenu.add(startMenuItem);
        projectMenu.add(stopMenuItem);
        return projectMenu;
    }

    private JMenuItem getAboutMenu() {
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.setMaximumSize(aboutMenuItem.getPreferredSize());
        aboutMenuItem.addActionListener(MenuBarControllers.getAboutController());
        return aboutMenuItem;
    }
}
