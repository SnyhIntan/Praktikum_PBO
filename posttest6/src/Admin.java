public final class Admin {
    private final String nimNip;
    private String password;
    protected String nama;
    public String email;

    public Admin(String nimNip, String password, String nama, String email) {
        this.nimNip = nimNip;
        this.password = password;
        this.nama = nama;
        this.email = email;
    }

    public final String getNimNip() {
        return nimNip;
    }    

    public boolean login(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public boolean login(String nimNip, String inputPassword) {
        return this.nimNip.equals(nimNip) && this.password.equals(inputPassword);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected String getNama() {
        return nama;
    }

    protected void setNama(String nama) {
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
        return "Admin - NIM/NIP: " + nimNip + ", Nama: " + nama + ", Email: " + email;
    }
}
