// main.java
import java.io.IOException;
import java.util.List;

public class main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("java -cp src main test/<.obj> <maxDepth>");
            System.exit(1);
        }
        
        String filePath = args[0];
        int maxDepth;

        try {
            maxDepth = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return;
        }

        if (maxDepth <= 0) {
            System.err.println("maxDepth: 0--10");
            System.exit(1);
        }
        if (maxDepth > 10) {
            System.err.println("maxDepth: 0--10");
        }

        Statistics statistics;

        // Menyiapkan memproses input
        ObjectParser objectParser = new ObjectParser();
        objectParser.parse(filePath);
        List<Triangle> triangles = objectParser.getTriangles();

        // Menyiapkan OctreeNodes
        Octree octree = new Octree(maxDepth);
        octree.buildBox(triangles);
        octree.buildOctreeNode();

        statistics = octree.getStatistics();
        statistics.startTimer();

        // Membentuk leaf nodes (proses DnC fyi)
        octree.buildLeaf(octree.getOctreeNode(), 0, triangles);
        List<OctreeNode> leaves = octree.getLeaf();

        // ObjectWriter untuk output ditulis hasil DnC
        ObjectWriter objectWriter = new ObjectWriter();
        try {
            String output = objectWriter.write(leaves, filePath, maxDepth);
            statistics.outputPath = output;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        statistics.stopTimer();
        Repoerter repoerter = new Repoerter(statistics);
        repoerter.report();
        System.exit(0);
    }
}
