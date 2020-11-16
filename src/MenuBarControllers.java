import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuBarControllers {
    public static ActionListener getFileOpenController() {
        return e -> {
            Repository.getInstance().openFile();
        };
    }

    public static ActionListener getFileSaveController() {
        return e -> System.out.println("Save Pressed");
    }

    public static ActionListener getProjectNewController() {
        return e -> System.out.println("New Project");
    }

    public static ActionListener getProjectStartController() {
        return e -> System.out.println("Start");
    }

    public static ActionListener getProjectStopController() {
        return e -> System.out.println("Stop");
    }

    public static ActionListener getAboutController() {
        return e -> {
            JOptionPane.showMessageDialog(null, Repository.getInstance().getTeamInfo());
        };
    }
}
