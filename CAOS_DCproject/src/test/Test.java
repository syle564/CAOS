package test;
	




import model.LoadInfo;
import SQL_CON.MySQL;

public class Test {
	public static void main(String[] args) {
		for(LoadInfo lo:MySQL.getDatesForDock("'2013-06-02'"))
		System.out.println(lo);
		MySQL.calculateAndCreateLoad(1,4);
		MySQL.closeConnection();
	}

}
