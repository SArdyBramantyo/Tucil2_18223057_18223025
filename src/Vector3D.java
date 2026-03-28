// Vector3D.java
public class Vector3D {
    private double x;
    private double y;
    private double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Getter
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    // Operations
    public Vector3D addVector3d(Vector3D object) { 
        //                                      X                    Y                    Z
        Vector3D result = new Vector3D( (this.x + object.x), (this.y + object.y), (this.z + object.z) ); // Penamahan Matrix
        return result;
    }

    public Vector3D subVector3d(Vector3D object) {
        //                                      X                    Y                    Z
        Vector3D result = new Vector3D( (this.x - object.x), (this.y - object.y), (this.z - object.z) ); // Pengurangan Matrix
        return result;
    }

    public Vector3D scalarVector3D (double num) {
        //                                   X               Y               Z
        Vector3D result = new Vector3D( (num * this.x), (num * this.y), (num * this.z) ); // Scalar Matrix
        return result;
    }

    public Vector3D crossVector3d (Vector3D object) {
        /*
        |  i   j   k  |
        | a1  a2  a3  | => a x b = <(a2*b3 - a3*b2), (a3*b1 - a1*b3), (a1*b2 - a2*b1)>
        | b1  b2  b3  |
        */
        Vector3D result = new Vector3D( (this.y * object.z  -  this.z * object.y), (this.z * object.x  -  this.x * object.z), (this.x * object.y  -  this.y * object.x));
        return result;
    }

    public double dotVector3D (Vector3D object) {
        /*
        a = [a1, a2, b3]
                          ==> a • b = (a1 * b1) + (a2 * b2) + (a3 * b3)
        b = [b1, b2, b3]
        */
        double result =  (this.x * object.x) + (this.y * object.y) + (this.z * object.z);
        return result;
    }

    public double length() {
        // Length     =      sqrt(         x^2          +           y^2         +           z^2          )
        double result = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
        return result;
    }

    // Normalization Matrix
    public Vector3D normalize() {
        double length = length();
        double normalized_X;
        double normalized_Y;
        double normalized_Z;

        if (length > 0) { // Pembagian untuk Sum(X, Y, Z) = 1
            normalized_X = x/length;
            normalized_Y = y/length;
            normalized_Z = z/length;
        } else {
            normalized_X = 0;
            normalized_Y = 0;
            normalized_Z = 0;
        }

        Vector3D result = new Vector3D(normalized_X, normalized_Y, normalized_Z);
        return result;  
    }
}
