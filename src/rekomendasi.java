public class Rekomendasi {
    private static final String KODE_SKRIPSI = "SKR";

    static DaftarInt prasyaratBelumLulus(Kurikulum k, boolean[] lulus, int matkul) {
        boolean[] dikunjungi = new boolean[k.jumlah];
        DaftarInt stack = new DaftarInt();
        DaftarInt hasil = new DaftarInt();
        stack.tambah(matkul);
        dikunjungi[matkul] = true;
        while (!stack.kosong()) {
            int v = stack.pop();
            for (ListBerantai.Node p = k.graf.prasyaratLangsung(v).kepala(); p != null; p = p.next) {
                int u = p.nilai;
                if (lulus[u] || dikunjungi[u]) continue;
                dikunjungi[u] = true;
                hasil.tambah(u);
                stack.tambah(u);
            }
        }
        urutkan(k, hasil);
        return hasil;
    }

    static void urutkan(Kurikulum k, DaftarInt d) {
        for (int i = 1; i < d.ukuran(); i++) {
            int kunci = d.ambil(i);
            int j = i - 1;
            while (j >= 0 && lebihBesar(k, d.ambil(j), kunci)) {
                d.ubah(j + 1, d.ambil(j));
                j--;
            }
            d.ubah(j + 1, kunci);
        }
    }

    private static boolean lebihBesar(Kurikulum k, int a, int b) {
        if (k.mk[a].semester != k.mk[b].semester) return k.mk[a].semester > k.mk[b].semester;
        return a > b;
    }

    static DaftarInt mkWajibBelumLulus(Kurikulum k, boolean[] lulus) {
        DaftarInt hasil = new DaftarInt();
        for (int i = 0; i < k.jumlah; i++) {
            if (!k.mk[i].kode.equals(KODE_SKRIPSI) && !lulus[i]) hasil.tambah(i);
        }
        return hasil;
    }

    private static String gabung(Kurikulum k, DaftarInt d) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < d.ukuran(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(k.mk[d.ambil(i)].kode);
        }
        return sb.toString();
    }

    public static void tampilkan(Kurikulum k, Mahasiswa m) {
        DaftarInt bisa = new DaftarInt();
        DaftarInt tidakBisa = new DaftarInt();
        String[] alasan = new String[k.jumlah];

        for (int i = 0; i < k.jumlah; i++) {
            if (m.lulus[i] || !k.mk[i].ditawarkan(m.semesterBerikutnya)) continue;

            if (k.mk[i].kode.equals(KODE_SKRIPSI)) {
                DaftarInt kurang = mkWajibBelumLulus(k, m.lulus);
                if (kurang.kosong()) bisa.tambah(i);
                else {
                    tidakBisa.tambah(i);
                    alasan[i] = "belum lulus semua MK wajib (kurang " + kurang.ukuran() + " MK: " + gabung(k, kurang) + ")";
                }
            } else {
                DaftarInt kurang = prasyaratBelumLulus(k, m.lulus, i);
                if (kurang.kosong()) bisa.tambah(i);
                else {
                    tidakBisa.tambah(i);
                    alasan[i] = "belum lulus " + gabung(k, kurang);
                }
            }
        }

        System.out.println("=== " + m.namaBerkas + " (semester berikutnya: " + m.semesterBerikutnya + ") ===");
        System.out.println("Daftar MK yang bisa diambil:");
        if (bisa.kosong()) System.out.println("(tidak ada)");
        for (int i = 0; i < bisa.ukuran(); i++) {
            Matakuliah c = k.mk[bisa.ambil(i)];
            System.out.println((i + 1) + ". " + c.kode + " " + c.nama + " " + c.sks + " SKS");
        }
        System.out.println("Daftar MK yang belum bisa diambil semester depan:");
        if (tidakBisa.kosong()) System.out.println("(tidak ada)");
        for (int i = 0; i < tidakBisa.ukuran(); i++) {
            int idx = tidakBisa.ambil(i);
            System.out.println(k.mk[idx].kode + ": " + alasan[idx]);
        }
        System.out.println();
    }
}
