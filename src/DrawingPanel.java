import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class DrawingPanel extends JPanel implements Observer {
    private static final Color CITY_COLOR = new Color(15, 15, 15);
    private static final int CITY_RADIUS = 2;
    private static DrawingPanel drawingPanel;

    private DrawingPanel() {
        addMouseListener(DrawingPanelControllers.getDrawingPanel());
    }

    public static DrawingPanel getInstance() {
        if (drawingPanel == null) {
            drawingPanel = new DrawingPanel();
            Repository.getInstance().addObserver(drawingPanel);
        }
        return drawingPanel;
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
        revalidate();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawCities(graphics);
    }

    private void drawCities(Graphics graphics) {
        graphics.setColor(CITY_COLOR);
        if (Repository.isLoaded()) {
            for (int i = 0; i < Repository.getLength(); i++) {
                double[] point = Repository.getXYForCity(i);
                graphics.fillOval((int) point[Repository.INDEX_FOR_X] - CITY_RADIUS,
                        (int) point[Repository.INDEX_FOR_Y] - CITY_RADIUS, CITY_RADIUS * 2,
                        CITY_RADIUS * 2);
            }
        }
    }

}
