package it.polito.tdp.newufosightings.db;

public class TestDAO {

	public static void main(String[] args) {

		NewUfoSightingsDAO dao = new NewUfoSightingsDAO();

		System.out.println(dao.loadAllStates());
		System.out.println(dao.loadavvistamentianno(2000, "light"));
	}

}
