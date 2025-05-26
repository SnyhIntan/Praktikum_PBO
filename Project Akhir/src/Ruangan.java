public class Ruangan {
    protected final String kodeRuangan;
    protected int kapasitas; 
    protected String fasilitas;
    protected String lokasi;

    public Ruangan(String kodeRuangan, int kapasitas, String fasilitas, String lokasi) {
        this.kodeRuangan = kodeRuangan;
        this.kapasitas = kapasitas;
        this.fasilitas = fasilitas;
        this.lokasi = lokasi;
    }

    public String getKodeRuangan() {
        return kodeRuangan;
    }

    protected int getKapasitas() {
        return kapasitas;
    }

    protected void setKapasitas(int kapasitas) {
        if (kapasitas > 0) {
            this.kapasitas = kapasitas;
        } else {
            System.out.println("Kapasitas harus lebih dari 0!");
        }
    }

    protected String getFasilitas() {
        return fasilitas;
    }

    protected void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    protected String getLokasi() {
        return lokasi;
    }

    protected void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getJenis() {
        return "Ruangan Umum";
    }

    @Override
    public String toString() {
        return getJenis() +
               "\nKode Ruangan: " + kodeRuangan +
               "\nKapasitas   : " + kapasitas +
               "\nFasilitas   : " + fasilitas +
               "\nLokasi      : " + lokasi +
               "\n";
    }
}
