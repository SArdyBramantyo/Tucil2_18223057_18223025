// Reporter.java

public class Repoerter {
    private final Statistics statistics;

    public Repoerter(Statistics statistics) {
        this.statistics = statistics;
    }
    
    private void printSeparator() {
        System.out.println("====================================");
    }

    private void printVoxel() {
        int voxels = statistics.totalVoxels;
        int vertices = voxels * 8;
        int faces = voxels * 12;
        System.out.printf("Voxel: %d%n", voxels);
        System.out.printf("Vertices: %d%n", vertices);
        System.out.printf("Faces: %d%n", faces);
    }

    private void printNode() {
        System.out.println("Node Octree");
        for (int i = 0; i < statistics.maxDepth; i++) {
            System.out.println((i+1) + " : " + statistics.nodeCountPerDepth[i+1]);
        }
    }

    private void printPrune() {
        System.out.println("Pruned Octree");
        for (int i = 0; i < statistics.maxDepth; i++) {
            System.out.println((i+1) + " : " + statistics.prunedCountPerDepth[i+1]);
        }
    }

    private void printTime() {
        System.out.printf("Start: %.3f | End: %.3f%n", statistics.startTime/1000.0, statistics.endTime/1000.0);        
        System.out.println("Waktu Eksekusi: " + statistics.getProgramTime()/1000.0);
    }

    public void report() {
        printSeparator();
        printVoxel();
        printSeparator();
        printNode();
        printSeparator();
        printPrune();
        printSeparator();
        printTime();
        printSeparator();
        System.out.println("Selesai reportnya");
    }
}
