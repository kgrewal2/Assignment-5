import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuBarControllers {
    public static ActionListener getFileOpenController() {
        return e -> Repository.getInstance().openFile();
    }

    public static ActionListener getFileSaveController() {
        return e -> {
            try {
                Repository.getInstance().saveFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        };
    }

    public static ActionListener getProjectNewController() {
        return e -> {
            Repository.getInstance().reset();
            TSPThreadControl.getInstance().reset();
        };
    }

    public static ActionListener getProjectStartController() {
        return e -> TSPThreadControl.getInstance().startAllThreads();
    }

    public static ActionListener getProjectStopController() {
        return e -> TSPThreadControl.getInstance().stopAllThreads();
    }

    public static ActionListener getAboutController() {
        return e -> JOptionPane.showMessageDialog(null, Repository.getInstance().getTeamInfo());
    }
}
