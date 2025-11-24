import aplicacao.ACMEDonations;

public class Main {
    public static void main(String[] args) {
        ACMEDonations app = new ACMEDonations();

        String saida = "recursos/relatorio.txt";
        String entrada = "recursos/dadosentrada.txt";

        app.executar(saida, entrada);
        System.out.println("Execução concluída. Verifique o arquivo " + saida);
    }
}