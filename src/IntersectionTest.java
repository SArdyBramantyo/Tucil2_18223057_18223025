// IntersectionTest.java
import java.util.List;
import java.util.Vector;

public class IntersectionTest {
    private static final double EPSILON = 1e-6;

    // Getter

    // 1. Getter Box (X, Y, Z) -- 3 Sumbu
    public static Vector3D[] getBoxAxes() {
        Vector3D[] boxAxes = {new Vector3D(1, 0, 0),
                              new Vector3D(0, 1, 0),
                              new Vector3D(0, 0, 1)
                            };
        return boxAxes;
    }

    // 2. Getter Triangle Normalized -- 1 Sumbu
    public static Vector3D getTriangleNormalAxis(Triangle triangle) {
        return triangle.getNormal();
    }
    // 3. Getter Cross Product -- 9 Sumbu 
    public static Vector3D[] getCrossAxes(Triangle triangle) {
        Vector3D t_v0 = triangle.getV0();
        Vector3D t_v1 = triangle.getV1();
        Vector3D t_v2 = triangle.getV2();
        Vector3D edge0 = t_v1.subVector3d(t_v0); // A ke B.
        Vector3D edge1 = t_v2.subVector3d(t_v1); // B ke C.
        Vector3D edge2 = t_v0.subVector3d(t_v2); // C ke A.
        
        Vector3D[] edges = {edge0, edge1, edge2};
        Vector3D[] boxAxes = getBoxAxes();
        Vector3D[] crossAxes = new Vector3D[9];

        int idx = 0;
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < boxAxes.length; j++) {
                crossAxes[idx++] = edges[i].crossVector3d(boxAxes[j]);
            }
        }
        return crossAxes;
    }

    // Memproyeksi setiap sudut untuk menentukan bayangan kubus (melihat Min-Max)
    public static double[] projectBox(Vector3D axis, Vector3D[] corners) {
        double min = axis.dotVector3D(corners[0]);
        double max = min;

        for (int i = 1; i < 8; i++) {
            double val = axis.dotVector3D(corners[i]);
            if (val<min) {
                min = val;
            }
            if (val>max) {
                max = val;
            }
        }
        double[] result = {min, max};
        return result;
    }

    // Memproyeksikan setiap sudut segitiga untuk menentukan bayangannya (melihat Min-Max)
    public static double[] projectTriangle(Vector3D axis, Vector3D[] vertices) {
        double min = axis.dotVector3D(vertices[0]);
        double max = min;
        for (int i = 1; i < 3; i++){
            double val = axis.dotVector3D(vertices[i]);
            if (val<min) {
                min = val;
            }
            if (val>max) {
                max = val;
            }
        }
        double[] result = {min, max};
        return result;
    }

    // Mengecek apakah overlaping
    public static boolean overlaps(double[] boxProject, double[] triangleProject) {
        boolean boxOverlapsTriangle;

        // Kubus akan overlap jika bayangannya sama atau lebih besar dibanding Segitiga.
        if ( (boxProject[1]+EPSILON) >= triangleProject[0]) {
            boxOverlapsTriangle = true;
        } else {
            boxOverlapsTriangle = false;
        }

        // Segitiga akan overlap bayangannya sama atau lebih besar dibanding Kubus
        boolean triangleOverlapsBox;
        if ( (triangleProject[1]+EPSILON) >= boxProject[0]) {
            triangleOverlapsBox = true;
        } else {
            triangleOverlapsBox = false;
        }

        // Overlaps hanya berlaku kalau keduanya true saja
        if ( (boxOverlapsTriangle == true) && (triangleOverlapsBox == true) ) {
            return true;
        } else {
            return false;
        }
    }

    // Separating Axis Thoerem, untuk mengecek apakah ada yang beririsan
    public static boolean intersects(OctreeNode octreeNode, Triangle triangle) {
        Vector3D[] corners = octreeNode.get8Corners();

        Vector3D t_v0 = triangle.getV0();
        Vector3D t_v1 = triangle.getV1();
        Vector3D t_v2 = triangle.getV2();
        Vector3D[] vertices = new Vector3D[]{t_v0, t_v1, t_v2};

        // Cek 3 sumbu koordinat
        for (Vector3D boxAxes : getBoxAxes()) { 
            double[] projectBoxAxes = projectBox(boxAxes, corners);
            double[] projectTriangleAxes = projectTriangle(boxAxes, vertices);

            boolean isOverlapsBoxAxes = overlaps(projectBoxAxes, projectTriangleAxes);
            if (isOverlapsBoxAxes == false) {
                return false;
            }
        }
        // Cek normal triangle
        Vector3D triangleNormalAxis = getTriangleNormalAxis(triangle);
        double[] projectBoxNormalTriangle = projectBox(triangleNormalAxis, corners);
        double[] projectTriangleNormalTriangle = projectTriangle(triangleNormalAxis, vertices);

        boolean isOverlapsNormalTriangle = overlaps(projectBoxNormalTriangle, projectTriangleNormalTriangle);
        if (isOverlapsNormalTriangle == false) {
            return false;
        }
        
        // Cek 9 Cross Product
        for (Vector3D crossAxes : getCrossAxes(triangle)) {
            if (crossAxes.length() < 1e-4) { // Don't over straight banget
                continue;
            }
            double[] projectBoxCrossAxes = projectBox(crossAxes, corners);
            double[] projectTriangleCrossAxes = projectTriangle(crossAxes, vertices);
            
            boolean isOverlapsCrossAxes = overlaps(projectBoxCrossAxes, projectTriangleCrossAxes);
            if (isOverlapsCrossAxes == false) {
                return false;
            }
        }

        return true;
    }

    public static boolean anyIntersects(OctreeNode octreeNode, List<Triangle> triangles) {
        for (Triangle triangle : triangles) {
            if (intersects(octreeNode, triangle)) {
                return true;
            }
        }
        return false;
    }
}
