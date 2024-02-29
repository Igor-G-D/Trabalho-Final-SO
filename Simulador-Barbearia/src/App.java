import java.util.ArrayList;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        int N = 5;      
        int M = 3;     
        int tempoDeGeracaoEmSegundos = new Random().nextInt(20) + 10; // Gera um tempo aleatório entre 10 e 30 segundos

        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("Barbearia aberta! " + N + " cadeira(s) de espera, " + M + " barbeiro(s) e " + (M/2) + " pente(s) e tesoura(s).");
        System.out.println("Clientes serão gerados por " + tempoDeGeracaoEmSegundos + " segundos.");
        System.out.println("------------------------------------------------------------------------------------------------------");

        Barbearia barbearia = new Barbearia(N, M); 

        // Criação de M barbeiros
        Barbeiro[] barbeiros = new Barbeiro[M];
        for (int i = 0; i < barbeiros.length; i++) {
            barbeiros[i] = new Barbeiro("Barbeiro " + (i + 1), barbearia);
            barbeiros[i].start();
        }

        // Criação de clientes
        ArrayList<Cliente> clientes = new ArrayList<Cliente>(); 
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        int index = 1;

        while (elapsedTime < tempoDeGeracaoEmSegundos * 1000) {
            Thread.sleep(new Random().nextInt(3000));

            Cliente cliente = new Cliente("Cliente " + (index), barbearia);
            index++;
            clientes.add(cliente);
            cliente.start();

            elapsedTime = System.currentTimeMillis() - startTime;
        }
        
        // Aguarda até que todos os clientes tenham sido atendidos
        int totalClientes = clientes.size(); 
        while (barbearia.getTotalClientesAtendidos() + barbearia.getTotalClientesDesistiram() < totalClientes) 
        {
            Thread.sleep(1000);
        }

        for (Cliente cliente : clientes) {
            cliente.join();
        }

        for (Barbeiro barbeiro : barbeiros) {
            barbeiro.interrupt();
            barbeiro.join();
        }

        // Exibe o total de clientes atendidos
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("Total de clientes gerados: " + totalClientes);
        System.out.println("Total de clientes atendidos: " + barbearia.getTotalClientesAtendidos());
        System.out.println("Total de clientes que desistiram: " + barbearia.getTotalClientesDesistiram());
        System.out.println("------------------------------------------------------------------------------------------------------");

        System.exit(0);
    }
}
