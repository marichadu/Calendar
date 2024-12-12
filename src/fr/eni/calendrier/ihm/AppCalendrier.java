package fr.eni.calendrier.ihm;

import fr.eni.calendrier.service.Methode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Classe principale pour l'affichage et la navigation du calendrier
 */
public class AppCalendrier {

    /**
     * Méthode principale du programme
     * @param args arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // Déclaration des variables
        Scanner scanner = new Scanner(System.in);
        LocalDate dateJour = LocalDate.now();
        boolean continuer = true;

        while(continuer) {
            // Affichage du calendrier courant
            afficherCalendrier(dateJour);
            
            // Affichage du menu
            afficherMenu();
            
            // Traitement du choix utilisateur
            String choix = scanner.nextLine().toLowerCase();
            switch(choix) {
                case "p": // Mois précédent
                    dateJour = dateJour.minusMonths(1);
                    break;
                case "s": // Mois suivant
                    dateJour = dateJour.plusMonths(1);
                    break;
                case "r": // Rechercher une date
                    System.out.println("Entrez une date (jj/mm/aaaa) ou appuyez sur Entrée pour la date actuelle :");
                    dateJour = lireDate(scanner);
                    break;
                case "q": // Quitter
				    System.out.println("Au revoir!");
                    continuer = false;
                    break;
                default:
                    System.out.println("Option invalide");
                    break;
            }
        }
        scanner.close();
    }

    /**
     * Affiche le calendrier pour une date donnée
     * @param dateJour la date pour laquelle afficher le calendrier
     */
    private static void afficherCalendrier(LocalDate dateJour) {
        int indice = 0;
        int dernierJourDuMois = 0;
        int ligneTableau = 1;

        // Affichage du mois et de l'année
        Methode.affichageMoisAnnee(dateJour);
        
        // Initialisation du tableau avec des espaces
        Methode.initialisationTableau();
        
        // Alimentation de la première ligne (jours de la semaine)
        Methode.alimentationPremiereLigneTableau();
        
        // Positionnement sur le premier jour du mois
        dateJour = dateJour.minusDays(dateJour.getDayOfMonth() - 1L);
        
        // Calcul de l'indice du premier jour
        indice = Methode.traitementJourMois(dateJour);
        
        // Remplissage des espaces avant le premier jour
        Methode.alimentationBlancsAvantPremierJour(indice);
        
        // Récupération du nombre de jours dans le mois
        dernierJourDuMois = dateJour.lengthOfMonth();
        
        // Remplissage des jours du mois
        Methode.alimentationTableau(dernierJourDuMois, indice, ligneTableau);
        
        // Positionnement sur le dernier jour du mois
        dateJour = dateJour.plusDays(dernierJourDuMois - 1L);
        
        // Affichage final du calendrier
        Methode.affichageCalendrier();
    }

    /**
     * Affiche le menu des options disponibles
     */
    private static void afficherMenu() {
        System.out.println("\n[p] Mois précédent - [s] Mois suivant - [r] Rechercher une date - [q] Quitter");
        System.out.print("Votre choix : ");
    }

    /**
     * Lit et valide une date saisie par l'utilisateur
     * @param scanner le scanner pour lire l'entrée utilisateur
     * @return la date validée ou la date du jour si aucune saisie
     */
    private static LocalDate lireDate(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return LocalDate.now();
        }

        while (true) {
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Format de date incorrect. Veuillez réessayer (jj/mm/aaaa) :");
                input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    return LocalDate.now();
                }
            }
        }
    }
}