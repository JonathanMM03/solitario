package solitario;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Baraja {
    private final Queue<Carta> cartas;

    public Baraja() {
        cartas = new LinkedList<>();
        String[] palos = {"♥", "♦", "♣", "♠"};
        for (String palo : palos) {
            for (int valor = 1; valor <= 13; valor++) {
                cartas.add(new Carta(palo, valor, false));
            }
        }
        Collections.shuffle((LinkedList<Carta>) cartas);
    }

    public Carta sacarCarta() {
        return cartas.poll();
    }

    public boolean estaVacia() {
        return cartas.isEmpty();
    }
}