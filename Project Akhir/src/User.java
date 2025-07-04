import java.util.Scanner;

public abstract class User {
    protected  final String nimNip;
    protected String password;
    protected String nama;
    protected String email;
    protected static Scanner scanner = new Scanner(System.in);

    public User(String nimNip, String password, String nama, String email) {
        this.nimNip = nimNip;
        this.password = password;
        this.nama = nama;
        this.email = email;
    }

    public final String getNimNip() { // final method
        return nimNip;
    }

    public boolean login(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public boolean login(String nimNip, String inputPassword) {
        return this.nimNip.equals(nimNip) && this.password.equals(inputPassword);
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected String getNama() {
        return nama;
    }

    protected void setNama(String nama) {
        this.nama = nama;
    }

    protected String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    public abstract void tampilkanInfo();

    @Override
    public String toString() {
        return "NIM/NIP: " + nimNip + ", Nama: " + nama + ", Email: " + email;
    }
}
