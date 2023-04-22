package co.uniquindio.pr2.agenda.test;

import co.uniquindio.pr2.agenda.model.IReunion;

public class Main {

	public static void main(String[] args) {
		IReunion reunion = new IReunion() {

			@Override
			public void agendar() {
				System.out.println("hola");
				System.out.println("hola");
				System.out.println("hola");
			}
		};
	}
}
