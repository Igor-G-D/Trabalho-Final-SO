import java.util.ArrayList;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        int N = 3;      // Cadeiras de espera
        int M = 2;      // Cadeiras de barbear = barbeiros

        int tempoDeGeracaoEmSegundos = 10;  // Tempo em segundos para gerar clientes

        Barbearia barbearia = new Barbearia(N, M); 

        // Criação de M barbeiros
        Barbeiro[] barbeiros = new Barbeiro[M];
        for (int i = 0; i < barbeiros.length; i++) {
            barbeiros[i] = new Barbeiro("Barbeiro " + (i + 1), barbearia);
            barbeiros[i].start();
        }

        // Criação de clientes enquanto o tempo decorrido for menor que o tempo desejado
        ArrayList<Cliente> clientes = new ArrayList<Cliente>(); 
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        int i = 0;
        while (elapsedTime < tempoDeGeracaoEmSegundos * 1000) { // Converte o tempo para milissegundos
            Cliente cliente = new Cliente("Cliente " + (i + 1), barbearia);
            i++;
            clientes.add(cliente);
            cliente.start();
            try {
                Thread.sleep(new Random().nextInt(2000)); // Aguarda um período de tempo aleatório
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            elapsedTime = System.currentTimeMillis() - startTime;
        }
        
        int totalClientes = clientes.size(); // Supondo que 'clientes' seja a lista de todos os clientes gerados

        // Aguarda até que todos os clientes tenham sido atendidos
        while (barbearia.getTotalClientesAtendidos() + barbearia.getTotalClientesDesistiram() < totalClientes) 
        {
            Thread.sleep(1000); // Aguarda um segundo antes de verificar novamente
        }

        // Exibe o total de clientes atendidos
        System.out.println("Total de clientes atendidos: " + barbearia.getTotalClientesAtendidos());
        System.out.println("Total de clientes que desistiram: " + barbearia.getTotalClientesDesistiram());

        // Encerra a execução do programa
        System.exit(0);
    }
}
