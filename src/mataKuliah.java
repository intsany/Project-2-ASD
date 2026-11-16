public class mataKuliah {
    public final String kode;
    public final String nama;
    public final int sks;
    public final int semester;     
    public final String periode;   // "Ganjil", "Genap", atau "Keduanya"

    public mataKuliah(String kode, String nama, int sks, int semester, String periode) {
        this.kode = kode;
        this.nama = nama;
        this.sks = sks;
        this.semester = semester;
        this.periode = periode;
    }

    public boolean ditawarkan(String semesterBerikutnya) {
        return periode.equalsIgnoreCase("Keduanya") || periode.equalsIgnoreCase(semesterBerikutnya);
    }
}
