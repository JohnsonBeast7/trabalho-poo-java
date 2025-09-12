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
                    cadastrarVeiculo();
                    retornarMenu();
                    scan.nextLine();
                    break;
                case 2:
                    listaVeiculos();
                    retornarMenu();
                    scan.nextLine();
                    break;
                case 3:
                    removerVeiculo();
                    retornarMenu();
                    scan.nextLine();
                    break;
                case 4:
                    pesquisarVeiculo();
                    retornarMenu();
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

    static void cadastrarVeiculo() {
        System.out.println("==== Cadastrando novo veículo ====");
        String marca = Input.scanString("Digite a marca do veículo: ", scan);
        String modelo = Input.scanString("Digite o modelo do veículo: ", scan);
        int ano;
        do {
            ano = Input.scanInt("Digite o ano do veículo: ", scan);
        } while (!validarAno(ano));
        String placa;
        do {
            placa = Input.scanString("Digite a placa do veículo (EX: BRA1S23): ", scan);
        } while (!validarPlaca(placa));
        placa = placa.toUpperCase();
        Veiculo veiculo = new Veiculo(marca, modelo, ano, placa);
        veiculos.add(veiculo);
        System.out.println("Veículo cadastrado com sucesso!");
    }

    static boolean validarAno(int ano) {
        if (ano < 1886) {
            System.out.println("O ano do veículo não pode ser menor que 1886.");
            return false;
        }
        return true;
    }
    
    static boolean validarPlaca(String placa) {
        String placaFormatada = placa.toUpperCase();
        if (!placaFormatada.matches("[A-Z]{3}[0-9][A-Z][0-9]{2}")) {
            System.out.println("Placa inválida: formato esperado é ABC1D23.");
            return false;
        }
        for (Veiculo veiculo : veiculos ) {
            if (placaFormatada.equals(veiculo.getPlaca())) {
                System.out.println("Já existe um veículo cadastrado com essa placa. Tente novamente.");
                return false;
            }
        }
        return true;
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
        if (veiculos.isEmpty()) {
            System.out.println("Não há nenhum veículo cadastrado");
            return;
        }
        listaVeiculos();
        String placa = Input.scanString("Digite a placa do veículo que deseja excluir: ", scan);
        boolean controle = veiculos.removeIf(veiculo -> placa.equalsIgnoreCase(veiculo.getPlaca()));
        if (controle) {
            System.out.println("O veículo foi excluído com sucesso!");
        } else {
            System.out.println("Placa não encontrada, voltando pro menu...");
        }

    }

    static void pesquisarVeiculo() {
        if (veiculos.isEmpty()) {
            System.out.println("Não há nenhum veículo cadastrado");
            return;
        }
        String tipoPesquisa;
        String pesquisa = "";

        while (true) {
            tipoPesquisa = Input.scanString("Você deseja pesquisar por placa ou modelo? ", scan);
            if (tipoPesquisa.equalsIgnoreCase("placa")) {
                pesquisa = Input.scanString("Digite a placa do veículo: ", scan);
                break;
            } else if (tipoPesquisa.equalsIgnoreCase("modelo")) {
                pesquisa = Input.scanString("Digite o modelo do veículo: ", scan);
                break;
            } else {
                System.out.println("Opção inválida, escolha entre placa ou modelo pra realizar a pesquisa. Tente novamente.");
            }
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
            } else if (tipoPesquisa.equalsIgnoreCase("modelo")
                    && veiculo.getModelo().toLowerCase().contains(pesquisa.toLowerCase())) {
                if (!controle) {
                    System.out.println("=== Resultado da Pesquisa ===");
                }
                System.out.println(veiculo);
                controle = true;
            }
        }

        if (!controle) {
            System.out.println("Veículo não encontrado, voltando pro menu.");
        }

    }

    static void retornarMenu() {
        System.out.println("Presione Enter para retornar pro menu.");
    }

}