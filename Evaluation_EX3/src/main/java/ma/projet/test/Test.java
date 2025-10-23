package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateUtil.class);

        FemmeService femmeService = context.getBean(FemmeService.class);
        HommeService hommeService = context.getBean(HommeService.class);
        MariageService mariageService = context.getBean(MariageService.class);

        // === Création des femmes ===
        Femme f1 = new Femme(); f1.setCin("F001"); f1.setNom("Amina"); f1.setPrenom("Bennani"); f1.setDateNaissance(LocalDate.of(1980, 5, 10));
        Femme f2 = new Femme(); f2.setCin("F002"); f2.setNom("Khadija"); f2.setPrenom("Mansouri"); f2.setDateNaissance(LocalDate.of(1983, 8, 25));
        Femme f3 = new Femme(); f3.setCin("F003"); f3.setNom("Hajar"); f3.setPrenom("El Idrissi"); f3.setDateNaissance(LocalDate.of(1985, 2, 18));
        Femme f4 = new Femme(); f4.setCin("F004"); f4.setNom("Fatima"); f4.setPrenom("Rachidi"); f4.setDateNaissance(LocalDate.of(1978, 12, 2));
        Femme f5 = new Femme(); f5.setCin("F005"); f5.setNom("Salma"); f5.setPrenom("Toumi"); f5.setDateNaissance(LocalDate.of(1981, 11, 30));

        Arrays.asList(f1, f2, f3, f4, f5).forEach(femmeService::create);

        // === Création des hommes ===
        Homme h1 = new Homme(); h1.setNom("Youssef"); h1.setPrenom("El Amrani");
        Homme h2 = new Homme(); h2.setNom("Ahmed"); h2.setPrenom("Berrada");
        Homme h3 = new Homme(); h3.setNom("Reda"); h3.setPrenom("Zerouali");

        Arrays.asList(h1, h2, h3).forEach(hommeService::create);

        // === Création des mariages ===
        Mariage m1 = new Mariage(); m1.setHomme(h1); m1.setFemme(f1); m1.setDateDebut(LocalDate.of(2005, 6, 12)); m1.setNbrEnfant(2);
        Mariage m2 = new Mariage(); m2.setHomme(h1); m2.setFemme(f2); m2.setDateDebut(LocalDate.of(2010, 9, 3)); m2.setNbrEnfant(1);
        Mariage m3 = new Mariage(); m3.setHomme(h1); m3.setFemme(f3); m3.setDateDebut(LocalDate.of(2018, 5, 7)); m3.setNbrEnfant(3);
        Mariage m4 = new Mariage(); m4.setHomme(h1); m4.setFemme(f4); m4.setDateDebut(LocalDate.of(2000, 3, 10)); m4.setDateFin(LocalDate.of(2004, 8, 20)); m4.setNbrEnfant(0);

        Mariage m5 = new Mariage(); m5.setHomme(h2); m5.setFemme(f1); m5.setDateDebut(LocalDate.of(2002, 4, 15)); m5.setNbrEnfant(1);
        Mariage m6 = new Mariage(); m6.setHomme(h2); m6.setFemme(f2); m6.setDateDebut(LocalDate.of(2007, 11, 2)); m6.setNbrEnfant(2);
        Mariage m7 = new Mariage(); m7.setHomme(h2); m7.setFemme(f3); m7.setDateDebut(LocalDate.of(2015, 9, 22)); m7.setNbrEnfant(1);
        Mariage m8 = new Mariage(); m8.setHomme(h2); m8.setFemme(f4); m8.setDateDebut(LocalDate.of(2019, 1, 5)); m8.setNbrEnfant(0);

        Mariage m9 = new Mariage(); m9.setHomme(h3); m9.setFemme(f5); m9.setDateDebut(LocalDate.of(2020, 2, 18)); m9.setNbrEnfant(2);
        Mariage m10 = new Mariage(); m10.setHomme(h3); m10.setFemme(f2); m10.setDateDebut(LocalDate.of(2023, 7, 10)); m10.setNbrEnfant(0);

        Arrays.asList(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10).forEach(mariageService::create);

        // === Affichages ===
        System.out.println("\n--- Liste des Femmes ---");
        femmeService.findAll().forEach(f -> System.out.printf("%-5s %-25s %-25s %-12s%n",
                f.getCin(), f.getNom(), f.getPrenom(), f.getDateNaissance()));

        // Femme la plus âgée
        Femme plusAgee = femmeService.findAll().stream()
                .min((fA, fB) -> fA.getDateNaissance().compareTo(fB.getDateNaissance()))
                .orElse(null);
        System.out.println("\nFemme la plus âgée : " +
                (plusAgee != null ? plusAgee.getCin() + " " + plusAgee.getNom() + " " + plusAgee.getPrenom() : "N/A"));

        // Épouses de Youssef
        System.out.println("\n--- Épouses de l'homme " + h1.getNom() + " ---");
        hommeService.afficherEpousesEntreDates(h1.getId(), LocalDate.of(1990, 1, 1), LocalDate.of(2030, 12, 31));

        // Nombre d’enfants de Amina entre 2000 et 2025
        System.out.println("\n--- Nombre d'enfants de " + f1.getNom() + " entre 2000 et 2025 ---");
        int nbEnfants = femmeService.nombreEnfantsEntreDates(f1.getId(), LocalDate.of(2000, 1, 1), LocalDate.of(2025, 12, 31));
        System.out.println("Nombre d'enfants : " + nbEnfants);

        // Femmes mariées deux fois ou plus
        System.out.println("\n--- Femmes mariées deux fois ou plus ---");
        femmeService.femmesMarieesAuMoinsDeuxFois()
                .forEach(f -> System.out.printf("%-5s %-25s %-25s%n", f.getCin(), f.getNom(), f.getPrenom()));

        // Hommes mariés à 4 femmes entre 1990 et 2030
        System.out.println("\n--- Hommes mariés à 4 femmes entre 1990 et 2030 ---");
        hommeService.afficherNombreHommesAvecQuatreFemmes(LocalDate.of(1990, 1, 1), LocalDate.of(2030, 12, 31));

        // Mariages de Youssef avec détails
        System.out.println("\n--- Mariages de l'homme " + h1.getNom() + " avec détails ---");
        mariageService.afficherMariagesAvecDetails(h1.getId());

        context.close();
    }
}
