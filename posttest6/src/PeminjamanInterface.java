public interface PeminjamanInterface {
    void ajukanPeminjaman(String kodeRuangan, String tanggal, String waktuMulai, String waktuSelesai);
    void batalkanPeminjaman(String kodePeminjaman);
}