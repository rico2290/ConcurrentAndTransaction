/*
RICARDO JOÃO LIMA            --  349059
MARCOS JOSE MARTINS PEREIRA  --  374195

*/

import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.Thread;

public class Tr_Manager extends Transaction{

	int tr = 0;

	public void create_transaction(int tr_id){

		this.id = tr_id;
		tr = tr + 1; 
		this.status = 0;
		
		}


	public void commit(int id_tr, Item item){
		this.status = 1;
		item.transaction_Op.remove(id_tr);
	}

	

}


