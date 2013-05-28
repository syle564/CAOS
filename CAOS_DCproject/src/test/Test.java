package test;
	


import SQL_CON.MySQL;

public class Test {
	public static void main(String[] args) {
		MySQL.createLoadOperation
		("2013-06-02 10:10:10","2013-06-02 10:40:10", 2, 1);
		System.out.println(DU.createDate());
	}

}
