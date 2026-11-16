public class GrafPrasyarat {
    private final int jumlahSimpul;
    private final ListBerantai[] membuka;
    private final ListBerantai[] syarat;

    public GrafPrasyarat(int jumlahSimpul) {
        this.jumlahSimpul = jumlahSimpul;
        membuka = new ListBerantai[jumlahSimpul];
        syarat = new ListBerantai[jumlahSimpul];
        for (int i = 0; i < jumlahSimpul; i++) {
            membuka[i] = new ListBerantai();
            syarat[i] = new ListBerantai();
        }
    }

    public void tambahSisi(int prasyarat, int matkul) {
        membuka[prasyarat].tambah(matkul);
        syarat[matkul].tambah(prasyarat);
    }

    public ListBerantai prasyaratLangsung(int matkul) { return syarat[matkul]; }
    public ListBerantai membuka(int matkul) { return membuka[matkul]; }
    public int jumlahSimpul() { return jumlahSimpul; }

    /** Validasi data: graf prasyarat harus DAG (tanpa siklus). Memakai algoritma Kahn (topological sort). */
    public boolean adalahDAG() {
        int[] derajatMasuk = new int[jumlahSimpul];
        for (int v = 0; v < jumlahSimpul; v++) derajatMasuk[v] = syarat[v].ukuran();

        int[] antrian = new int[jumlahSimpul];        // queue berbasis array
        int depan = 0, belakang = 0;
        for (int v = 0; v < jumlahSimpul; v++) if (derajatMasuk[v] == 0) antrian[belakang++] = v;

        while (depan < belakang) {
            int u = antrian[depan++];
            for (ListBerantai.Node p = membuka[u].kepala(); p != null; p = p.next) {
                if (--derajatMasuk[p.nilai] == 0) antrian[belakang++] = p.nilai;
            }
        }
        return belakang == jumlahSimpul;              // semua simpul terproses <=> tidak ada siklus
    }
}
