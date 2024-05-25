package solitario;

import javax.swing.*;
import java.util.Stack;

public class Solitario {
    private final Tablero tablero;

    public Solitario() {
        tablero = new Tablero();
        iniciarJuego();
    }

    private void iniciarJuego() {
        boolean jugando = true;
        while (jugando) {
            mostrarTablero();
            String[] opciones = {"Mover Carta", "Sacar del Mazo", "Salir"};
            int opcion = JOptionPane.showOptionDialog(null, "Elige una acción", "Solitario",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            switch (opcion) {
                case 0 -> moverCarta();
                case 1 -> sacarDelMazo();
                case 2 -> jugando = false;
            }
        }
    }

    private void mostrarTablero() {
        StringBuilder estado = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            estado.append("Columna ").append(i + 1).append(": ");
            Stack<Carta> columna = tablero.getColumnas()[i];
            for (Carta carta : columna) {
                estado.append(carta).append(" ");
            }
            estado.append("\n");
        }
        estado.append("Descarte: ");
        for (Carta carta : tablero.getDescarte()) {
            estado.append(carta).append(" ");
        }
        estado.append("\nMazo: ").append(tablero.getMazo().size()).append(" cartas");
        JOptionPane.showMessageDialog(null, estado.toString());
    }

    private void moverCarta() {
        String[] opcionesMovimiento = {"Columna a Columna", "Descarte a Columna", "Columna a Fundación", "Descarte a Fundación"};
        int tipoMovimiento = JOptionPane.showOptionDialog(null, "Elige el tipo de movimiento", "Mover Carta",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcionesMovimiento, opcionesMovimiento[0]);

        switch (tipoMovimiento) {
            case 0 -> moverColumnaAColumna();
            case 1 -> moverDescarteAColumna();
            case 2 -> moverColumnaAFundacion();
            case 3 -> moverDescarteAFundacion();
        }
    }

    private void moverColumnaAColumna() {
        int columnaOrigen = Integer.parseInt(JOptionPane.showInputDialog("Ingresa la columna de origen (1-7):")) - 1;
        int columnaDestino = Integer.parseInt(JOptionPane.showInputDialog("Ingresa la columna de destino (1-7):")) - 1;
        
        Stack<Carta> origen = tablero.getColumnas()[columnaOrigen];
        Stack<Carta> destino = tablero.getColumnas()[columnaDestino];

        if (origen.isEmpty()) {
            JOptionPane.showMessageDialog(null, "La columna de origen está vacía.");
            return;
        }

        Carta cartaOrigen = origen.peek();
        if (destino.isEmpty()) {
            if (cartaOrigen.getValor() == 13) {  // Solo el Rey puede colocarse en una columna vacía
                destino.push(origen.pop());
            } else {
                JOptionPane.showMessageDialog(null, "Solo un Rey puede colocarse en una columna vacía.");
            }
        } else {
            Carta cartaDestino = destino.peek();
            if ((cartaOrigen.getValor() == cartaDestino.getValor() - 1) &&
                !cartaOrigen.getPalo().equals(cartaDestino.getPalo())) {
                destino.push(origen.pop());
            } else {
                JOptionPane.showMessageDialog(null, "Movimiento no válido.");
            }
        }

        if (!origen.isEmpty()) {
            origen.peek().setVisible(true);
        }
    }

    private void moverDescarteAColumna() {
        if (tablero.getDescarte().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El descarte está vacío.");
            return;
        }

        int columnaDestino = Integer.parseInt(JOptionPane.showInputDialog("Ingresa la columna de destino (1-7):")) - 1;
        Stack<Carta> destino = tablero.getColumnas()[columnaDestino];
        Carta cartaDescarte = tablero.getDescarte().peek();

        if (destino.isEmpty()) {
            if (cartaDescarte.getValor() == 13) {  // Solo el Rey puede colocarse en una columna vacía
                destino.push(tablero.getDescarte().pop());
            } else {
                JOptionPane.showMessageDialog(null, "Solo un Rey puede colocarse en una columna vacía.");
            }
        } else {
            Carta cartaDestino = destino.peek();
            if ((cartaDescarte.getValor() == cartaDestino.getValor() - 1) &&
                !cartaDescarte.getPalo().equals(cartaDestino.getPalo())) {
                destino.push(tablero.getDescarte().pop());
            } else {
                JOptionPane.showMessageDialog(null, "Movimiento no válido.");
            }
        }
    }

    private void moverColumnaAFundacion() {
        int columnaOrigen = Integer.parseInt(JOptionPane.showInputDialog("Ingresa la columna de origen (1-7):")) - 1;
        Stack<Carta> origen = tablero.getColumnas()[columnaOrigen];

        if (origen.isEmpty()) {
            JOptionPane.showMessageDialog(null, "La columna de origen está vacía.");
            return;
        }

        Carta cartaOrigen = origen.peek();
        Stack<Carta> fundacion = tablero.getFundaciones()[getFundacionIndex(cartaOrigen.getPalo())];

        if ((fundacion.isEmpty() && cartaOrigen.getValor() == 1) ||
            (!fundacion.isEmpty() && cartaOrigen.getValor() == fundacion.peek().getValor() + 1)) {
            fundacion.push(origen.pop());
        } else {
            JOptionPane.showMessageDialog(null, "Movimiento no válido.");
        }

        if (!origen.isEmpty()) {
            origen.peek().setVisible(true);
        }
    }

    private void moverDescarteAFundacion() {
        if (tablero.getDescarte().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El descarte está vacío.");
            return;
        }

        Carta cartaDescarte = tablero.getDescarte().peek();
        Stack<Carta> fundacion = tablero.getFundaciones()[getFundacionIndex(cartaDescarte.getPalo())];

        if ((fundacion.isEmpty() && cartaDescarte.getValor() == 1) ||
            (!fundacion.isEmpty() && cartaDescarte.getValor() == fundacion.peek().getValor() + 1)) {
            fundacion.push(tablero.getDescarte().pop());
        } else {
            JOptionPane.showMessageDialog(null, "Movimiento no válido.");
        }
    }

    private int getFundacionIndex(String palo) {
        return switch (palo) {
            case "♥" -> 0;
            case "♦" -> 1;
            case "♣" -> 2;
            case "♠" -> 3;
            default -> -1;
        };
    }

    private void sacarDelMazo() {
        if (!tablero.getMazo().isEmpty()) {
            Carta carta = tablero.getMazo().poll();
            carta.setVisible(true);
            tablero.getDescarte().push(carta);
        } else {
            while (!tablero.getDescarte().isEmpty()) {
                tablero.getMazo().add(tablero.getDescarte().pop());
            }
        }
    }

    public static void main(String[] args) {
        new Solitario();
    }
}