package xml;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class OuvreurTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAccepteFichier_rejet() throws ExceptionXml {
		boolean result = OuvreurDeFichierXml.getInstance().accept(new File("/Projet_Eclipse/Ressources/docIncorrect.txt"));
		assertFalse(result);
	}
	
	@Test
	public void testAccepteFichier_accepte() throws ExceptionXml {
		boolean result = OuvreurDeFichierXml.getInstance().accept(new File("/Projet_Eclipse/Ressources/petitPlan.xml"));
		assertTrue(result);
	}

}
