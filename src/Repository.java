import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Repository extends Observable {
    private double multiplierX, multiplierY;
    private final String TEAM_INFO = "Assignment 5 - Architecture\nProject by Karandeep Singh Grewal";
    private final int TOTAL_COLS = 3;
    private static List<double[]> cityDataList = new ArrayList<>();
    private static Repository repository = null;
    public double minX = 0, minY = 0, maxX = 0, maxY = 0;
    public double rangeX = 0, rangeY = 0;
    public static final int INDEX_FOR_X = 1, INDEX_FOR_Y = 2;

    private Repository() {
    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    public void addCity(double x, double y) {
        double[] city = new double[TOTAL_COLS];
        city[INDEX_FOR_X] = x;
        city[INDEX_FOR_Y] = y;
        cityDataList.add(city);
        notifyObservers();
    }

    public static double[] getXYForCity(int city) {
        return cityDataList.get(city);
    }

    public static int getLength() {
        return cityDataList.size();
    }

    public static boolean isLoaded() {
        if (cityDataList == null)
            return false;
        return true;
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
            cityDataList = stringToList(getCityDataString(fileData));
            assignXYBounds();
            calculateMultiplier();
            scaleData();
            notifyObservers();
        }
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
        return list;
    }

    private void scaleData() {
        for (int i = 0; i < getLength(); i++)
            cityDataList.set(i, originalToScreenScale(cityDataList.get(i)));
    }

    private void calculateMultiplier() {
        multiplierX = DrawingPanel.getInstance().getHeight() / getInstance().rangeX;
        multiplierY = DrawingPanel.getInstance().getWidth() / getInstance().rangeY;
    }

    private double[] originalToScreenScale(double[] point) {
        point[INDEX_FOR_X] = (point[INDEX_FOR_X] - getInstance().minX) * multiplierX;
        point[INDEX_FOR_Y] = (point[INDEX_FOR_Y] - getInstance().minY) * multiplierY;
        return point;
    }
}
