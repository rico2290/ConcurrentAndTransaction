/*
RICARDO JOÃO LIMA            --  349059
MARCOS JOSE MARTINS PEREIRA  --  374195

*/

public class Main  {
	
	public static void main(String[] args) {
		
		


		Tr_Manager T1 = new Tr_Manager();
		Lock_Manager L1 = new Lock_Manager();
		
		

		Item item1 = new Item(1);
		Item item2 = new Item(2);
		Item z = new Item(3);
		
		
		T1.create_transaction(1);
		L1.LS(1,item1);

		T1.create_transaction(2);
		T1.create_transaction(3);

		L1.LX(3,item1);
		L1.LS(2,item1);
		L1.LX(1,item2);
		
		T1.commit(1,item1);
		T1.commit(1,item2);
		L1.LS(2,item1);

		L1.Wait_Die(1,item1,"S");
		L1.Wait_Die(2,item1,"S");

		T1.commit(2,item1);
		
	}


}


