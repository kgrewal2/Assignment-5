import java.util.ArrayList;
import java.util.List;

public class TSPAlgo extends Thread {
    int id;
    int startCity, endCity;
    int currentCity;

    public void setStartCity(int city) {
        startCity = city;
    }

    public void setEndCity(int city) {
        endCity = city;
    }

    public void run() {
        currentCity = startCity;
        while (currentCity <= endCity) {
            TSPRoute tspRoute = new TSPRoute();
            //START ALGO FOR CITY
            int temp = currentCity;
            List<Integer> pendingCities = getRandomRoute();
            tspRoute.addCity(temp);
            pendingCities.remove((Integer) temp);
            for (int i = 0; i < Repository.getInstance().getLength() - 1; i++) {
                double minDistance = Double.POSITIVE_INFINITY;
                int nearestNeighbour = pendingCities.get(0);
                for (Integer city : pendingCities) {
                    double distance = Repository.getInstance().getDistanceBetweenCities(temp, city);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestNeighbour = city;
                    }
                }
                tspRoute.addCity(nearestNeighbour);
                tspRoute.addDistance(minDistance);
                pendingCities.remove((Integer) nearestNeighbour);
            }
            Repository.getInstance().addToTopRoutes(tspRoute);
            //ALGO COMPLETE FOR CITY
            currentCity++;
        }
    }

    public List<Integer> getRandomRoute() {
        List<Integer> route = new ArrayList<>();
        for (int i = 0; i < Repository.getInstance().getLength(); i++) {
            route.add(i);
        }
        return route;
    }
}
