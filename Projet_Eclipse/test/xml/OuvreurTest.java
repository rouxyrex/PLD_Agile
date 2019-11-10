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
	public void testAccepteFichier() throws ExceptionXml {
		boolean result = OuvreurDeFichierXml.getInstance().accept(new File("/Projet_Eclipse/Resources/docIncorrect.txt"));
		assertFalse(result);
	}

}
