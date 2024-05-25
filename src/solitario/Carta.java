package solitario;
import lombok.Data;

@Data
public class Carta {
    private final String palo;
    private final int valor;
    private boolean visible;

    public Carta(String palo, int valor, boolean visible) {
        this.palo = palo;
        this.valor = valor;
        this.visible = visible;
    }

    @Override
    public String toString() {
        return visible ? valor + " de " + palo : "[Oculta]";
    }
}
