package solitario;

import java.util.*;

public class Tablero {
    private final Stack<Carta>[] columnas;
    private final Queue<Carta> mazo;
    private final Stack<Carta> descarte;
    private final Stack<Carta>[] fundaciones;

    @SuppressWarnings("unchecked")
    public Tablero() {
        columnas = new Stack[7];
        for (int i = 0; i < 7; i++) {
            columnas[i] = new Stack<>();
        }
        mazo = new LinkedList<>();
        descarte = new Stack<>();
        fundaciones = new Stack[4];
        for (int i = 0; i < 4; i++) {
            fundaciones[i] = new Stack<>();
        }
        inicializarTablero();
    }

    private void inicializarTablero() {
        Baraja baraja = new Baraja();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j <= i; j++) {
                Carta carta = baraja.sacarCarta();
                carta.setVisible(j == i);
                columnas[i].push(carta);
            }
        }
        while (!baraja.estaVacia()) {
            mazo.add(baraja.sacarCarta());
        }
    }

    public Stack<Carta>[] getColumnas() {
        return columnas;
    }

    public Queue<Carta> getMazo() {
        return mazo;
    }

    public Stack<Carta> getDescarte() {
        return descarte;
    }

    public Stack<Carta>[] getFundaciones() {
        return fundaciones;
    }
}