package dados;

public class DoacaoPerecivel extends Doacao {
        private  TipoDoacaoPerecivel tipo;
        private String validade;

        public DoacaoPerecivel(String descricao, double valor, int quantidade, Doador doador, TipoDoacaoPerecivel tipo, String validade) {
            super(descricao, valor, quantidade, doador);
            this.tipo = tipo;
            this.validade = validade;
        }

        public TipoDoacaoPerecivel getTipo() {
            return tipo;
        }

        public String getValidade() {
            return validade;
        }


        @Override
        public String geraResumo() {
            return getDescricao() + "," + getValor() + "," + getQuantidade() + "," + tipo + "," + validade;
        }
    }

