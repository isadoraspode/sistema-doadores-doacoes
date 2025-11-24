package dados;

public class DoacaoDuravel extends Doacao {
    private TipoDoacaoDuravel tipo;
        public DoacaoDuravel(String descricao, double valor, int quantidade, Doador doador, TipoDoacaoDuravel tipo) {
            super(descricao, valor, quantidade, doador);
            this.tipo = tipo;
        }

        public TipoDoacaoDuravel getTipo() {
            return tipo; }

        @Override
        public String geraResumo() {
            return getDescricao() + "," + getValor() + "," + getQuantidade() + "," + tipo.name();
        }
    }
