import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/** Utilitas pembacaan berkas teks per baris. */
public class Berkas {
    public static String[] bacaBaris(String path) throws IOException {
        String[] hasil = new String[16];
        int n = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String baris;
            while ((baris = br.readLine()) != null) {
                if (n == 0 && baris.length() > 0 && baris.charAt(0) == '\uFEFF') baris = baris.substring(1); // buang BOM
                if (n == hasil.length) {
                    String[] baru = new String[n * 2];
                    for (int i = 0; i < n; i++) baru[i] = hasil[i];
                    hasil = baru;
                }
                hasil[n++] = baris;
            }
        }
        String[] pas = new String[n];
        for (int i = 0; i < n; i++) pas[i] = hasil[i];
        return pas;
    }
}
