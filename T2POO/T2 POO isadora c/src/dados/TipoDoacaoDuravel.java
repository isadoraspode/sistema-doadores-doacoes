package dados;

public enum TipoDoacaoDuravel {
        ELETRODOMESTICO,
        ROUPA,
        BRINQUEDO,
        MOVEL;

        public static boolean existeTipo(String tipo) {
            for (TipoDoacaoDuravel t : TipoDoacaoDuravel.values()) {
                if (t.name().equalsIgnoreCase(tipo)) {
                    return true;
                }
            }
            return false;
        }
    }

