public class Mahasiswa extends User {
    private String prodi;

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

    public void ajukanPeminjaman(String kodeRuangan, String tanggal, String waktuMulai, String waktuSelesai) {
        System.out.println("\n\nMahasiswa " + nama + " berhasil mengajukan peminjaman ruangan!!");
        System.out.println("Ruangan : " + kodeRuangan);
        System.out.println("Tanggal : " + tanggal);
        System.out.println("Waktu   : " + waktuMulai + " - " + waktuSelesai);
        System.out.print("\nHarap ruangan digunakan dengan baik.");
        System.out.print("\nTekan Enter untuk kembali...");
        scanner.nextLine();
    }
    

    @Override
    public String toString() {
        return "Mahasiswa - " + super.toString() + ", Prodi: " + prodi;
    }
}
