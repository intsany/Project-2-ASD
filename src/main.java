import java.io.IOException;

public class main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Pemakaian: java Main <matakuliah.csv> <prasyarat.csv> <mhs_xx.txt> [mhs_yy.txt ...]");
            System.exit(1);
        }
        try {
            kurikulum k = new kurikulum();
            k.muatMatakuliah(args[0]);
            k.muatPrasyarat(args[1]);
            for (int i = 2; i < args.length; i++) {
                rekomendasi.tampilkan(k, mahasiswa.baca(args[i], k));
            }
        } catch (IOException e) {
            System.err.println("Gagal: " + e.getMessage());
            System.exit(1);
        }
    }
}
