# Trabalho-Final-SO
## UFPEL - Sistemas Operacionais, Igor Dutra e Graziele Fagundes, 2023/2
### Simulação da barbearia “Pronto Socorro"

  O problema consiste em simular o funcionamento de
uma barbearia com as seguintes características. A
barbearia tem uma sala de espera com N cadeiras e
uma cadeira de barbear. Se não tem clientes à
espera, os barbeiros sentam em cadeiras e dormem.
Quando chega um cliente, ele acorda um dos
barbeiros. Se chega outro cliente enquanto todos os
barbeiros estão trabalhando, ele ocupa uma cadeira
e espera (se tem alguma cadeira disponível) ou vai
embora (se todas as cadeiras estão ocupadas).

  Um barbeiro, para poder cortar o cabelo de alguém,
precisa ter acesso a uma tesoura e um pente, porém, nessa barbearia não há
Universidade Federal de Pelotas
Disciplina de Sistemas Operacionais
Prof. Rafael Burlamaqui Amaral / Monitor: Vinícius Peruzzi
uma quantidade de tesouras e pentes que permita que todos os barbeiros
trabalhem ao mesmo tempo.

  Vamos direcionar nossa simulação apenas em uma barbearia hipotética
direcionada ao atendimento de clientes. Para isso, é necessário ter um número
N de barbeiros, um número N/2 pentes e um número N/2 tesouras.

Com esse contexto, simule o seguinte cenário usando Pthreads.

Cada barbeiro é um thread e cada um dos clientes também.

Uma função principal deve ficar lançando uma quantidade indefinida de
threads, que representam clientes, com tempos variados entre eles (use um
valor aleatório a cada iteração), até que alguma condição de parada faça ela
encerrar.

  Um cliente (thread lançado) deve ir para a execução buscando uma cadeira,
enquanto houver espaço. Caso as cadeiras estejam ocupadas, o cliente vai
embora. Obs. (Dica): cuidado com o handle do join.

Cada cliente deve ter um tempo diferente para cada corte, isso porque supõese que cada cliente escolheu um corte de cabelo diferente. Os tempos devem
variar de 3 a 6 segundos.

Ao final da execução, deve-se informar a quantidade de clientes que foram
atendidos e quantos desistiram devido a sala de espera estar cheia.

Enquanto a condição de parada não for atingida, a main deve ficar criando
threads e enviando para a execução. A condição de parada pode ser um tempo
pré-definido passado como parâmetro de execução, ou um tempo aleatório, ou
a leitura de um getchar() na main.
