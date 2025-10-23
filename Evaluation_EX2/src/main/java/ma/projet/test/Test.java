package ma.projet.test;

import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.util.HibernateUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateUtil.class);

        EmployeService employeService = context.getBean(EmployeService.class);
        EmployeTacheService employeTacheService = context.getBean(EmployeTacheService.class);
        ProjetService projetService = context.getBean(ProjetService.class);
        TacheService tacheService = context.getBean(TacheService.class);

        // === Création des employés ===
        Employe emp1 = new Employe();
        emp1.setNom("Karim");
        emp1.setPrenom("El Amrani");
        employeService.create(emp1);

        Employe emp2 = new Employe();
        emp2.setNom("Sara");
        emp2.setPrenom("Benali");
        employeService.create(emp2);

        // === Création des projets ===
        Projet proj1 = new Projet();
        proj1.setNom("Application Mobile Santé");
        proj1.setChef(emp1);
        proj1.setDateDebut(LocalDate.of(2025, 3, 1));
        proj1.setDateFin(LocalDate.of(2025, 6, 30));
        projetService.create(proj1);

        Projet proj2 = new Projet();
        proj2.setNom("Système de Gestion RH");
        proj2.setChef(emp2);
        proj2.setDateDebut(LocalDate.of(2025, 4, 10));
        proj2.setDateFin(LocalDate.of(2025, 8, 15));
        projetService.create(proj2);

        // === Création des tâches ===
        Tache t1 = new Tache();
        t1.setNom("Conception UI/UX");
        t1.setProjet(proj1);
        t1.setPrix(7000f); // en dirhams
        t1.setDateDebut(LocalDate.of(2025, 3, 5));
        t1.setDateFin(null);
        tacheService.create(t1);

        Tache t2 = new Tache();
        t2.setNom("Développement API");
        t2.setProjet(proj2);
        t2.setPrix(12000f); // en dirhams
        t2.setDateDebut(LocalDate.of(2025, 4, 15));
        t2.setDateFin(null);
        tacheService.create(t2);

        Tache t3 = new Tache();
        t3.setNom("Intégration et Tests");
        t3.setProjet(proj1);
        t3.setPrix(18000f); // en dirhams
        t3.setDateDebut(LocalDate.of(2025, 4, 1));
        t3.setDateFin(LocalDate.of(2025, 5, 10));
        tacheService.create(t3);

        Tache t4 = new Tache();
        t4.setNom("Déploiement");
        t4.setProjet(proj1);
        t4.setPrix(22000f); // en dirhams
        t4.setDateDebut(LocalDate.of(2025, 5, 15));
        t4.setDateFin(LocalDate.of(2025, 5, 25));
        tacheService.create(t4);

        // === Association Employé - Tâche ===
        EmployeTache et1 = new EmployeTache();
        et1.setEmploye(emp1);
        et1.setTache(t1);
        employeTacheService.create(et1);

        EmployeTache et2 = new EmployeTache();
        et2.setEmploye(emp1);
        et2.setTache(t3);
        employeTacheService.create(et2);

        EmployeTache et3 = new EmployeTache();
        et3.setEmploye(emp2);
        et3.setTache(t2);
        employeTacheService.create(et3);

        EmployeTache et4 = new EmployeTache();
        et4.setEmploye(emp1);
        et4.setTache(t4);
        employeTacheService.create(et4);

        // === Tests des méthodes ===
        System.out.println("\n--- Tâches d'un employé ---");
        employeService.afficherTachesParEmploye(emp1.getId());

        System.out.println("\n--- Projets gérés par l'employé ---");
        employeService.afficherProjetsGeresParEmploye(emp1.getId());

        System.out.println("\n--- Tâches planifiées pour le projet ---");
        projetService.afficherTachesPlanifieesParProjet(proj1.getId());

        System.out.println("\n--- Tâches réalisées pour le projet ---");
        projetService.afficherTachesRealiseesParProjet(proj1.getId());

        System.out.println("\n--- Tâches avec un prix > 10 000 DH ---");
        tacheService.afficherTachesPrixSuperieurA1000(); // méthode existante, garde la logique

        System.out.println("\n--- Tâches réalisées entre deux dates ---");
        LocalDate debut = LocalDate.of(2025, 3, 1);
        LocalDate fin = LocalDate.of(2025, 5, 30);
        tacheService.afficherTachesEntreDates(debut, fin);

        context.close();
    }
}
