public class listBerantai {
    public static class Node {
        public final int nilai;
        public Node next;
        Node(int nilai) { this.nilai = nilai; }
    }

    private Node kepala, ekor;
    private int ukuran = 0;

    public void tambah(int nilai) {
        Node baru = new Node(nilai);
        if (kepala == null) kepala = ekor = baru;
        else { ekor.next = baru; ekor = baru; }
        ukuran++;
    }

    public Node kepala() { return kepala; }
    public int ukuran() { return ukuran; }
}