public class TabelHash {
    private static class Entri {
        final String kunci;
        int nilai;
        Entri next;
        Entri(String kunci, int nilai, Entri next) { this.kunci = kunci; this.nilai = nilai; this.next = next; }
    }

    private Entri[] bucket;
    private int ukuran = 0;

    public TabelHash(int kapasitasAwal) {
        bucket = new Entri[kapasitasAwal];
    }

    private static int hash(String kunci, int kapasitas) {
        int h = 0;
        for (int i = 0; i < kunci.length(); i++) h = 31 * h + kunci.charAt(i);   // polynomial rolling hash
        return (h & 0x7fffffff) % kapasitas;
    }

    public void taruh(String kunci, int nilai) {
        int b = hash(kunci, bucket.length);
        for (Entri e = bucket[b]; e != null; e = e.next) {
            if (e.kunci.equals(kunci)) { e.nilai = nilai; return; }
        }
        bucket[b] = new Entri(kunci, nilai, bucket[b]);
        ukuran++;
        if (ukuran > bucket.length * 3 / 4) rehash();    // jaga load factor <= 0.75
    }

    /** @return nilai untuk kunci, atau -1 bila tidak ada. */
    public int ambil(String kunci) {
        int b = hash(kunci, bucket.length);
        for (Entri e = bucket[b]; e != null; e = e.next) {
            if (e.kunci.equals(kunci)) return e.nilai;
        }
        return -1;
    }

    private void rehash() {
        Entri[] lama = bucket;
        bucket = new Entri[lama.length * 2];
        for (int i = 0; i < lama.length; i++) {
            for (Entri e = lama[i]; e != null; e = e.next) {
                int b = hash(e.kunci, bucket.length);
                bucket[b] = new Entri(e.kunci, e.nilai, bucket[b]);
            }
        }
    }
}
