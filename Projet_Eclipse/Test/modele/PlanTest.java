package modele;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Element;

import xml.LectureXml;

public class PlanTest {
	private static Plan plan;
	private static Intersection interInit;
	private static DemandeLivraison demande;
	private static int listeLg;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		

		LectureXml lecteur = new LectureXml();
		
		plan = lecteur.creerPlan(new File("./Ressources/moyenPlan.xml"));
		demande = lecteur.creerDemandeDeLivraison(plan, new File("./Ressources/demandeMoyen5.xml"));
		
		interInit = plan.getIntersectionById("55444215");
		listeLg = demande.getLivraisons().size() ;
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testDijkstra() {
		LinkedList<Trajet> trajetsTest = plan.Dijkstra(demande, interInit);
		assertNotNull(trajetsTest);
		assertTrue(listeLg<=trajetsTest.size());
	}

}
