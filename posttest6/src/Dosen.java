public class Dosen extends User implements PeminjamanInterface {
    private String departemen;
    private static int totalPeminjaman = 0;

    public Dosen(String nip, String password, String nama, String email, String departemen) {
        super(nip, password, nama, email);
        this.departemen = departemen;
    }

    public String getDepartemen() {
        return departemen;
    }

    public void setDepartemen(String departemen) {
        this.departemen = departemen;
    }

    public static int getTotalPeminjaman() {
        return totalPeminjaman;
    }

    @Override
    public void ajukanPeminjaman(String kodeRuangan, String tanggal, String waktuMulai, String waktuSelesai) {
        try {
            if (kodeRuangan == null || kodeRuangan.isEmpty()) {
                throw new IllegalArgumentException("Kode ruangan tidak boleh kosong");
            }
            
            totalPeminjaman++;
            System.out.println("\n\nDosen " + nama + " berhasil mengajukan peminjaman ruangan!!");
            System.out.println("Ruangan : " + kodeRuangan);
            System.out.println("Tanggal : " + tanggal);
            System.out.println("Waktu   : " + waktuMulai + " - " + waktuSelesai);
            System.out.println("\nTotal Peminjaman: " + totalPeminjaman);
            System.out.print("\nHarap ruangan digunakan dengan baik.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error dalam pengajuan peminjaman: " + e.getMessage());
        } finally {
            System.out.print("\nTekan Enter untuk kembali...");
            scanner.nextLine();
        }
    }

    @Override
        public void tampilkanInfo() {
        System.out.println("\nSelamat datang, Dosen " + getNama() + "!");
        System.out.println("======================================");
        System.out.println("NIP        : " + getNimNip());
        System.out.println("Email      : " + getEmail());
        System.out.println("Departemen : " + departemen);
        System.out.println("======================================\n");
    }

    @Override
    public void batalkanPeminjaman(String kodePeminjaman) {
        try {
            if (kodePeminjaman == null || kodePeminjaman.isEmpty()) {
                throw new IllegalArgumentException("Kode peminjaman tidak valid");
            }
            System.out.println("\nPeminjaman dengan kode " + kodePeminjaman + " berhasil dibatalkan");
            totalPeminjaman--;
        } catch (IllegalArgumentException e) {
            System.err.println("Error dalam pembatalan peminjaman: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Dosen - " + super.toString() + ", Departemen: " + departemen;
    }
}
