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
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/Test1_FormatXml.xml"));
		
	}
	
	
	@Test
	public void testCreerPlan_PlanExiste() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/Test2_PlanExiste.xml"));
		assertNotNull(plan);
		assertNotNull(plan.getIntersections());
		assertNotNull(plan.getTroncons());
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_nbNoeuds() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/Test3_nbNoeuds.xml"));
		
	}

	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_nbIntersections() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/Test4_nbIntersections.xml"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_nbTroncons() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/Test5_nbTroncons.xml"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_TronconLgNeg() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/Test6_TronconLgNeg.xml"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_TronconOrigineNull() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/Test7_TronconOrigineNull.xml"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_TronconDestinationNull() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/Test8_TronconDestinationNull.xml"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_IntersectionLat() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/Test9_IntersectionLat.xml"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerPlan_IntersectionLong() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/Test10_IntersectionLong.xml"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerDemandeDeLivraison_FormatXML() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/petitPlan.xml"));
		DemandeLivraison demande = lecteur.creerDemandeDeLivraison(plan, new File("./Ressources/Test11_Demande_FormatXML.xml"));
	}
	
	@Test
	public void testCreerDemandeDeLivraison_LivraisonExiste() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/petitPlan.xml"));
		DemandeLivraison demande = lecteur.creerDemandeDeLivraison(plan, new File("./Ressources/Test12_Demande_LivraisonExiste.xml"));
		assertNotNull(demande);
		assertNotNull(demande.getLivraisons());
		assertNotNull(demande.getEntrepot());
		assertNotNull(demande.getHeureDepart());
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerDemandeDeLivraison_EntrepotNull() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/petitPlan.xml"));
		DemandeLivraison demande = lecteur.creerDemandeDeLivraison(plan, new File("./Ressources/Test13_Demande_EntrepotNull.xml"));
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerDemandeDeLivraison_MauvaisPlan() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/petitPlan.xml"));
		DemandeLivraison demande = lecteur.creerDemandeDeLivraison(plan , new File("./Ressources/Test14_Demande_MauvaisPlan.xml"));
	}
	
	@Test(expected=ExceptionXml.class)
	public void testCreerDemandeDeLivraison_DureeNeg() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/petitPlan.xml"));
		DemandeLivraison demande = lecteur.creerDemandeDeLivraison(plan, new File("./Ressources/Test15_Demande_DureeNeg.xml"));
	}
	@Test(expected=ExceptionXml.class)
	public void testDoublonPlan_FormatXML() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/Test16_IdNonUnique.xml"));
		
	}
	
	@Test(expected=ExceptionXml.class)
	public void testOrigineEgaleDestination_FormatXML() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/Test17_OrigineEgaleDestination.xml"));
		
	}
	@Test(expected=ExceptionXml.class)
	public void testDoublonTroncon_FormatXML() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/Test18_DoublonTroncon.xml"));
		
	}
	@Test(expected=ExceptionXml.class)
	public void testDoublon_Livraison() throws NumberFormatException, IOException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Plan plan = lecteur.creerPlan(new File("./Ressources/petitPlan.xml"));
		DemandeLivraison demande = lecteur.creerDemandeDeLivraison(plan, new File("./Ressources/Test19_DoublonLivraison.xml"));
	}
	
	
}
