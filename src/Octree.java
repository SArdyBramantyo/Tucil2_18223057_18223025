// Octree.java
import java.util.ArrayList;
import java.util.List;

public class Octree {
    private OctreeNode octreeNode;
    private int maxDepth;
    private Vector3D center;
    private double halfSize;
    private List<OctreeNode> leaf;
    private Statistics statistics;
    
    public Octree(int maxDepth) {
        this.maxDepth = maxDepth;
        this.leaf = new ArrayList<>();
    }

    // Getter
    public OctreeNode getOctreeNode() {
        return this.octreeNode;
    }

    public List<OctreeNode> getLeaf() {
        return this.leaf;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void buildBox(List<Triangle> triangles) {
        // Instansiasi setiap variabel dengan MAX_VALUE biar bisa menerima setiap input di iterasi pertama.
        double xMin = Double.MAX_VALUE;
        double xMax = -Double.MAX_VALUE;
        double yMin = Double.MAX_VALUE;
        double yMax = - Double.MAX_VALUE;
        double zMin = Double.MAX_VALUE;
        double zMax = - Double.MAX_VALUE;

        // Menentukan kubus terkecil yang dapat membungkus seluruh objek.
        for (Triangle triangle : triangles) {
            Vector3D[] triangleVectors = new Vector3D[]{triangle.getV0(), triangle.getV1(), triangle.getV2()};
            
            for (Vector3D triangleVector : triangleVectors) {
                double xVal = triangleVector.getX();
                double yVal = triangleVector.getY();
                double zVal = triangleVector.getZ();

                // Min harus lebih kecil, Max harus lebih besar 
                if (xVal < xMin) { 
                    xMin = xVal;
                } 
                if (xVal > xMax) {
                    xMax = xVal;
                }
                if (yVal < yMin) {
                    yMin = yVal;
                }
                if (yVal > yMax) {
                    yMax = yVal;
                }
                if (zVal < zMin) {
                    zMin = zVal;
                }
                if (zVal > zMax) {
                    zMax = zVal;
                }
            }
        }
        // Menyiapkan variabel untuk kubus baru dengan Center & HalfSize!
        double xCenter = (xMin + xMax)/2.0;
        double yCenter = (yMin + yMax)/2.0;
        double zCenter = (zMin + zMax)/2.0;
        this.center = new Vector3D(xCenter, yCenter, zCenter);

        double xRange = xMax-xMin;
        double yRange = yMax-yMin;
        double zRange = zMax-zMin;
        double maxRange = Math.max(xRange, Math.max(yRange, zRange));
        this.halfSize = (maxRange/2.0) * 1.01;
    }

    // Membuat kubus tersebut
    public void buildOctreeNode() {
        this.octreeNode = new OctreeNode(this.center, this.halfSize);
        this.octreeNode.setSurface(true);
        this.statistics = new Statistics(maxDepth);
    }

    // Untuk efisiensi, akan menggunakan filter saat menyentuh kubus saja (Intersects)
    public List<Triangle> getRelevantTriangles(OctreeNode octreeNode, List<Triangle> triangles) {
        List<Triangle> result = new ArrayList<>();
        for (Triangle triangle : triangles) {
            if (IntersectionTest.intersects(octreeNode, triangle)) {
                result.add(triangle);
            }
        }
        return result;
    }

    // Divide n Conquer
    public void buildLeaf(OctreeNode octreeNode, int depth, List<Triangle> triangles) {
        // IF sudah ujung, END.
        if (depth == maxDepth) {
            octreeNode.setLeaf(true);
            leaf.add(octreeNode);
            statistics.recordNode(depth);
            statistics.recordVoxel();
            return;
        }

        statistics.recordNode(depth);

        // Iterasi untuk Divide n Conquer
        for (int i = 0; i < 8; i++) { 
            Vector3D childeCenter = octreeNode.getChildCenter(i);
            double childHalfSize = octreeNode.getChildHalfSize();
            OctreeNode childNode = new OctreeNode(childeCenter, childHalfSize);

            List<Triangle> relevantTriangles = getRelevantTriangles(childNode, triangles);

            if (relevantTriangles.isEmpty()) {
                statistics.recordPruned(depth+1);
            } else { // Divide kalau ada segitiga yang beririsan dengan i'th child.
                childNode.setSurface(true);
                octreeNode.setChild(i, childNode);
                buildLeaf(childNode, depth+1, relevantTriangles);
            }
        }
    }

    public void collectLeaf(OctreeNode octreeNode, List<OctreeNode> leaf) {
        if (octreeNode.isLeaf()) {
            leaf.add(octreeNode);
            return;
        }
        for (int i=0; i<8; i++) {
            OctreeNode childNode = octreeNode.getChild(i);
            if (childNode != null) {
                collectLeaf(childNode, leaf);
            }
        }
    }
}
