import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

public class Repository extends Observable {
    public static final int INDEX_FOR_CITY = 0, INDEX_FOR_X = 1, INDEX_FOR_Y = 2;
    final static int MAX_SHORTEST_ROUTES = 3;
    private static List<double[]> cityDataList = new ArrayList<>();
    private static Repository instance = null;
    private final int TOTAL_COLS = 3;
    public double minX, minY, maxX, maxY;
    public double rangeX, rangeY;
    private double multiplierX, multiplierY;
    private CopyOnWriteArrayList<TSPRoute> shortestRoutes = new CopyOnWriteArrayList<>();
    private final String TEAM_INFO = "Assignment 5 - Architecture\nProject by Karandeep Singh Grewal";

    private Repository() {
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public CopyOnWriteArrayList<TSPRoute> getShortestRoutes() {
        return shortestRoutes;
    }

    public void addCity(double x, double y) {
        double[] city = new double[TOTAL_COLS];
        city[INDEX_FOR_CITY] = getLength() + 1;
        city[INDEX_FOR_X] = x;
        city[INDEX_FOR_Y] = y;
        cityDataList.add(city);
        notifyObservers();
    }

    synchronized public void addToTopRoutes(TSPRoute route) {
        Comparator<TSPRoute> comparator = Comparator.comparingDouble(TSPRoute::getDistance);
        shortestRoutes.add(route);
        shortestRoutes.sort(comparator);
        if (shortestRoutes.size() > MAX_SHORTEST_ROUTES) {
            while (shortestRoutes.size() != MAX_SHORTEST_ROUTES) {
                shortestRoutes.remove(MAX_SHORTEST_ROUTES);
            }
        }
        notifyObservers();
    }

    public double[] getXYForCity(int city) {
        return cityDataList.get(city);
    }

    public int getLength() {
        return cityDataList.size();
    }

    public boolean isDataLoaded() {
        return cityDataList != null;
    }

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }

    public String getTeamInfo() {
        return TEAM_INFO;
    }

    public void openFile() {
        String fileData = FileUtility.openFile();
        if (fileData != null) {
            reset();
            fileData = fileData.replace("EOF", "");
            cityDataList = stringToList(getCityDataString(fileData));
            if (getLength() > 0) {
                assignXYBounds();
                calculateMultiplier();
                scaleData();
            }
        }
        notifyObservers();
    }

    private void assignXYBounds() {
        minX = maxX = getXYForCity(0)[INDEX_FOR_X];
        minY = maxY = getXYForCity(0)[INDEX_FOR_Y];
        for (double[] point : getList()
        ) {
            if (point[INDEX_FOR_X] < minX)
                minX = point[INDEX_FOR_X];
            if (point[INDEX_FOR_X] > maxX)
                maxX = point[INDEX_FOR_X];
            if (point[INDEX_FOR_Y] < minY)
                minY = point[INDEX_FOR_Y];
            if (point[INDEX_FOR_Y] > maxY)
                maxY = point[INDEX_FOR_Y];
        }
        rangeX = maxX - minX;
        rangeY = maxY - minY;
    }

    private String getCityDataString(String data_string) {
        int startIndex = data_string.indexOf("NODE_COORD_SECTION");
        if (startIndex == -1)
            startIndex = 0;
        else
            startIndex = data_string.indexOf("\n", startIndex) + 1;
        return data_string.substring(startIndex);
    }

    private List<double[]> getList() {
        return cityDataList;
    }

    private List<double[]> stringToList(String cityData) {
        List<double[]> list = new ArrayList<>();
        cityData = cityData.replaceAll("\\n", " ");
        int tempCol = 0;
        double[] city = new double[TOTAL_COLS];
        for (int i = 0; i < cityData.length(); i++) {
            if (cityData.charAt(i) != ' ') {
                StringBuilder word = new StringBuilder();
                while (cityData.charAt(i) != ' ') {
                    word.append(cityData.charAt(i));
                    i++;
                }
                if (tempCol >= 0)
                    city[tempCol] = Double.parseDouble(word.toString());
                tempCol++;
                if (tempCol == 3) {
                    tempCol = 0;
                    list.add(city);
                    city = new double[TOTAL_COLS];
                }
            }
        }
        for (double[] l : list) {
            for (double d : l)
                System.out.print(d + ", ");
            System.out.println("\n");
        }

        return list;
    }

    private void scaleData() {
        for (int i = 0; i < getLength(); i++)
            cityDataList.set(i, originalToScreenScale(cityDataList.get(i)));
    }

    public void saveFile() throws IOException {
        FileUtility.writeListToArray(cityDataList);
    }

    private void calculateMultiplier() {
        multiplierX = DrawingPanel.getInstance().getHeight() / rangeX;
        multiplierY = DrawingPanel.getInstance().getWidth() / rangeY;
    }

    private double[] originalToScreenScale(double[] point) {
        point[INDEX_FOR_X] = (point[INDEX_FOR_X] - minX) * multiplierX;
        point[INDEX_FOR_Y] = (point[INDEX_FOR_Y] - minY) * multiplierY;
        return point;
    }

    public void reset() {
        cityDataList = new ArrayList<>();
        minX = minY = maxY = maxX = rangeX = rangeY = multiplierY = multiplierX = 0;
        shortestRoutes = new CopyOnWriteArrayList<>();
        notifyObservers();
    }

    public double getDistanceBetweenCities(int city1, int city2) {
        double[] city1Location = getXYForCity(city1);
        double[] city2Location = getXYForCity(city2);
        return Math.sqrt(Math.pow(city1Location[INDEX_FOR_X] - city2Location[INDEX_FOR_X], 2)
                + Math.pow(city1Location[INDEX_FOR_Y] - city2Location[INDEX_FOR_Y], 2));
    }
}
