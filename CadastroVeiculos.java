import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CadastroVeiculos {
    static Scanner scan = new Scanner(System.in);
    static List<Veiculo> veiculos = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("==== Bem vindo ao Controle de Frotas ====");
        String menu = """
                MENU
                Escolha uma das opções abaixo:
                1 - Cadastro de Veículo
                2 - Listar Veículos
                3 - Excluir Veículo
                4 - Pesquisar Veículo
                0 - Sair
                """;
        int opcao;
        do {
            System.out.println(menu);
            opcao = Input.scanInt("Escolha uma opção: ", scan);
            switch (opcao) {
                case 1:
                    cadastraVeiculo();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 2:
                    listaVeiculos();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 3:
                    removerVeiculo();
                    System.out.println("Presione Enter para continuar");
                    scan.nextLine();
                    break;
                case 4:
                    pesquisarVeiculo();
                    System.out.println("Presione Enter para continuar");
                    scan.nextLine();
                    break;
                case 0:
                    System.out.println("Obrigado por utilizar nosso sistema. Até logo!");
                    break;
                default:
                    System.out.println("Opção Inválida, tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }

    static void cadastraVeiculo() {
        System.out.println("==== Cadastrando novo veículo ====");
        String marca = Input.scanString("Digite a marca do veículo: ", scan);
        String modelo = Input.scanString("Digite o modelo do veículo: ", scan);
        int ano = Input.scanInt("Digite o ano do veículo: ", scan);
        String placa = Input.scanString("Digite a placa do veículo (EX: ABC-123): ", scan);
        Veiculo veiculo = new Veiculo(marca, modelo, ano, placa);
        veiculos.add(veiculo);
    }

    static void listaVeiculos() {
        if (veiculos.isEmpty()) {
            System.out.println("Não há nenhum veículo cadastrado.");
            return;
        }
        System.out.println("==== Veículos cadastrados ====");
        int contador = 1;
        for (Veiculo veiculo : veiculos) {
            System.out.printf("Veículo %d: %s%n", contador, veiculo);
            contador++;
        }
    }

    static void removerVeiculo() {
        listaVeiculos();
        String placa = Input.scanString("Digite a placa do veículo que deseja remover: ", scan);
        boolean controle = veiculos.removeIf(v -> placa.equalsIgnoreCase(v.getPlaca()));

        if (controle) {
            System.out.println("O veículo foi excluído com sucesso!");
        } else {
            System.out.println("Placa não encontrada, voltando pro menu...");
        }

    }

    static void pesquisarVeiculo() {

        String tipoPesquisa = Input.scanString("Você deseja pesquisar por placa ou modelo?: ", scan);
        String pesquisa = "";

        if (tipoPesquisa.equalsIgnoreCase("placa")) {
            pesquisa = Input.scanString("Digite a placa do veículo:", scan);
        } else if (tipoPesquisa.equalsIgnoreCase("modelo")) {
            pesquisa = Input.scanString("Digite o modelo do veículo:", scan);
        } else {
            System.out.println("Opção inválida, escolha entre placa ou modelo pra realizar a pesquisa.");
            return;
        }

        boolean controle = false;

        for (Veiculo veiculo : veiculos) {
            if (tipoPesquisa.equalsIgnoreCase("placa") && pesquisa.equalsIgnoreCase(veiculo.getPlaca())) {
                if (!controle) {
                    System.out.println("=== Resultado da Pesquisa ===");
                }
                System.out.println(veiculo);
                controle = true;
                break;
            } else if (tipoPesquisa.equalsIgnoreCase("modelo") && veiculo.getModelo().toLowerCase().contains(pesquisa.toLowerCase())) {
                if (!controle) {
                    System.out.println("=== Resultado da Pesquisa ===");
                }
                System.out.println(veiculo);
                controle = true;
            }
        }

        if (!controle) {
            System.out.println("Veículo não encontrado, tente novamente.");
        }

    }

}