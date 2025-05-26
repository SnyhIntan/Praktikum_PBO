import java.util.List;

public class Mahasiswa extends User implements PeminjamanInterface{
    private String prodi;
    private static int totalPeminjaman = 0;

    public Mahasiswa(String nim, String password, String nama, String email, String prodi) {
        super(nim, password, nama, email);
        this.prodi = prodi;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
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
            
            List<Peminjaman> data = CSVHelper.readPeminjamanFromCSV("peminjaman.csv");
            totalPeminjaman = 0;
            for (Peminjaman p : data) {
                if (p.getNimNip().equals(this.nimNip)) {
                    totalPeminjaman++;
                }
            }
            
            totalPeminjaman++;
            System.out.println("\n\nMahasiswa " + nama + " berhasil mengajukan peminjaman ruangan!!");
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
        System.out.println("\nSelamat datang, Mahasiswa " + getNama() + "!");
        System.out.println("======================================");
        System.out.println("NIM   : " + getNimNip());
        System.out.println("Email : " + getEmail());
        System.out.println("Prodi : " + prodi);
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
        return "Mahasiswa - " + super.toString() + ", Prodi: " + prodi;
    }
}
