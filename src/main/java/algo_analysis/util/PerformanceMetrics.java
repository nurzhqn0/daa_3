package algo_analysis.util;


public class PerformanceMetrics {
    private int operationsCount;
    private long startTime;
    private long endTime;
    private boolean isRunning;

    public PerformanceMetrics() {
        this.operationsCount = 0;
        this.startTime = 0;
        this.endTime = 0;
        this.isRunning = false;
    }

    public void start() {
        this.startTime = System.nanoTime();
        this.isRunning = true;
        this.operationsCount = 0;
    }

    public void stop() {
        if (isRunning) {
            this.endTime = System.nanoTime();
            this.isRunning = false;
        }
    }

    public void incrementOperations() {
        this.operationsCount++;
    }

    public void incrementOperations(int n) {
        this.operationsCount += n;
    }

    public int getOperationsCount() {
        return this.operationsCount;
    }

    public double getExecutionTimeMs() {
        long duration = isRunning ? (System.nanoTime() - startTime) : (endTime - startTime);
        return duration / 1_000_000.0;
    }

    public long getExecutionTimeNs() {
        return isRunning ? (System.nanoTime() - startTime) : (endTime - startTime);
    }

    public void reset() {
        this.operationsCount = 0;
        this.startTime = 0;
        this.endTime = 0;
        this.isRunning = false;
    }

    public String getSummary() {
        return String.format("Operations: %d, Time: %.2f ms",
                operationsCount, getExecutionTimeMs());
    }

    @Override
    public String toString() {
        return getSummary();
    }
}
