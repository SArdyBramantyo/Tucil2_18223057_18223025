// Triangle.java
public class Triangle {
    private Vector3D v0;
    private Vector3D v1;
    private Vector3D v2;
    private Vector3D normal;

    public Triangle(Vector3D v0, Vector3D v1, Vector3D v2) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;

        Vector3D edge1 = v1.subVector3d(v0);
        Vector3D edge2 = v2.subVector3d(v0);
        this.normal = (edge1.crossVector3d(edge2)).normalize();
    }

    // Getter
    public Vector3D getV0() {
        return v0;
    }
    public Vector3D getV1() {
        return v1;
    }
    public Vector3D getV2() {
        return v2;
    }

    public Vector3D getNormal() {
        return normal;
    }
}
