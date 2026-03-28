// ObjectParser.java
import java.io.*;
import java.util.*;

public class ObjectParser {
    private List<Vector3D> vertices = new ArrayList<>();
    private List<Triangle> triangles = new ArrayList<>();
    private int lineNumber;

    // Getter
    public List<Vector3D> getVertices() {
        return vertices;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public boolean validateFile(String filePath) {
        // 1. Null Validation
        if (filePath == null || filePath.trim().isEmpty()) {
            System.err.println("Path file NULL!");
            return false;
        }

        // 2. .obj Validation
        if (!filePath.toLowerCase().endsWith(".obj")) {
            String ext;
            if (filePath.contains(".")) {
                ext = filePath.substring(filePath.lastIndexOf('.'));
            } else {
                ext = "Tidak ada file!";
            }
            System.err.println("Hanya .obj yang valid. Input: " + ext);
            return false;
        }

        File file = new File(filePath);
        
        // 3. File Validation
        if (!file.exists()) {
            System.err.println("Tidak ada file di " + filePath);
            return false;
        }

        if (!file.isFile()) {
            System.err.println("Berikan File, bukan Folder");
            return false;
        }

        if (file.length() == 0) {
            System.err.println("File kosong!");
            return false;
        }

        return true;
    }

    public void parse(String filePath) {
        // File Validation
        boolean validateFile = validateFile(filePath);

        if (validateFile == false) {
            return;
        }

        vertices.clear();
        triangles.clear();
        lineNumber = 0;
        List<String> faceLines = new ArrayList<>();

        // Parsing .obj file
        try (FileReader fr = new FileReader(filePath); BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                // Mengecek jenis informasi
                if (line.startsWith("v ")) { 
                    parseVertex(line); 
                } else if (line.startsWith("f ")){
                    faceLines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("File gagal, Baris: " + lineNumber + ": " + e.getMessage());
        }

        for (String faceLine : faceLines) {
            Triangle triangle = parseFace(faceLine);
            if (triangle != null) {
                triangles.add(triangle);
            }
        }

        if (vertices.size() < 3) {
            throw new IllegalStateException("Minimal 3 vertex!");
        }
        if (triangles.isEmpty()) {
            throw new IllegalStateException("Tidak ada segitiga");
        }
        
    } 

    private void parseVertex(String line) {
        String[] lineSplits = line.split("\\s+");

        if (lineSplits.length != 4) { // Diasumsikan input akan hanya ada 4 kolom (v 1 2 3)
            throw new IllegalArgumentException("Format: v v_X v_Y v_Z");
        }

        try {
            // Format input dapat diasumsikan ([Type] X Y Z)
            double coords_X = Double.parseDouble(lineSplits[1]);
            double coords_Y = Double.parseDouble(lineSplits[2]);
            double coords_Z = Double.parseDouble(lineSplits[3]);

            if (Double.isNaN(coords_X) || Double.isNaN(coords_Y) || Double.isNaN(coords_Z) || Double.isInfinite(coords_X) || Double.isInfinite(coords_Y) || Double.isInfinite(coords_Z)) {
                throw new IllegalArgumentException("Informasi tidak valid " + lineNumber);
            }
            vertices.add(new Vector3D(coords_X, coords_Y, coords_Z));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private Triangle parseFace(String line) {
        String[] lineSplits = line.split("\\s+");

        if (lineSplits.length != 4) { // Diasumsikan input akan hanya ada 4 kolom (f 1 2 3)
            throw new IllegalArgumentException("Format: f f_X f_Y f_Z");
        }

        int[] indices = new int[3];
        for (int i = 0; i < 3 ; i++) {
            String lineSplit = lineSplits[i + 1];
            String indexString = lineSplit.split("/")[0];
            try {
                indices[i] = Integer.parseInt(indexString)- 1;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e.getMessage());
            }

            
            if (indices[i] < 0 || (indices[i] >= vertices.size()) ) {
                throw new IllegalArgumentException();
            }
        }

        Vector3D vector_A = vertices.get(indices[0]);
        Vector3D vector_B = vertices.get(indices[1]);
        Vector3D vector_C = vertices.get(indices[2]);
        Triangle triangle = new Triangle(vector_A, vector_B, vector_C);
        if (triangle.getNormal().length() < 1e-10) {
            System.err.println("Segitiga degeneratif! Baris: " + lineNumber);
            return null;
        }

        return triangle;
    }
}
