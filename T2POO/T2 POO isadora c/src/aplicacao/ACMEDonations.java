package aplicacao;

import dados.*;
import java.io.*;
import java.util.List;
import java.util.Scanner;

public class ACMEDonations {
    private CatalogoDoadores catalogoDoadores;
    private CatalogoDoacoes catalogoDoacoes;
    private Scanner entradaDados;

    public ACMEDonations() {
        this.catalogoDoadores = new CatalogoDoadores();
        this.catalogoDoacoes = new CatalogoDoacoes();
    }

    public void executar(String arquivoSaida, String arquivoEntrada) {
        try (PrintWriter relatorio = new PrintWriter(new File(arquivoSaida))) {
            try (Scanner entrada = new Scanner(new File(arquivoEntrada))) {
                this.entradaDados = entrada;

                passo1("recursos/doadores.csv", relatorio);
                passo2("recursos/doacoespereciveis.csv", relatorio);
                passo3("recursos/doacoesduraveis.csv", relatorio);
                passo4(relatorio);
                passo5(relatorio);
                passo6(relatorio);
                passo7(relatorio);
                passo8(relatorio);
                passo9(relatorio);
                passo10(relatorio);

            } catch (FileNotFoundException e) {
                System.err.println("Erro: Arquivo de entrada '" + arquivoEntrada + "' não encontrado.");
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo de saída '" + arquivoSaida + "' não pode ser criado/aberto.");
            e.printStackTrace();
        }
    }

    private void passo1(String arquivo, PrintWriter relatorio) {
        try (Scanner sc = new Scanner(new File(arquivo))) {
            sc.nextLine();
            while (sc.hasNextLine()) {
                String linha = sc.nextLine();
                String[] dados = linha.split(";");
                if (dados.length < 2) continue;

                String nome = dados[0].trim();
                String email = dados[1].trim();

                Doador novoDoador = new Doador(nome, email);
                if (catalogoDoadores.adicionaDoador(novoDoador)) {
                    relatorio.println("1:" + novoDoador.geraResumo(false));
                } else {
                    relatorio.println("1:ERRO:doador repetido");
                }
            }
        } catch (FileNotFoundException e) {
            relatorio.println("1:ERRO:Arquivo de doadores não encontrado.");
        }

    }

    private void passo2(String arquivo, PrintWriter relatorio) {
        try (Scanner sc = new Scanner(new File(arquivo))) {
            sc.nextLine();
            while (sc.hasNextLine()) {
                String linha = sc.nextLine();
                String[] dados = linha.split(";");
                if (dados.length < 6) continue;

                String descricao = dados[0].trim();
                String emailDoador = dados[3].trim();
                String tipoDividido = dados[4].trim();
                String validade = dados[5].trim();

                    TipoDoacaoPerecivel tipoDoacaoPerecivel = TipoDoacaoPerecivel.valueOf(dados[4].trim());


                Doador doador = catalogoDoadores.recuperaDoadorPorEmail(emailDoador);

                if (doador == null) {
                    relatorio.println("2:ERRO:doador inexistente");
                    continue;
                }

                double valor = 0;
                int quantidade = 0;
                try {
                    valor = Double.parseDouble(dados[1].trim());
                    quantidade = Integer.parseInt(dados[2].trim());
                } catch (NumberFormatException e) {
                    relatorio.println("2:ERRO:formato invalido");
                    continue;
                }

                DoacaoPerecivel novaDoacao = new DoacaoPerecivel(descricao, valor, quantidade, doador, tipoDoacaoPerecivel, validade);
                catalogoDoacoes.adicionaDoacao(novaDoacao);
                relatorio.println("2:" + novaDoacao.geraResumo());

            }
        } catch (FileNotFoundException e) {
            relatorio.println("2:ERRO:Arquivo de doações perecíveis não encontrado.");
        }
    }

    private void passo3(String arquivo, PrintWriter relatorio) {
        try (Scanner sc = new Scanner(new File(arquivo))) {
            sc.nextLine();
            while (sc.hasNextLine()) {
                String linha = sc.nextLine();
                String[] dados = linha.split(";");
                if (dados.length < 5) continue;

                String descricao = dados[0].trim();
                String emailDoador = dados[3].trim();
                String tipoStr = dados[4].trim();

                Doador doador = catalogoDoadores.recuperaDoadorPorEmail(emailDoador);

                if (doador == null) {
                    relatorio.println("3:ERRO:doador inexistente");
                    continue;
                }

                double valor = 0;
                int quantidade = 0;
                try {
                    valor = Double.parseDouble(dados[1].trim());
                    quantidade = Integer.parseInt(dados[2].trim());
                } catch (NumberFormatException e) {
                    relatorio.println("3:ERRO:formato invalido");
                    continue;
                }

                TipoDoacaoDuravel tipo;
                try {
                    tipo = TipoDoacaoDuravel.valueOf(tipoStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    relatorio.println("3:ERRO:tipo invalido");
                    continue;
                }

                DoacaoDuravel novaDoacao = new DoacaoDuravel(descricao, valor, quantidade, doador, tipo);
                catalogoDoacoes.adicionaDoacao(novaDoacao);
                relatorio.println("3:" + novaDoacao.geraResumo());
            }
        } catch (FileNotFoundException e) {
            relatorio.println("3:ERRO:Arquivo de doações duráveis não encontrado.");
        }
    }

    private void passo4(PrintWriter relatorio) {
        if (!entradaDados.hasNextLine()) {
            relatorio.println("4:ERRO:Dados de entrada insuficientes (falta e-mail para passo 4).");
            return;
        }
        String email = entradaDados.nextLine().trim();
        Doador doador = catalogoDoadores.recuperaDoadorPorEmail(email);

        if (doador == null) {
            relatorio.println("4:ERRO:e-mail inexistente");
        } else {
            relatorio.println("4:" + doador.geraResumo(false));
        }
    }

    private void passo5(PrintWriter relatorio) {
        List<Doacao> doacoes = catalogoDoacoes.getDoacoes();
        if (doacoes.isEmpty()) {
            relatorio.println("5:ERRO:nenhuma doacao cadastrada");
            return;
        }

        for (Doacao d : doacoes) {
            relatorio.println("5:" + d.geraResumoCompleto());
        }
    }

    private void passo6(PrintWriter relatorio) {
        List<Doador> doadores = catalogoDoadores.getDoadores();
        if (doadores.isEmpty()) {
            relatorio.println("6:ERRO:nenhum doador encontrado");
            return;
        }

        for (Doador d : doadores) {
            relatorio.println("6:" + d.geraResumo(true));
        }
    }

    private void passo7(PrintWriter relatorio) {
        if (!entradaDados.hasNextLine()) return;

        String nome = entradaDados.nextLine().trim();
        Doador doador = catalogoDoadores.recuperaDoadorPorNome(nome);

        if (doador == null || doador.getDoacoes().isEmpty()) {
            relatorio.println("7:ERRO:nenhuma doacao localizada");
            return;
        }

        for (Doacao d : doador.getDoacoes()) {
            relatorio.println("7:" + d.geraResumoDoacaoDoador());
        }

    }

    private void passo8(PrintWriter relatorio) {
        if (!entradaDados.hasNextLine()) {
            relatorio.println("8:ERRO:Dados de entrada insuficientes (falta tipo de doação durável).");
            return;
        }

        String tipoStr = entradaDados.nextLine().trim();
        TipoDoacaoDuravel tipo;

        try {
            tipo = TipoDoacaoDuravel.valueOf(tipoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            relatorio.println("8:ERRO:tipo invalido");
            return;
        }

        List<DoacaoDuravel> doacoes = catalogoDoacoes.buscaPorTipoDuravel(tipo);

        if (doacoes.isEmpty()) {
            return;
        }

        for (DoacaoDuravel d : doacoes) {
            relatorio.println("8:" + d.geraResumoCompleto());
        }
    }

    private void passo9(PrintWriter relatorio) {
        if (!entradaDados.hasNext()) return;

        String tipo = entradaDados.nextLine().trim();

        if (tipo.isEmpty()) {
            relatorio.println("9:ERRO:tipo invalido");
            return;
        }

        DoacaoPerecivel maior = catalogoDoacoes.buscaMaiorPerecivelPorTipo(TipoDoacaoPerecivel.valueOf(tipo));


        if (maior == null) {
            relatorio.println("9:ERRO:nenhuma doacao localizada");
            return;
        }

        relatorio.println("9:" + maior.geraResumoCompleto());

    }

    private void passo10(PrintWriter relatorio) {

        List<Doador> doadores = catalogoDoadores.getDoadores();
        if (doadores.isEmpty()) {
            relatorio.println("10:ERRO:nenhum doador localizado");
            return;
        }

        Doador doadorMaiorMontante = null;
        double maiorMontante = -1;

        for (Doador d : doadores) {
            double montanteAtual = 0;
            for (Doacao doacao : d.getDoacoes()) {
                montanteAtual += doacao.getValor() * doacao.getQuantidade();
            }

            if (montanteAtual >= maiorMontante) {
                maiorMontante = montanteAtual;
                doadorMaiorMontante = d;
            }
        }

        String resumo = String.format("10:%s,%.2f", doadorMaiorMontante.geraResumo(false), maiorMontante);
        relatorio.println(resumo);
    }
}

