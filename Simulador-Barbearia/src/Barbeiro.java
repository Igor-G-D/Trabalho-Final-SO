class Barbeiro extends Thread {
    private Barbearia barbearia;

    public Barbeiro(String nome, Barbearia barbearia) {
        super(nome);
        this.barbearia = barbearia;
    }
    

    @Override
    public void run() {
        try {
            while (true) {
                barbearia.cortarCabelo(this);
            }
        } catch (InterruptedException e) {
            // Barbeiro interrompido
        }
    }
}