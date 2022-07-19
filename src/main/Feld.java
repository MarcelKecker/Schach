package main;

import enums.FigurEnum;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Feld extends JButton {
    private final ImageIcon koenigSchwarz = new ImageIcon(Objects.requireNonNull(Feld.class.getResource("KoenigSchwarz.png")));
    private final ImageIcon dameSchwarz = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("DameSchwarz.png")));
    private final ImageIcon turmSchwarz = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("TurmSchwarz.png")));
    private final ImageIcon laeuferSchwarz = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("LaeuferSchwarz.png")));
    private final ImageIcon pferdSchwarz = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("PferdSchwarz.png")));
    private final ImageIcon bauerSchwarz = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("BauerSchwarz.png")));
    private final ImageIcon koenigWeiss = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("KoenigWeiss.png")));
    private final ImageIcon dameWeiss = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("DameWeiss.png")));
    private final ImageIcon turmWeiss = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("TurmWeiss.png")));
    private final ImageIcon laeuferWeiss = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("LaeuferWeiss.png")));
    private final ImageIcon pferdWeiss = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("PferdWeiss.png")));
    private final ImageIcon bauerWeiss = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("BauerWeiss.png")));

    private Position position;
    private Spielfeld spielfeld;

    Feld(Position position, Spielfeld spielfeld) {
        this.position = position;
        this.spielfeld = spielfeld;
        this.setPreferredSize(new Dimension(100, 100));
        if ((position.getX() % 2 == 0 && position.getY() % 2 == 0) || (position.getX() % 2 == 1 && position.getY() % 2 == 1)) {
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.GREEN.darker().darker().darker());
        }
        updateGraphics();
    }
    private void updateGraphics() {
        if (spielfeld.getFigur(position).equals(FigurEnum.KOENIG_WEISS)) {
            setIcon(koenigWeiss);
        } else if (spielfeld.getFigur(position).equals(FigurEnum.KOENIG_SCHWARZ)) {
            setIcon(koenigSchwarz);
        } else if (spielfeld.getFigur(position).equals(FigurEnum.DAME_WEISS)) {
            setIcon(dameWeiss);
        } else if (spielfeld.getFigur(position).equals(FigurEnum.DAME_SCHWARZ)) {
            setIcon(dameSchwarz);
        } else if (spielfeld.getFigur(position).equals(FigurEnum.TURM_WEISS)) {
            setIcon(turmWeiss);
        } else if (spielfeld.getFigur(position).equals(FigurEnum.TURM_SCHWARZ)) {
            setIcon(turmSchwarz);
        } else if (spielfeld.getFigur(position).equals(FigurEnum.LAEUFER_WEISS)) {
            setIcon(laeuferWeiss);
        } else if (spielfeld.getFigur(position).equals(FigurEnum.LAEFER_SCHWARZ)) {
            setIcon(laeuferSchwarz);
        } else if (spielfeld.getFigur(position).equals(FigurEnum.PFERD_WEISS)) {
            setIcon(pferdWeiss);
        } else if (spielfeld.getFigur(position).equals(FigurEnum.PFERD_SCHWARZ)) {
            setIcon(pferdSchwarz);
        } else if (spielfeld.getFigur(position).equals(FigurEnum.BAUER_WEISS)) {
            setIcon(bauerWeiss);
        } else if (spielfeld.getFigur(position).equals(FigurEnum.BAUER_SCHWARZ)) {
            setIcon(bauerSchwarz);
        }
    }
}
