package xml;

import java.io.File;

import javax.swing.JFileChooser;

import javax.swing.filechooser.*;


public class OuvreurDeFichierXml extends FileFilter {// Singleton

	

	private static OuvreurDeFichierXml instance = null;

	private OuvreurDeFichierXml(){}

	protected static OuvreurDeFichierXml getInstance(){

		if (instance == null) instance = new OuvreurDeFichierXml();

		return instance;

	}



 	public File ouvre(boolean lecture) throws ExceptionXml{

 		int returnVal;

 		JFileChooser jFileChooserXML = new JFileChooser();

        jFileChooserXML.setFileFilter(this);

        jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (lecture)

         	returnVal = jFileChooserXML.showOpenDialog(null);

        else

         	returnVal = jFileChooserXML.showSaveDialog(null);

        if (returnVal != JFileChooser.APPROVE_OPTION) 

        	throw new ExceptionXml("Probleme a l'ouverture du fichier");

        return new File(jFileChooserXML.getSelectedFile().getAbsolutePath());

 	}

 	

 	@Override

    public boolean accept(File f) {

    	if (f == null) return false;

    	if (f.isDirectory()) return true;

    	String extension = getExtension(f);

    	if (extension == null) return false;

    	return extension.contentEquals("xml");

    }



	@Override

	public String getDescription() {

		return "Fichier XML";

	}



    private String getExtension(File f) {

	    String filename = f.getName();

	    int i = filename.lastIndexOf('.');

	    if (i>0 && i<filename.length()-1) 

	    	return filename.substring(i+1).toLowerCase();

	    return null;

   }

}