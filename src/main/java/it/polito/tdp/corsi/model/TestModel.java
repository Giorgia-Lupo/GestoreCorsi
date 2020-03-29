package it.polito.tdp.corsi.model;

public class TestModel {

	public static void main(String[] args) { //per testare il Model
		
		Model model = new Model();
		System.out.println(model.getCorsiByPeriodo(1));		
		
		System.out.println(model.getIscrittiByPeriodo(2));
	}

}
