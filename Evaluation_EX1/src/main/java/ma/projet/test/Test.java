package ma.projet.test;

import ma.projet.classes.Categorie;
import ma.projet.classes.Produit;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.service.ProduitService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.util.HibernateConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateConfig.class);

        ProduitService produitService = context.getBean(ProduitService.class);
        CommandeService commandeService = context.getBean(CommandeService.class);
        LigneCommandeService ligneService = context.getBean(LigneCommandeService.class);

        // === Création des catégories ===
        Categorie cat1 = new Categorie();
        cat1.setCode("C1");
        cat1.setLibelle("Informatique");

        Categorie cat2 = new Categorie();
        cat2.setCode("C2");
        cat2.setLibelle("Électroménager");

        // === Création des produits ===
        Produit p1 = new Produit();
        p1.setReference("LPT100");
        p1.setPrix(8500f); // en dirhams
        p1.setCategorie(cat1);

        Produit p2 = new Produit();
        p2.setReference("MSE200");
        p2.setPrix(250f); // en dirhams
        p2.setCategorie(cat1);

        Produit p3 = new Produit();
        p3.setReference("FRG300");
        p3.setPrix(4200f); // en dirhams
        p3.setCategorie(cat2);

        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);

        // === Création des commandes ===
        Commande c1 = new Commande();
        c1.setDate(LocalDate.of(2024, 11, 10));
        commandeService.create(c1);

        Commande c2 = new Commande();
        c2.setDate(LocalDate.of(2025, 2, 5));
        commandeService.create(c2);

        // === Création des lignes de commande ===
        LigneCommandeProduit l1 = new LigneCommandeProduit();
        l1.setProduit(p1);
        l1.setCommande(c1);
        l1.setQuantite(3);

        LigneCommandeProduit l2 = new LigneCommandeProduit();
        l2.setProduit(p2);
        l2.setCommande(c1);
        l2.setQuantite(5);

        LigneCommandeProduit l3 = new LigneCommandeProduit();
        l3.setProduit(p3);
        l3.setCommande(c2);
        l3.setQuantite(2);

        ligneService.create(l1);
        ligneService.create(l2);
        ligneService.create(l3);

        // === Affichages ===
        System.out.println("\n--- Produits par catégorie Informatique ---");
        List<Produit> produitsCat1 = produitService.findByCategorie(cat1);
        for (Produit p : produitsCat1) {
            System.out.printf("Réf : %-6s | Prix : %-8.2f DH | Catégorie : %s%n",
                    p.getReference(), p.getPrix(), p.getCategorie().getLibelle());
        }

        System.out.println("\n--- Produits par commande c1 ---");
        produitService.findByCommande(c1.getId());

        System.out.println("\n--- Produits commandés entre 2024-01-01 et 2025-12-31 ---");
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2025, 12, 31);
        produitService.findByDateCommande(start, end);

        System.out.println("\n--- Produits prix > 1000 DH ---");
        produitService.findProduitsPrixSuperieur(1000f);

        context.close();
    }
}
