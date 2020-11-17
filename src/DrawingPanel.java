import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DrawingPanel extends JPanel implements Observer {
    private static final Color CITY_COLOR = new Color(15, 15, 15);
    private static final int CITY_RADIUS = 2;
    private static DrawingPanel drawingPanel;
    private final List<Color> routeColors;

    private DrawingPanel() {
        addMouseListener(DrawingPanelControllers.getDrawingPanel());
        setBackground(Color.WHITE);
        routeColors = new ArrayList<>();
        routeColors.add(new Color(255, 0, 0, 255));
        routeColors.add(new Color(179, 0, 137, 255));
        routeColors.add(new Color(0, 0, 0, 100));
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
        drawRoutes(graphics);
    }

    private void drawCities(Graphics graphics) {
        graphics.setColor(CITY_COLOR);
        Repository instance = Repository.getInstance();
        if (instance.isDataLoaded()) {
            for (int i = 0; i < instance.getLength(); i++) {
                double[] point = instance.getXYForCity(i);
                graphics.fillOval((int) point[Repository.INDEX_FOR_X] - CITY_RADIUS,
                        (int) point[Repository.INDEX_FOR_Y] - CITY_RADIUS, CITY_RADIUS * 2,
                        CITY_RADIUS * 2);
            }
        }
    }

    private void drawRoutes(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        int color = 0;
        for (TSPRoute t : Repository.getInstance().getShortestRoutes()) {
            graphics2D.setColor(routeColors.get(color));
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setStroke(new BasicStroke(2));
            color++;
            int shift = color * 2;
            List<Integer> route = t.getData();
            int cityA = route.get(0);
            for (int i = 1; i < route.size(); i++) {
                int cityB = route.get(i);
                double[] a = Repository.getInstance().getXYForCity(cityA);
                double[] b = Repository.getInstance().getXYForCity(cityB);
                graphics2D.drawLine((int) a[1] + shift, (int) a[2] + shift, (int) b[1] + shift, (int) b[2] + shift);
                cityA = cityB;
            }
        }
    }
}
