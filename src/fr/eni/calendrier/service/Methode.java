package fr.eni.calendrier.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Classe contenant les méthodes utilitaires pour la gestion du calendrier
 */
public class Methode {
    private static final int LIGNE = 7;
    private static final int COLONNE = 7;
    private static final int CELL_WIDTH = 3;  // Largeur de chaque cellule
    private static final int TOTAL_WIDTH = COLONNE * CELL_WIDTH;  // Largeur totale (21)
    private static String[][] calendrier = new String[LIGNE][COLONNE];
    private static final String[] ENTETE = {"Lu", "Ma", "Me", "Je", "Ve", "Sa", "Di"};
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", new Locale("fr", "FR"));
    private static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("MMMM", new Locale("fr", "FR"));

    /**
     * Méthode permettant d'afficher le calendrier sous forme de tableau
     */
    public static void affichageCalendrier() {
        // Parcours du tableau à deux dimensions
        for (int ligne = 0; ligne < LIGNE; ligne++) {
            for (int colonne = 0; colonne < COLONNE; colonne++) {
                if (calendrier[ligne][colonne] == null) {
                    System.out.print("   ");
                } else {
                    System.out.printf("%3s", calendrier[ligne][colonne]);
                }
            }
            System.out.println();
        }
    }

    /**
     * Méthode permettant de remplir le tableau avec les jours du mois
     * @param dernierJourDuMois dernier jour du mois
     * @param indice position de départ dans la semaine
     * @param ligneTableau ligne de départ dans le tableau
     */
    public static void alimentationTableau(int dernierJourDuMois, int indice, int ligneTableau) {
        // Remplissage des jours, passage à la ligne suivante si on atteint dimanche
        for (int jour = 1; jour <= dernierJourDuMois; jour++) {
            calendrier[ligneTableau][indice] = String.valueOf(jour);
            indice++;
            if (indice == COLONNE) {
                indice = 0;
                ligneTableau++;
            }
        }
    }

    /**
     * Méthode permettant d'ajouter des espaces avant le premier jour du mois
     * @param indice nombre d'espaces à ajouter
     */
    public static void alimentationBlancsAvantPremierJour(int indice) {
        for (int col = 0; col < indice; col++) {
            calendrier[1][col] = " ";
        }
    }

    /**
     * Méthode permettant de déterminer la position du jour dans la semaine
     * @param dateJour date à analyser
     * @return position dans la semaine (0 pour lundi, 6 pour dimanche)
     */
    public static int traitementJourMois(LocalDate dateJour) {
        return dateJour.getDayOfWeek().getValue() - 1;
    }

    /**
     * Méthode permettant d'initialiser l'en-tête du calendrier
     */
    public static void alimentationPremiereLigneTableau() {
        for (int col = 0; col < COLONNE; col++) {
            calendrier[0][col] = ENTETE[col];
        }
    }

    /**
     * Méthode permettant d'initialiser le tableau avec des espaces
     */
    public static void initialisationTableau() {
        for (int ligne = 0; ligne < LIGNE; ligne++) {
            for (int colonne = 0; colonne < COLONNE; colonne++) {
                calendrier[ligne][colonne] = " ";
            }
        }
    }

    /**
     * Méthode permettant d'afficher l'heure, la date et le mois/année
     * @param dateJour date à afficher
     */
    public static void affichageMoisAnnee(LocalDate dateJour) {
        String time = LocalTime.now().format(TIME_FORMAT);
        String date = dateJour.format(DATE_FORMAT);
        String monthYear = dateJour.format(MONTH_FORMAT).toUpperCase() + " " + dateJour.getYear();
        
        System.out.printf("%"+(TOTAL_WIDTH/2 + time.length()/2)+"s%n", time);
        System.out.println(date);
        System.out.println();
        System.out.printf("%"+(TOTAL_WIDTH/2 + monthYear.length()/2)+"s%n", monthYear);
    }
}