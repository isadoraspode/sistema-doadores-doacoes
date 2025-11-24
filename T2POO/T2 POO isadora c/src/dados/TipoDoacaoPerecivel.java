package dados;

public enum TipoDoacaoPerecivel {
        ALIMENTO,
        MEDICAMENTO;

        public static boolean existeTipo(String tipo) {
            for (dados.TipoDoacaoPerecivel t : dados.TipoDoacaoPerecivel.values()) {
                if (t.name().equalsIgnoreCase(tipo)) {
                    return true;
                }
            }
            return false;
        }
    }
