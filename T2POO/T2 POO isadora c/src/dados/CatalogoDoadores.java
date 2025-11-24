package dados;

import java.util.ArrayList;
import java.util.List;

public class CatalogoDoadores {
        private List<Doador> doadores;

        public CatalogoDoadores() {
            this.doadores = new ArrayList<>();
        }

        public boolean adicionaDoador(Doador doador) {
            if (recuperaDoadorPorEmail(doador.getEmail()) == null) {
                return doadores.add(doador);
            }
            return false;
        }

        public Doador recuperaDoadorPorEmail(String email) {
            for (Doador d : doadores) {
                if (d.getEmail().equalsIgnoreCase(email)) {
                    return d;
                }
            }
            return null;
        }

        public Doador recuperaDoadorPorNome(String nome) {
            for (Doador d : doadores) {
                if (d.getNome().equalsIgnoreCase(nome)) {
                    return d;
                }
            }
            return null;
        }

        public List<Doador> getDoadores() {
            return doadores;
        }
    }