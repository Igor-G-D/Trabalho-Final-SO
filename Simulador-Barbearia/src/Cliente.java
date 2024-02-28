class Cliente extends Thread {
    private Barbearia barbearia;

    public Cliente(String nome, Barbearia barbearia) {
        super(nome);
        this.barbearia = barbearia;
    }

    @Override
    public void run() {
        try {
            barbearia.atenderCliente(this);
        } catch (InterruptedException e) {
            // Cliente interrompido
        }
    }
}