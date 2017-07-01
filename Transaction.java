/*
RICARDO JOÃO LIMA            --  349059
MARCOS JOSE MARTINS PEREIRA  --  374195

*/

import java.util.*;
import java.lang.*;
import java.io.*;
import java.text.*;




public abstract class Transaction {
	
	int id;
	String ts;
	int status;
	String tipo;
	
	public abstract void create_transaction(int id_trans);
	public abstract void commit(int id_trans, Item item);

}