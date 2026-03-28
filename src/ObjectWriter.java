// ObjectWriter.java
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ObjectWriter {
    private int[][] faces = {
        // Kiri (X-)
        {0, 4, 6},  {0, 6, 2},  
        // Kanan (X+)
        {1, 3, 7},{1, 7, 5},  

        // Bawah (Y-)
        {0, 1, 5}, {0, 5, 4},  

        // Atas (Y+) 
        {2, 6, 7}, {2, 7, 3},

        // Belakang (Z-)
        {0, 2, 3},{0, 3, 1},

        // Depan (Z+)
        {4, 5, 7},{4, 7, 6}   
    };

    private void writeVertices(BufferedWriter bw, OctreeNode node) throws IOException {
        Vector3D[] corners =  node.get8Corners();
        for (int i = 0; i < 8; i++) {
            double v_X =  corners[i].getX();
            double v_Y = corners[i].getY();
            double v_Z = corners[i].getZ();

            bw.write(String.format("v %.6f %.6f %.6f", v_X, v_Y, v_Z));
            bw.newLine();
        }
    }

    private void writeFaces(BufferedWriter bw, int startVertexIndex) throws IOException {
        for (int[] face : faces) {
            int f_0 = face[0] + startVertexIndex;
            int f_1 =  face[1] + startVertexIndex;
            int f_2 =  face[2] + startVertexIndex;
            bw.write(String.format("f %d %d %d", f_0, f_1, f_2));
            bw.newLine();
        }
    }

    public String write(List<OctreeNode> leafNodes, String inputPath, int maxDepth) throws IOException {
        String outputPath = generateOutputPath(inputPath);
        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();
        
        if (parentDir != null && parentDir.exists() == false) {
            if (parentDir.mkdir() == false) {
                throw new IOException();
            }
        }

        try (FileWriter fw = new FileWriter(outputFile); BufferedWriter bw = new BufferedWriter(fw)) {
            for (OctreeNode leafNode : leafNodes) {
                writeVertices(bw, leafNode);
            }

            for (int i = 0; i < leafNodes.size(); i++) {
                writeFaces(bw, (i * 8) + 1);
            }
        }
        return outputPath;
    }

    // Penulisan path
    public String generateOutputPath(String inputPath) { 
        File file = new File(inputPath);
        String name = file.getName();

        int dotIndex = name.lastIndexOf('.');
        String base;
        if (dotIndex != -1) {
            base = name.substring(0, dotIndex);
        } else {
            base = name;
        }
        
        String parent = file.getParent();
        // output file format <file name>-voxelized.obj
        if (parent != null) {
            return parent + File.separator + base + "-voxelized.obj";
        } else {
            return base + "-voxelized.obj";
        }
    }
}
