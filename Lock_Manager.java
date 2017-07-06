/*
RICARDO JOÃO LIMA            --  349059
MARCOS JOSE MARTINS PEREIRA  --  374195

*/

import java.util.*;
import java.text.SimpleDateFormat;
import java.lang.Thread;

public class Lock_Manager {
	

	Map<Integer,Integer> lock_table_transaction = new HashMap<Integer,Integer>();
	Map<Integer,String> lock_table_lock = new HashMap<Integer,String>();
	
	Queue<Map<Integer,String>> wait_Q = new LinkedList<Map<Integer,String>>();
	Map<Integer,String> aux = new HashMap<Integer,String>();
	//Map<String,String> aux1 = new HashMap<String,String>();


	public void LS(int id_transacao, Item item){
		
		int verificador = 0;

		try {
			Thread.sleep(1000);
		}catch (Exception ex) {
		  	ex.printStackTrace();
		}

		if (item.transaction_Op.isEmpty()){ // se Lista Tr do item for vazia concede
			try {
				System.out.println("Concedendo permissão...");
				Thread.sleep(1000);
			lock_table_lock.put(item.id,"S");
				lock_table_transaction.put(item.id,id_transacao);
				item.transaction_Op.put(id_transacao,"S");
				System.out.println("Bloqueio (S) da transação " + id_transacao + " no item " + item.id + "  concedido\n");
				String novaTS = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
				item.transaction_TS.put(id_transacao,novaTS);
		}catch (Exception ex) {
		  	ex.printStackTrace();
		}
				

		// Percorrer caso contario e verificar que operação está acessando o item	
		}else{
			for (Map.Entry<Integer,String> entrada : item.transaction_Op.entrySet()) {
				try {
					System.out.println("Analisando a Tabela de BLoqueio...");
					Thread.sleep(1000);
					String c = entrada.getValue();
					if(c == "X"){
						verificador = 1;
					}
				}catch (Exception ex) {
				  	ex.printStackTrace();
				}
				
			}
			// Coloca a Tr na lista de espera
			if (verificador == 1) {
				Map<Integer,String> aux = new HashMap<Integer,String>();
				aux.put(id_transacao,"S");
				wait_Q.add(aux);
				System.out.println("Bloqueio (S) da transação " + id_transacao  + " no item " + item.id +" não concedido\n");
			}else{
				lock_table_lock.put(item.id,"S");
				lock_table_transaction.put(item.id,id_transacao);
				item.transaction_Op.put(id_transacao,"S");
				System.out.println("Bloqueio (S) da transação " + id_transacao +" no item " + item.id + "  concedido\n");
				String novaTS = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
				item.transaction_TS.put(id_transacao,novaTS);
			}
		}
	}

	public void LX(int id_transacao, Item item){

		try {
			Thread.sleep(1000);
		}catch (Exception e) {
			e.printStackTrace();
		}
		// se Lista das Tr do item for vazia concede
		if (item.transaction_Op.isEmpty()){
				lock_table_lock.put(item.id,"X");
				lock_table_transaction.put(item.id,id_transacao);
				item.transaction_Op.put(id_transacao,"X");
				System.out.println("Bloqueio (X) da transação " + id_transacao + " no item " + item.id +"  concedido\n");
				String novaTS = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
				item.transaction_TS.put(id_transacao,novaTS);

		// Coloca logo na lista de espera	
		}else{
			Map<Integer,String> aux = new HashMap<Integer,String>();
			aux.put(id_transacao,"X");
			wait_Q.add(aux);
			System.out.println("Bloqueio (X) da transação " + id_transacao + " no item " + item.id +" não concedido\n");
		}



	}

	public void U(int id_transacao, Item item){

		lock_table_lock.remove(item.id);
		lock_table_transaction.remove(item.id);
	}

	//System.out.println("Tamanho do TS no início: " + item.transaction_TS.size());// Checando a lista

	public void Wait_Die(int id_transacao, Item item, String tipo){ 
		
		Map<Integer,String> lista_tr_ts = new HashMap<Integer,String>();
		lista_tr_ts.putAll(item.transaction_TS);
		
		boolean vazio = lista_tr_ts.isEmpty();
		String timeStamp = null;
		
		// diferente de vazio pq tem elemento. percorre!
		if (!(vazio)) {
			for (Integer entrada : lista_tr_ts.keySet()){ // chave do valor da tabela transaction_TS
				if(entrada == id_transacao){// compara afim de pegar o TS
					timeStamp = lista_tr_ts.get(entrada);
				}				
			}

			if (timeStamp != null){ // Analisar para nao comparar lixo
				for (Integer entrada : lista_tr_ts.keySet()){ 
					if(entrada != id_transacao){ // analiisar N-1 elementos da lista transaction_TS
						if (timeStamp.compareTo(lista_tr_ts.get(entrada)) < 0){ //esq < dir retorna -1
							Map<Integer,String> aux = new HashMap<Integer,String>();
							aux.put(id_transacao,tipo);
							wait_Q.add(aux);
							System.out.println("Colocando de wait_Q em Wait_Die a Transacao "+ id_transacao);
						
						// os TS nunca vao ser iguais ( por causa do Thread.Sleep). caso esq > dir retorn 1
						}else{
							String novaTS = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
							item.transaction_TS.put(id_transacao,novaTS);
							System.out.println("Transação "+ id_transacao + " abortada\n");
						}
					}
				}
			}
		}
	}
	



}


