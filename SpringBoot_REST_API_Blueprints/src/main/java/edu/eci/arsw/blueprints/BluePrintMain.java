package edu.eci.arsw.blueprints;

import javax.swing.Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

@SpringBootApplication
public class BluePrintMain implements CommandLineRunner{
    @Autowired
    BlueprintsServices bps;
    public static void main(String[] args) {
        SpringApplication.run(BluePrintMain.class, args);
        
        
    }

    @Override
    public void run(String... args) throws Exception {
        Point[] points = new Point[]{new Point(1, 0), new Point(0, 0), new Point(10, 0), new Point(3, 0)};
        int planos = 5;
        String author2 = "Juan Pablo";
        String author1 = "Sebastian";
        for (int i = 0; i < planos; i++) {
            bps.addNewBlueprint(new Blueprint(author1, "plano " + i,points));
            bps.addNewBlueprint(new Blueprint(author2, "plano " + i,points));
        }
        
        System.out.println("--------PLANOS--------");
        System.out.println(bps.getAllBlueprints());
        System.out.println("--------PLANOS POR AUTOR--------");
        System.out.println("--------PLANOS DE JUAN PABLO--------");
        System.out.println(bps.getBlueprintsByAuthor(author2));
        System.out.println("--------PLANOS DE SEBATIAN--------");
        System.out.println(bps.getBlueprintsByAuthor(author1));
    }
    
}
