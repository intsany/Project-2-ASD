public class grafPrasyarat {
    private final int jumlahSimpul;
    private final listBerantai[] membuka;
    private final listBerantai[] syarat;

    public grafPrasyarat(int jumlahSimpul) {
        this.jumlahSimpul = jumlahSimpul;
        membuka = new listBerantai[jumlahSimpul];
        syarat = new listBerantai[jumlahSimpul];
        for (int i = 0; i < jumlahSimpul; i++) {
            membuka[i] = new listBerantai();
            syarat[i] = new listBerantai();
        }
    }

    public void tambahSisi(int prasyarat, int matkul) {
        membuka[prasyarat].tambah(matkul);
        syarat[matkul].tambah(prasyarat);
    }

    public listBerantai prasyaratLangsung(int matkul) { return syarat[matkul]; }
    public listBerantai membuka(int matkul) { return membuka[matkul]; }
    public int jumlahSimpul() { return jumlahSimpul; }

    public boolean adalahDAG() {
        int[] derajatMasuk = new int[jumlahSimpul];
        for (int v = 0; v < jumlahSimpul; v++) derajatMasuk[v] = syarat[v].ukuran();

        int[] antrian = new int[jumlahSimpul];        
        int depan = 0, belakang = 0;
        for (int v = 0; v < jumlahSimpul; v++) if (derajatMasuk[v] == 0) antrian[belakang++] = v;

        while (depan < belakang) {
            int u = antrian[depan++];
            for (listBerantai.Node p = membuka[u].kepala(); p != null; p = p.next) {
                if (--derajatMasuk[p.nilai] == 0) antrian[belakang++] = p.nilai;
            }
        }
        return belakang == jumlahSimpul;            
    }
}
