import java.util.ArrayList;
import java.util.List;

public class TSPRoute {
    private List<Integer> route;
    private double distance;

    public TSPRoute() {
        route = new ArrayList<>();
    }

    public void addCity(Integer city) {
        route.add(city);
    }

    public List<Integer> getData() {
        return route;
    }

    public double getDistance() {
        return distance;
    }

    public void addDistance(double distance) {
        this.distance += distance;
    }
}
