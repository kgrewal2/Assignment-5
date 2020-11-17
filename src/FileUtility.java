import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FileUtility {
    private static File selectedFile = null;

    public static String openFile() {
        JFileChooser fileChooser = getPreconfiguredJFileChooser();
        fileChooser.setDialogTitle("Open File");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            StringBuilder contentBuilder = new StringBuilder();
            try (Stream<String> stream = Files.lines(Paths.get(selectedFile.toString()), StandardCharsets.UTF_8)) {
                stream.forEach(s -> contentBuilder.append(s).append("\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return contentBuilder.toString();
        }
        return null;
    }

    public static void writeListToArray(List<double[]> list) throws IOException {
        JFileChooser fileChooser = getPreconfiguredJFileChooser();
        fileChooser.setDialogTitle("Save As");
        fileChooser.setApproveButtonText("Save");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            StringBuilder builder = new StringBuilder();
            for (double[] row : list) {
                for (double entry : row) {
                    builder.append(entry).append(" ");
                }
                builder.append("\n");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile.getAbsoluteFile()));
            writer.write(builder.toString());
            writer.close();
        }
    }

    private static JFileChooser getPreconfiguredJFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        if (selectedFile == null) {
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        } else {
            fileChooser.setCurrentDirectory(selectedFile.getParentFile());
        }
        return fileChooser;
    }
}
