import java.util.Random;
import java.util.concurrent.Semaphore;

class Barbearia {
    private int cadeirasEspera;
    private int clientesAtendidos;
    private int clientesDesistiram;
    private Semaphore mutex;
    private Semaphore clientes;
    private Semaphore barbeirosDisponiveis;

    public Barbearia(int N, int M) {
        this.clientesAtendidos = 0;
        this.clientesDesistiram = 0;
        this.cadeirasEspera = N;
        this.mutex = new Semaphore(1);
        this.clientes = new Semaphore(0);
        this.barbeirosDisponiveis = new Semaphore(M, true);
    }

    public void cortarCabelo(Barbeiro barbeiro) throws InterruptedException {
        clientes.acquire();
        barbeirosDisponiveis.acquire();

        mutex.acquire();
        cadeirasEspera++;
        mutex.release();

        int tempo = new Random().nextInt(4000) + 4000;
        System.out.println(barbeiro.getName() + " está cortando cabelo por " + tempo + "ms. Cadeiras de espera restantes: " + cadeirasEspera + ".");
        Thread.sleep(tempo);
        sairBarbearia();

        barbeirosDisponiveis.release();
    }

    public void atenderCliente(Cliente cliente) throws InterruptedException {
        mutex.acquire();
        if (cadeirasEspera > 0) {
            cadeirasEspera--;
            System.out.println(cliente.getName() + " está esperando. Cadeiras de espera restantes: " + cadeirasEspera);
            mutex.release();
            clientes.release();
        } 
        else 
        {
            this.clientesDesistiram++;
            mutex.release();
            System.out.println(cliente.getName() + " desistiu devido à sala de espera cheia.");
        }
    }

    private void sairBarbearia() throws InterruptedException {
        mutex.acquire();
        clientesAtendidos++;
        mutex.release();
        System.out.println("Um cliente terminou seu corte e saiu da barbearia.");
    }

    public int getTotalClientesAtendidos() {
        return clientesAtendidos;
    }

    public int getTotalClientesDesistiram() {
        return clientesDesistiram;
    }
}
