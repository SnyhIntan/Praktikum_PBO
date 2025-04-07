public class Peminjaman {
    private final String nimNip;
    private String nama;
    private String kodeRuangan;
    private String tanggal;
    private String waktuMulai;
    private String waktuSelesai;
    private final String keterangan;


    public Peminjaman(String nimNip, String nama, String kodeRuangan, String tanggal, String waktuMulai, String waktuSelesai, String keterangan) {
        this.nimNip = nimNip;
        this.nama = nama;
        this.kodeRuangan = kodeRuangan;
        this.tanggal = tanggal;
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
        this.keterangan = keterangan;
    }
    

    // Getter
    public String getNimNip() {
        return nimNip;
    }

    public String getNama() {
        return nama;
    }

    public String getKodeRuangan() {
        return kodeRuangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getWaktuMulai() {
        return waktuMulai;
    }

    public String getWaktuSelesai() {
        return waktuSelesai;
    }

    public String getKeterangan() {
        return keterangan;
    }


    // Setter
    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setKodeRuangan(String kodeRuangan) {
        this.kodeRuangan = kodeRuangan;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setWaktuMulai(String waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public void setWaktuSelesai(String waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public void updateData(String nama, String kodeRuangan, String tanggal, String waktuMulai, String waktuSelesai) {
        this.nama = (nama != null && !nama.trim().isEmpty()) ? nama : this.nama;
        this.kodeRuangan = (kodeRuangan != null && !kodeRuangan.trim().isEmpty()) ? kodeRuangan : this.kodeRuangan;
        this.tanggal = (tanggal != null && !tanggal.trim().isEmpty()) ? tanggal : this.tanggal;
        this.waktuMulai = (waktuMulai != null && !waktuMulai.trim().isEmpty()) ? waktuMulai : this.waktuMulai;
        this.waktuSelesai = (waktuSelesai != null && !waktuSelesai.trim().isEmpty()) ? waktuSelesai : this.waktuSelesai;
    }

    @Override
    public String toString() {
        return "NIM/NIP: " + nimNip +
            ", Nama: " + nama +
            ", Kode Ruangan: " + kodeRuangan +
            ", Tanggal: " + tanggal +
            ", Waktu: " + waktuMulai + " - " + waktuSelesai +
            ", Keterangan: " + keterangan;
    }

}
