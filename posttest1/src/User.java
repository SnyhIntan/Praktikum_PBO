public class User {
    private final String nimNip; // ID unik
    private String password;
    private String nama;
    private String email;

    public User(String nimNip, String password, String nama, String email) {
        this.nimNip = nimNip;
        this.password = password;
        this.nama = nama;
        this.email = email;
    }

    public String getNimNip() {
        return nimNip;
    }

    public boolean login(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "NIM/NIP: " + nimNip + ", Nama: " + nama + ", Email: " + email;
    }
}
