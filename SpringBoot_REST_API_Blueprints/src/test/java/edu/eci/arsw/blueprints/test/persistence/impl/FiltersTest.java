package edu.eci.arsw.blueprints.test.persistence.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.RedundancyFilter;
import edu.eci.arsw.blueprints.persistence.SubsamplingFilter;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;


public class FiltersTest {
    @Test
    public void shouldRedundancyFilterEliminateRepeatedPoints() throws BlueprintNotFoundException, BlueprintPersistenceException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        RedundancyFilter fr = new RedundancyFilter();
        Point[] juanp = new Point[] { new Point(40, 40), new Point(15, 15), new Point(40, 40), new Point(40, 40),
                new Point(15, 15), new Point(15, 15) };
        Point[] pablop = new Point[] { new Point(15, 14) };
        Blueprint bp0 = new Blueprint("juan", "hola", juanp);
        Blueprint bp1 = new Blueprint("pablo", "adios", pablop);
        Blueprint bp2 = new Blueprint("sebastian", "prueba", new Point[] {});
        ibpp.saveBlueprint(bp0);
        fr.filterBlueprint(bp0);
        Assert.assertEquals(2, bp0.getPoints().size());
        ibpp.saveBlueprint(bp1);
        fr.filterBlueprint(bp1);
        Assert.assertEquals(1, bp1.getPoints().size());
        ibpp.saveBlueprint(bp2);
        fr.filterBlueprint(bp2);
        Assert.assertEquals(0, bp2.getPoints().size());
    }
    @Test
    public void shouldRedundancyFilterEliminateRepeatedPointsForMoreThanOneBlueprint() throws BlueprintNotFoundException, BlueprintPersistenceException {
        RedundancyFilter fr = new RedundancyFilter();
        Point[] juanp = new Point[] { new Point(40, 40), new Point(15, 15), new Point(40, 40), new Point(40, 40),
                new Point(15, 15), new Point(15, 15) };
        Point[] pablop = new Point[] { new Point(15, 14) };
        Blueprint bp0 = new Blueprint("juan", "hola", juanp);
        Blueprint bp1 = new Blueprint("pablo", "adios", pablop);
        Blueprint bp2 = new Blueprint("sebastian", "prueba", new Point[] {});
        Set<Blueprint> blueprints = new HashSet<Blueprint>();
        blueprints.add(bp0);
        blueprints.add(bp1);
        blueprints.add(bp2);
        try {
            fr.filterBlueprints(blueprints);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }
        Object[] blueprintsCo = blueprints.toArray();
        Assert.assertEquals(2, ((Blueprint) blueprintsCo[0]).getPoints().size());
        Assert.assertEquals(1, ((Blueprint) blueprintsCo[1]).getPoints().size());
        Assert.assertEquals(0, ((Blueprint) blueprintsCo[2]).getPoints().size());
    }

    @Test
    public void shouldSubsamplingFilterEliminateSubsamplingPoints() throws BlueprintNotFoundException, BlueprintPersistenceException {
        SubsamplingFilter fs = new SubsamplingFilter();
        Point[] juanp = new Point[] { new Point(10, 10), new Point(14, 12), new Point(19, 20), new Point(34, 25),
                new Point(1, 4),
                new Point(14, 18), new Point(1, 4), new Point(9, 80), new Point(8, 7), new Point(14, 25) };
        Point[] pablop = new Point[] { new Point(1, 2), new Point(2, 6) };
        Blueprint bp0 = new Blueprint("juan", "hola", juanp);
        Blueprint bp1 = new Blueprint("pablo", "adios", pablop);
        Blueprint bp2 = new Blueprint("sebastian", "prueba", new Point[] {});
        try {
            fs.filterBlueprint(bp0);
            fs.filterBlueprint(bp1);
            fs.filterBlueprint(bp2);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(5, bp0.getPoints().size());
        Assert.assertEquals(1, bp1.getPoints().size());
        Assert.assertEquals(0, bp2.getPoints().size());
        Assert.assertEquals(Arrays.asList(new Point[] {}).toString(), bp2.getPoints().toString());
    }

    @Test
    public void shouldSubsamplingFilterEliminateSubsamplingPointsForMoreThanOneBlueprint() throws BlueprintNotFoundException, BlueprintPersistenceException {
        SubsamplingFilter fs = new SubsamplingFilter();
        Point[] juanp = new Point[] { new Point(10, 10), new Point(14, 12), new Point(19, 20), new Point(34, 25),
                new Point(1, 4),
                new Point(14, 18), new Point(1, 4), new Point(9, 80), new Point(8, 7), new Point(14, 25) };
        Point[] pablop = new Point[] { new Point(1, 2), new Point(2, 6) };
        Blueprint bp0 = new Blueprint("juan", "hola", juanp);
        Blueprint bp1 = new Blueprint("pablo", "adios", pablop);
        Blueprint bp2 = new Blueprint("sebastian", "prueba", new Point[] {});
        Set<Blueprint> blueprints = new HashSet<Blueprint>();
        blueprints.add(bp0);
        blueprints.add(bp1);
        blueprints.add(bp2);
        try {
            fs.filterBlueprints(blueprints);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }
        Object[] blueprintsCo = blueprints.toArray();
        Assert.assertEquals(5, ((Blueprint) blueprintsCo[0]).getPoints().size());
        Assert.assertEquals(1, ((Blueprint) blueprintsCo[1]).getPoints().size());
        Assert.assertEquals(0, ((Blueprint) blueprintsCo[2]).getPoints().size());
    }

    
}
