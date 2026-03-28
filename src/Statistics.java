// Statistics.java
public class Statistics {
    public final int[] nodeCountPerDepth;
    public final int[] prunedCountPerDepth;
    public final int maxDepth;
    public int totalVoxels;
    public long startTime;
    public long endTime;
    public String outputPath;

    public Statistics(int maxDepth) {
        this.maxDepth = maxDepth;
        this.nodeCountPerDepth = new int[maxDepth + 1];
        this.prunedCountPerDepth = new int[maxDepth + 1];
        this.totalVoxels = 0;
        this.startTime = 0;
        this.endTime = 0;
        this.outputPath = null;
    }

    // Getter
    public long getProgramTime() {
        return (this.endTime-this.startTime);
    }

    // Setter
    public void startTimer() {
        this.startTime =  System.currentTimeMillis();
    }
    
    public void stopTimer() {
        this.endTime = System.currentTimeMillis();
    }

    public void recordNode(int depth) {
        nodeCountPerDepth[depth]++;
    }

    public void recordPruned(int depth) {
        prunedCountPerDepth[depth]++;
    }

    public void recordVoxel() {
        totalVoxels++;
    }
}
