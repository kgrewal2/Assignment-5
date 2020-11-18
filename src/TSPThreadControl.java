public class TSPThreadControl {
    private static TSPThreadControl instance;
    private final TSPAlgo[] threads;
    int totalCities;
    boolean shouldStartFromScratch = true;
    boolean isRunning = false;
    int totalThreads;

    private TSPThreadControl() {
        totalThreads = Runtime.getRuntime().availableProcessors() - 1;
        if (totalThreads < 1)
            totalThreads = 1;
        threads = new TSPAlgo[totalThreads];
    }

    public static TSPThreadControl getInstance() {
        if (instance == null)
            instance = new TSPThreadControl();
        return instance;
    }

    public void startAllThreads() {
        if (shouldStartFromScratch) {
            totalCities = Repository.getInstance().getLength();
            int citiesPerThread = (int) Math.ceil((float) totalCities / threads.length);
            for (int i = 0; i < threads.length; i++) {
                int start = i * citiesPerThread;
                int end = start + citiesPerThread - 1;
                if (end >= totalCities)
                    end = totalCities - 1;
                threads[i] = new TSPAlgo();
                threads[i].setStartCity(start);
                threads[i].setEndCity(end);
                threads[i].start();
            }
            shouldStartFromScratch = false;
        } else if (!isRunning) {
            for (TSPAlgo t : threads) {
                t.resume();
            }
            isRunning = true;
        }
    }

    public void reset() {
        totalCities = 0;
        shouldStartFromScratch = true;
        isRunning = false;
        stopAllThreads();
    }

    public void stopAllThreads() {
        for (TSPAlgo t : threads) {
            if (t==null)
                return;
            t.suspend();
        }
        isRunning = false;
    }
}
