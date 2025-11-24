package dados;

import java.util.ArrayList;
import java.util.List;

public class Doador {
    private String nome;
    private String email;
    private List<Doacao> doacoes;

    public Doador(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.doacoes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public List<Doacao> getDoacoes() {
        return doacoes;
    }

    public int getQuantidadeDoacoes() {
        return doacoes.size();
    }

    public void adicionaDoacao(Doacao doacao) {
        this.doacoes.add(doacao);
    }

    public String geraResumo(boolean comQuantidade) {
        if (comQuantidade) {
            return nome + "," + email + "," + getQuantidadeDoacoes();
        }
        return nome + "," + email;
    }
}

