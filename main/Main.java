package main;

import xml.LectureXml;

import java.io.IOException;

import plan.Plan;
import ihm.IHM;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		LectureXml l = new LectureXml();
		try {
			Plan p = l.creerPlan("grandPlan.xml"); 
			IHM ihm = new IHM();
			ihm.afficherPlan(p);
			ihm.afficherLivraisonPlan(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
