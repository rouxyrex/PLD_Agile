package xml;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import modele.DemandeLivraison;
import modele.Plan;

public class LectureXMLtests {

	private LectureXml lecteur;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		lecteur = new LectureXml();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_FormatXML() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("/Projet_Eclipse/Resources/Test1_FormatXml.xml"));
		
	}
	
	
	@Test
	public void testCreerPlan_PlanExiste() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/Test2_PlanExiste"));
		assertNotNull(plan);
		assertNotNull(plan.getIntersections());
		assertNotNull(plan.getTroncons());
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_nbNoeuds() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/Test3_nbNoeuds"));
		
	}

	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_nbIntersections() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/Test4_nbIntersections"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_nbTroncons() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/Test5_nbTroncons"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_TronconLgNeg() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/Test6_TronconLgNeg"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_TronconOrigineNull() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/Test7_TronconOrigineNull"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_TronconDestinationNull() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/Test8_TronconDestinationNull"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_IntersectionLat() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/Test9_IntersectionLat"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_IntersectionLong() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/Test10_IntersectionLong"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerDemandeDeLivraison_FormatXML() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/petitPlan"));
		DemandeLivraison demande = lecteur.creerDemandeDeLivraison(plan, new File("./../../../XMLTest/Test11_Demande_FormatXML"));
	}
	
	@Test
	public void testCreerDemandeDeLivraison_LivraisonExiste() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/petitPlan"));
		DemandeLivraison demande = lecteur.creerDemandeDeLivraison(plan, new File("./../../../XMLTest/Test12_Demande_LivraisonExiste"));
		assertNotNull(demande);
		assertNotNull(demande.getLivraisons());
		assertNotNull(demande.getEntrepot());
		assertNotNull(demande.getHeureDepart());
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerDemandeDeLivraison_EntrepotNull() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/petitPlan"));
		DemandeLivraison demande = lecteur.creerDemandeDeLivraison(plan, new File("./../../../XMLTest/Test13_Demande_EntrepotNull"));
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerDemandeDeLivraison_MauvaisPlan() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/petitPlan"));
		DemandeLivraison demande = lecteur.creerDemandeDeLivraison(plan , new File("./../../../XMLTest/Test14_Demande_MauvaisPlan"));
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerDemandeDeLivraison_DureeNeg() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./../../../XMLTest/petitPlan"));
		DemandeLivraison demande = lecteur.creerDemandeDeLivraison(plan, new File("./../../../XMLTest/Test15_Demande_DureeNeg"));
	}
	
	
	
}
