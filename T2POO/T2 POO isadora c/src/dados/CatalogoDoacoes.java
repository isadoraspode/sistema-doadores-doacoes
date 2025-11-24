package dados;

import java.util.ArrayList;
import java.util.List;

public class CatalogoDoacoes {
    private List<Doacao> doacoes;

    public CatalogoDoacoes() {
        this.doacoes = new ArrayList<>();
    }

    public boolean adicionaDoacao(Doacao doacao) {
        if (doacao != null) {
            doacoes.add(doacao);
            doacao.getDoador().adicionaDoacao(doacao);
            return true;
        }
        return false;
    }

    public List<Doacao> getDoacoes() {
        return doacoes;
    }

    public List<DoacaoDuravel> buscaPorTipoDuravel(TipoDoacaoDuravel tipo) {
        List<DoacaoDuravel> resultados = new ArrayList<>();
        for (Doacao d : doacoes) {
            // Verifica se é DadosDoacao.DoacaoDuravel e se o tipo confere
            if (d instanceof DoacaoDuravel) {
                DoacaoDuravel dd = (DoacaoDuravel) d;
                if (dd.getTipo() == tipo) {
                    resultados.add(dd);
                }
            }
        }
        return resultados;
    }

    public DoacaoPerecivel buscaMaiorPerecivelPorTipo(TipoDoacaoPerecivel tipo) {
        DoacaoPerecivel maior = null;
        for (Doacao d : doacoes) {
            if (d instanceof DoacaoPerecivel) {
                DoacaoPerecivel dp = (DoacaoPerecivel) d;
                if (dp.getTipo().equals(tipo)) {
                    if (maior == null || dp.getQuantidade() > maior.getQuantidade()) {
                        maior = dp;
                    }
                }
            }
        }
        return maior;
    }
}

