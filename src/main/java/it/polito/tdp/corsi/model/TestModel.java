package it.polito.tdp.corsi.model;

public class TestModel {

	public static void main(String[] args) { //per testare il Model
		
		Model model = new Model();
		System.out.println(model.getCorsiByPeriodo(1));		
		
		System.out.println("++++++++++");
		
		System.out.println(model.getIscrittiByPeriodo(2));
		
		System.out.println("++++++++++");
		
		Corso c = new Corso("01KSUPG", null, null, null);
		for (Studente s : model.getStudentiByCorso(c)) {
			System.out.println(s+"\n");
		}	
		
		System.out.println("++++++++++");
		
		
		
	}

}
