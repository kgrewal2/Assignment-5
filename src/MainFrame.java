import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("Assignment 5 - Karandeep Singh Grewal");
        Dimension DEFAULT_DIMENSIONS = new Dimension(1080, 1024);
        setSize(DEFAULT_DIMENSIONS);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        add(new MenuBar(), BorderLayout.NORTH);
        add(DrawingPanel.getInstance(), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        setCommonUISettings();
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    private static void setCommonUISettings(){
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
