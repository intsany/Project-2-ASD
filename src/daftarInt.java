public class daftarInt {
    private int[] data = new int[8];
    private int n = 0;

    public void tambah(int v) {
        if (n == data.length) {                    // penuh -> kapasitas digandakan (amortized O(1))
            int[] baru = new int[data.length * 2];
            for (int i = 0; i < n; i++) baru[i] = data[i];
            data = baru;
        }
        data[n++] = v;
    }

    public int ambil(int i) { return data[i]; }
    public void ubah(int i, int v) { data[i] = v; }
    public int ukuran() { return n; }
    public boolean kosong() { return n == 0; }

    public int pop() { return data[--n]; }
}
