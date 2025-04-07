public class Dosen extends User {
    private String departemen;

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

    public void ajukanPeminjaman(String kodeRuangan, String tanggal, String waktuMulai, String waktuSelesai) {
        System.out.println("\n\nDosen " + nama + " berhasil mengajukan peminjaman ruangan!!");
        System.out.println("Ruangan : " + kodeRuangan);
        System.out.println("Tanggal : " + tanggal);
        System.out.println("Waktu   : " + waktuMulai + " - " + waktuSelesai);
        System.out.print("\nHarap ruangan digunakan dengan baik.");
        System.out.print("\nTekan Enter untuk kembali...");
        scanner.nextLine();
    }
    

    @Override
    public String toString() {
        return "Dosen - " + super.toString() + ", Departemen: " + departemen;
    }
}
