import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Barbearia {
    private int cadeirasEspera;
    private int clientesAtendidos;
    private int clientesDesistiram;
    private Lock lock;
    private Semaphore clientes;
    private Semaphore barbeirosDisponiveis;
    private Semaphore pentesDisponiveis;
    private Semaphore tesourasDisponiveis;

    public Barbearia(int N, int M) {
        this.clientesAtendidos = 0;
        this.clientesDesistiram = 0;
        this.cadeirasEspera = N;
        this.lock = new ReentrantLock();
        this.clientes = new Semaphore(0);
        this.barbeirosDisponiveis = new Semaphore(M, true);
        this.pentesDisponiveis = new Semaphore(M/2);
        this.tesourasDisponiveis = new Semaphore(M/2);
    }

    public void cortarCabelo(Barbeiro barbeiro) throws InterruptedException {
        if (!clientes.tryAcquire()) {
            System.out.println("Sem clientes, " + barbeiro.getName() + " está cochilando");
            clientes.acquire();
            System.out.println( barbeiro.getName() + " foi acordado para atender um cliente");
        }

        barbeirosDisponiveis.acquire();

        if (!pentesDisponiveis.tryAcquire()) {
            System.out.println("Sem pentes disponiveis, " + barbeiro.getName() + " está aguardando");
            pentesDisponiveis.acquire();
            System.out.println( barbeiro.getName() + " foi acordado para atender um cliente");
        }

        if (!tesourasDisponiveis.tryAcquire()) {
            System.out.println("Sem clientes, " + barbeiro.getName() + " está cochilando");
            tesourasDisponiveis.acquire();
            System.out.println( barbeiro.getName() + " foi acordado para atender um cliente");
        }

        lock.lock();
        cadeirasEspera++;
        lock.unlock();

        int tempo = new Random().nextInt(4000) + 4000;
        System.out.println(barbeiro.getName() + " está cortando cabelo por " + tempo + "ms. Cadeiras de espera restantes: " + cadeirasEspera + ".");
        Thread.sleep(tempo);
        sairBarbearia();
    }

    public void atenderCliente(Cliente cliente) throws InterruptedException {
        lock.lock();
        if (cadeirasEspera > 0) {
            cadeirasEspera--;
            System.out.println(cliente.getName() + " está esperando. Cadeiras de espera restantes: " + cadeirasEspera);
            lock.unlock();
            clientes.release();
        } 
        else 
        {
            this.clientesDesistiram++;
            lock.unlock();
            System.out.println(cliente.getName() + " desistiu devido à sala de espera cheia.");
        }
    }

    private void sairBarbearia() throws InterruptedException {
        lock.lock();
        clientesAtendidos++;
        barbeirosDisponiveis.release();
        pentesDisponiveis.release();
        tesourasDisponiveis.release();
        lock.unlock();
        System.out.println("Um cliente terminou seu corte e saiu da barbearia. Recursos Liberados");
    }

    public int getTotalClientesAtendidos() {
        return clientesAtendidos;
    }

    public int getTotalClientesDesistiram() {
        return clientesDesistiram;
    }
}
