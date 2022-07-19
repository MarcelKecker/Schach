package main;

import enums.FigurEnum;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Spielfeld extends JPanel {

    private static final int SPIELFELD_BREITE = 8;
    private static final int SPIELFELD_HOEHE = 8;
    private Koenig koenigWeiss;
    private Koenig koenigSchwarz;
    private Dame dameWeiss;
    private Dame dameSchwarz;
    private Turm tuermeWeiss1;
    private Turm tuermeWeiss2;
    private Turm tuermeSchwarz1;
    private Turm tuermeSchwarz2;
    private Laeufer laeuferWeiss1;
    private Laeufer laeuferWeiss2;
    private Laeufer laeuferSchwarz1;
    private Laeufer laeuferSchwarz2;
    private Pferd pferdWeiss1;
    private Pferd pferdWeiss2;
    private Pferd pferdSchwarz1;
    private Pferd pferdSchwarz2;
    ArrayList<Bauer> bauerWeiss;
    ArrayList<Bauer> bauerSchwarz;

    public Spielfeld() {
        this.setLayout(new GridLayout(SPIELFELD_HOEHE, SPIELFELD_BREITE));

        ArrayList<Feld> felder = new ArrayList<>();
        for (int i = 0; i < SPIELFELD_BREITE * SPIELFELD_HOEHE; i++) {
            felder.add(new Feld(position(i), this));
            this.add(felder.get(i));
        }
        koenigWeiss = new Koenig(new Position(4, 7));
        koenigSchwarz = new Koenig(new Position(4, 0));
        dameWeiss = new Dame(new Position(3, 7));
        dameSchwarz = new Dame(new Position(3, 0));
        tuermeWeiss1 = new Turm(new Position(0, 7));
        tuermeWeiss2 = new Turm(new Position(7, 7));
        tuermeSchwarz1 = new Turm(new Position(0, 0));
        tuermeSchwarz2 = new Turm(new Position(7, 0));
        laeuferWeiss1 = new Laeufer(new Position(2, 7));
        laeuferWeiss2 = new Laeufer(new Position(5, 7));
        laeuferSchwarz1 = new Laeufer(new Position(2, 0));
        laeuferSchwarz2 = new Laeufer(new Position(5, 0));
        pferdWeiss1 = new Pferd(new Position(1, 7));
        pferdWeiss2 = new Pferd(new Position(6, 7));
        pferdSchwarz1 = new Pferd(new Position(1, 0));
        pferdSchwarz2 = new Pferd(new Position(6, 0));
        ArrayList<Bauer> bauerWeiss = new ArrayList<>();
        ArrayList<Bauer> bauerSchwarz = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            bauerWeiss.add(new Bauer(new Position(i, 6)));
            bauerSchwarz.add(new Bauer(new Position(i, 1)));
        }
    }

    public Position position(int feld) {
        return new Position(feld % SPIELFELD_BREITE, feld / SPIELFELD_BREITE);
    }

    public int feld(Position position) {
        return position.getY() * SPIELFELD_BREITE + position.getX();
    }

    public FigurEnum getFigur(Position position) {
        if (koenigWeiss.istKoenig(position)) {
            return  FigurEnum.KOENIG_WEISS;
        } else if (koenigSchwarz.istKoenig(position)) {
            return  FigurEnum.KOENIG_SCHWARZ;
        } else if (dameWeiss.istDame(position)) {
            return  FigurEnum.DAME_WEISS;
        } else if (dameSchwarz.istDame(position)) {
            return  FigurEnum.DAME_SCHWARZ;
        } else if (laeuferWeiss1.istLaeufer(position)) {
            return  FigurEnum.LAEUFER_WEISS;
        } else if (laeuferWeiss2.istLaeufer(position)) {
            return  FigurEnum.LAEUFER_WEISS;
        } else if (laeuferSchwarz1.istLaeufer(position)) {
            return  FigurEnum.LAEFER_SCHWARZ;
        } else if (laeuferSchwarz2.istLaeufer(position)) {
            return  FigurEnum.LAEFER_SCHWARZ;
        } else if (pferdWeiss1.istPferd(position)) {
            return  FigurEnum.PFERD_WEISS;
        } else if (pferdWeiss2.istPferd(position)) {
            return  FigurEnum.PFERD_WEISS;
        } else if (pferdSchwarz1.istPferd(position)) {
            return  FigurEnum.PFERD_SCHWARZ;
        } else if (pferdSchwarz2.istPferd(position)) {
            return  FigurEnum.PFERD_SCHWARZ;
        } else if (tuermeWeiss1.istTurm(position)) {
            return  FigurEnum.TURM_WEISS;
        } else if (tuermeWeiss2.istTurm(position)) {
            return  FigurEnum.TURM_WEISS;
        } else if (tuermeSchwarz1.istTurm(position)) {
            return  FigurEnum.TURM_SCHWARZ;
        } else if (tuermeSchwarz2.istTurm(position)) {
            return  FigurEnum.TURM_SCHWARZ;
        } else {
            for (int i = 0; i < bauerWeiss.size(); i++) {
                if (bauerWeiss.get(i).istBauer(position)) {
                    return FigurEnum.BAUER_WEISS;
                } else if (bauerSchwarz.get(i).istBauer(position)) {
                    return FigurEnum.BAUER_SCHWARZ;
                }
            }
            return null;
        }
    }
}
