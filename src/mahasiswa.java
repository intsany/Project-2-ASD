import java.io.IOException;

public class Mahasiswa {
    public String namaBerkas;
    public String semesterBerikutnya;   // "Ganjil" atau "Genap"
    public boolean[] lulus;             // lulus[i] = true bila MK dengan indeks i sudah lulus

    public static Mahasiswa baca(String path, Kurikulum k) throws IOException {
        Mahasiswa m = new Mahasiswa();
        m.namaBerkas = path;
        m.lulus = new boolean[k.jumlah];
        String[] baris = Berkas.bacaBaris(path);
        for (int i = 0; i < baris.length; i++) {
            String b = baris[i].trim();
            if (b.isEmpty()) continue;
            int sama = b.indexOf('=');
            if (sama < 0) continue;
            String kunci = b.substring(0, sama).trim();
            String nilai = b.substring(sama + 1).trim();
            if (kunci.equals("semester_berikutnya")) {
                if (nilai.equalsIgnoreCase("ganjil")) m.semesterBerikutnya = "Ganjil";
                else if (nilai.equalsIgnoreCase("genap")) m.semesterBerikutnya = "Genap";
                else throw new IOException("Nilai semester_berikutnya harus ganjil/genap: " + nilai);
            } else if (kunci.equals("lulus") && !nilai.isEmpty()) {
                String[] kode = nilai.split(",");
                for (int j = 0; j < kode.length; j++) {
                    String c = kode[j].trim();
                    if (c.isEmpty()) continue;
                    int idx = k.indeks.ambil(c);
                    if (idx < 0) System.err.println("Peringatan: kode MK tidak dikenal diabaikan: " + c);
                    else m.lulus[idx] = true;
                }
            }
        }
        if (m.semesterBerikutnya == null) throw new IOException("semester_berikutnya tidak ditemukan di " + path);
        return m;
    }
}
