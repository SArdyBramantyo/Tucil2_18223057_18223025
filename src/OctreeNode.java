// OctreeNode.java
public class OctreeNode {
    private Vector3D center;
    private double halfSize;
    private OctreeNode[] childrenNodes;
    private boolean isLeaf;
    private boolean isSurface;

    public OctreeNode(Vector3D center, double halfSize) {
        this.center = center;
        this.halfSize = halfSize;
        this.childrenNodes = new OctreeNode[8];
        this.isLeaf = false;
        this.isSurface = false;
    }

    // Getter
    public Vector3D getCenter() {
        return center;
    }   
    public double getHalfSize() {
        return halfSize;
    }
    public OctreeNode[] getChildren() {
        return childrenNodes;
    }
    public OctreeNode getChild(int index) {
        if (index >= 0 && index <= 7) {
            return this.childrenNodes[index];
        } else {
            throw new IllegalArgumentException("Index: 0--7. Current index: " + index);
        }
    }

    public boolean isLeaf() {
        return isLeaf;
    }
    public boolean isSurface() {
        return isSurface;
    }

    // Setter
    public void setChild (int index, OctreeNode node) {
        if (index < 0 || index > 7) {
            throw new IllegalArgumentException("Index: 0--7. Current index: " + index);
        }
        if (this.childrenNodes[index] != null) {
            throw new IllegalArgumentException("Leaf node ke-"+ index +" sudah ada!");
        }
        this.childrenNodes[index] = node;
    }

    public void setLeaf(boolean value) {
        this.isLeaf = value;
    }

    public void setSurface(boolean value) {
        this.isSurface = value;
    }


    // Operations

    // Center untuk patokan perhitunan corners
    public Vector3D getChildCenter(int octant) {
        double offset = this.halfSize/2.0;

        // X coords
        double cx = this.center.getX();
        if ((octant & 1) == 0) {
            cx -= offset;
        } else {
            cx += offset; 
        }

        // Y coords
        double cy =this.center.getY();
        if ((octant & 2) == 0) {
            cy -= offset;
        } else {
            cy += offset;
        }

        // Z coords
        double cz = this.center.getZ();
        if ((octant & 4) == 0) {
            cz -= offset;
        } else {
            cz += offset;
        }

        Vector3D result = new Vector3D(cx, cy, cz);
        return result;
    }

    // Half Size sebagai ukuran menuju corner dari center
    public double getChildHalfSize() {
        double result = this.halfSize/2.0;
        return result;
    }

    // Menghitung koordinat setiap corner
    public Vector3D[] get8Corners() {
        Vector3D[] corners = new Vector3D[8];

        // Menghitung koordinat setiap corner dari kubus.
        for (int i = 0; i < 8; i++) { 
            double cx = this.center.getX();
            if ((i & 1) == 0) {
                cx -= this.halfSize; // left side
            } else {
                cx += this.halfSize; // right side
            }

            double cy = this.center.getY(); 
            if ((i & 2) == 0) {
                cy -= this.halfSize; // bottom side
            } else {
                cy += this.halfSize; // top side
            }

            
            double cz = this.center.getZ(); 
            if ((i & 4) == 0){
                cz -= this.halfSize; // back side
            } else {
                cz += this.halfSize; // front side
            }

            Vector3D corner = new Vector3D(cx, cy, cz); 
            corners[i] = corner;
        }

        return corners;
    }
}
