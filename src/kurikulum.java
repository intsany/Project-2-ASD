import java.io.IOException;

public class kurikulum {
    public mataKuliah[] mk;
    public int jumlah;
    public tabelHash indeks;
    public grafPrasyarat graf;

    public void muatMatakuliah(String path) throws IOException {
        String[] baris = Berkas.bacaBaris(path);
        mk = new mataKuliah[baris.length];
        indeks = new tabelHash(64);
        jumlah = 0;
        for (int i = 1; i < baris.length; i++) {
            String b = baris[i].trim();
            if (b.isEmpty()) continue;
            String[] f = b.split(";");
            if (f.length < 5) throw new IOException("Baris matakuliah.csv tidak valid: " + b);
            try {
                mk[jumlah] = new mataKuliah(f[0].trim(), f[1].trim(),
                        Integer.parseInt(f[2].trim()), Integer.parseInt(f[3].trim()), f[4].trim());
            } catch (NumberFormatException e) {
                throw new IOException("SKS/semester bukan angka pada baris: " + b);
            }
            indeks.taruh(mk[jumlah].kode, jumlah);
            jumlah++;
        }
    }

    public void muatPrasyarat(String path) throws IOException {
        String[] baris = Berkas.bacaBaris(path);
        graf = new grafPrasyarat(jumlah);
        for (int i = 1; i < baris.length; i++) {
            String b = baris[i].trim();
            if (b.isEmpty()) continue;
            String[] f = b.split(";");
            if (f.length < 2) throw new IOException("Baris prasyarat.csv tidak valid: " + b);
            int u = indeks.ambil(f[0].trim());
            int v = indeks.ambil(f[1].trim());
            if (u < 0 || v < 0) throw new IOException("Kode MK tidak dikenal pada prasyarat.csv: " + b);
            graf.tambahSisi(u, v);
        }
        if (!graf.adalahDAG()) throw new IOException("Graf prasyarat mengandung siklus, data tidak valid.");
    }
}
