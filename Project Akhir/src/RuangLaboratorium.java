public class RuangLaboratorium extends Ruangan {
    private String jenisLaboratorium;

    public RuangLaboratorium(String kodeRuangan, int kapasitas, String fasilitas, String lokasi, String jenisLaboratorium) {
        super(kodeRuangan, kapasitas, fasilitas, lokasi);
        this.jenisLaboratorium = jenisLaboratorium;
    }
    public void setJenisLaboratorium(String jenisLaboratorium) {
        this.jenisLaboratorium = jenisLaboratorium;
    }

    public String getJenisLaboratorium() {
        return jenisLaboratorium;
    }

    @Override
    public String getJenis() {
        return "Laboratorium " + jenisLaboratorium;
    }
}
