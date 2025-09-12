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
                    break;
                case 2:
                    listaVeiculos();
                    retornarMenu();
                    break;
                case 3:
                    removerVeiculo();
                    retornarMenu();
                    break;
                case 4:
                    pesquisarVeiculo();
                    retornarMenu();
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
        for (Veiculo veiculo : veiculos) {
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
            while (true) {
            String placa = Input.scanString("Digite a placa do veículo que deseja excluir, ou 0 pra retornar pro menu: ", scan);
            if (placa.equals("0")) {
                return;
            }
            boolean controle = veiculos.removeIf(veiculo -> placa.equalsIgnoreCase(veiculo.getPlaca()));
            if (controle) {
                System.out.println("O veículo foi excluído com sucesso!");
                break;
            } else {
                System.out.println("Placa não encontrada, tente novamente.");
            }
        }

    }

    static void pesquisarVeiculo() {

        if (veiculos.isEmpty()) {
            System.out.println("Não há nenhum veículo cadastrado");
            retornarMenu();
            return;
        }

        String tipoPesquisa;
        String pesquisa = "";

        while (true) {
            tipoPesquisa = Input.scanString("Você deseja pesquisar por placa ou modelo? ", scan);
            if (tipoPesquisa.equalsIgnoreCase("placa")) {
                break;
            } else if (tipoPesquisa.equalsIgnoreCase("modelo")) {
                break;
            } else {
                System.out.println("Opção inválida, escolha entre placa ou modelo pra realizar a pesquisa. Tente novamente.");
            }
        }

        boolean controle = false;
        int tentativas = 0;

        while (!controle) {
            if (tipoPesquisa.equalsIgnoreCase("placa")) {
                pesquisa = Input.scanString("Digite a placa do veículo, ou 0 pra retornar pro menu: ", scan);
                if (pesquisa.equals("0")) {
                    return;
                }
            } else {
                pesquisa = Input.scanString("Digite o modelo do veículo, ou 0 pra retornar pro menu: ", scan);
                if (pesquisa.equals("0")) {
                    return;
                }
            }

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
                System.out.println("Veículo não encontrado, tente novamente.");
                tentativas++;
                if (tentativas == 5) {
                    System.out.println("Limite de tentativas atingido.");
                    break;
                }
            }
        }

    }

    static void retornarMenu() {
        System.out.println("Presione Enter para confirmar.");
        scan.nextLine();
    }

}