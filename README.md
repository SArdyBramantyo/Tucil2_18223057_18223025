# Voxelization of an Image (Divide & Conquer)

Voxelization merupakan sebuah problem yang menanyakan bagaimana mengubah sebuah objek 3D yang diubah menjadi objek 3D juga dengan komposisinya balok-balok seperti di Minecraft. Programnya tersendiri akan menerima input sebuah file .obj yang akan membaca informasi terkait objek 3 dimensi tersebut. Dengan menggunakan struktur data Octree, pendekatan kami akan menggunakan algoritma Divide and Conquer, yang dimana akan membaginya menjadi 8 bagian hingga sampai ke leaf node. Proses pengubahaan ke kotak-kotak akan menggunakan Separating Axis Theorem yang dapat mendeteksi irisan antara kubus dengan segitiga yang menandakan posisi dan ukuran yang sesuai.

## Struktur File
```
Tucil2_18223057_18223025/
├── doc/
│   ├── Spesifikasi Tugas Kecil 2 - IF2211 - 2025_2026.pdf
│   ├── Tucil2_K1_18223057_18223025.pdf
|
├── src/
│   ├── IntersectionTest.java
│   ├── main.java
│   ├── ObjectParser.java
│   ├── ObjectWriter.java
│   ├── Octree.java
│   ├── OctreeNode.java
│   ├── Repoerter.java
│   ├── Statistics.java
│   ├── Triangle.java
│   ├── Vector3D.java
│   ├── VoxelGenerator.java
│
└── test/
│   ├── cow.obj
│   ├── line.obj
│   ├── pumpkin.obj
│   ├── teapot.obj
│   ├── teddybear.obj
│
└── README.md
```

## Installation
### Windows
Cukup punya Java JDK (8+) akan dapat compile dan menjalankan program.
```
https://www.oracle.com/java/technologies/downloads/
```
## Compilation
Untuk melakukan kompilasi dari program kami, lakukan ini saja:
```
cd Tucil2_18223057_18223025
javac -d out src/*.java
```

## Input
Untuk dapat menjalankan sebuah test file, gunakan format "java -cp out main test/<.obj> <maxDepth>
<.obj>: Nama file input dari user yang ada pada /test.
<maxDepth>: Depth terdalam dari user yang diingkan (range 1—10).
```
java -cp out main test/teapot.obj 7
```

## Output
Output akan ditulis ke sebuah .obj file baru dengan format: <namaFile>-voxelized.obj yang akan ada pada file /test. Untuk dapat melakukan rendering, karena kami tidak mengerjakan bonus juga, dapat menggunakan website berikut: https://3dviewer.net/index.html.

## Author
| Name | NIM | Kelas |
|------|------|------|
| Stanislaus Ardy Bramantyo | 18223057 | K01 |
| Muhammad Azzam Robbani | 18223025 | K01 |

---
