// VoxelGenerator.java
import java.util.ArrayList;
import java.util.List;

public class VoxelGenerator {
    private List<double[]> verticesList = new ArrayList<>();
    private List<int[]> facesList = new ArrayList<>();
    private int count = 0;

    // Getter
    public List<double[]> getVerticesList() {
        return verticesList;
    }

    public List<int[]> getFacesList() {
        return facesList;
    }

    private void generateVertices(Vector3D[] corners) {
        for (int i = 0; i < 8; i++) {
            double[] newVertice = new double[] {corners[i].getX(), corners[i].getY(), corners[i].getZ()};
            verticesList.add(newVertice);
        }
    }

    // Membuat 12 Segitiga (2 Segitiga per Sisi dengan 6 total sisi di sebuah kubus)
    private void generateFaces(int offset){
        int[][] faces = { // List of faces, untuk tau segitiga yang mana aja
            // Kiri (X-)
            {0, 4, 2},
            {2, 4, 6},

            // Kanan (X+)
            {1, 3, 5},
            {3, 7, 5},

            // Bawah (Y-)
            {0, 1, 4},
            {1, 5, 4},

            // Atas (Y+)
            {2, 6, 3},
            {3, 6, 7},

            // Belakang (Z-)
            {0, 2, 1},
            {1, 2, 3},

            // Depan (Z+)
            {4, 5, 7},
            {4, 7, 6}
        };

        for (int[] face : faces) {
            int[] newFace = new int[]{(face[0]+offset + 1), (face[1]+offset +1), (face[2]+offset + 1)};
            facesList.add(newFace);
        }
    }

    public void generate(List<OctreeNode> leaves) {
        verticesList.clear();
        facesList.clear();
        count = 0;

        for (OctreeNode leaf : leaves) { // Ngebuat kubus untuk setiap leaf node.
            int offset = count;
            Vector3D[] corners = leaf.get8Corners();
            generateVertices(corners);
            generateFaces(offset);            
            
            count = count + 8;
        }
    }
    
}
