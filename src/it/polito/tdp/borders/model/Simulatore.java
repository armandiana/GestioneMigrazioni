package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
	//qual � il Modello e quindi lo stato del sistema ad ogni passo,
	//ci� che mi dice dove posso muovermi � il grafo che abbiamo creato nel punto uno.
	private Graph<Country, DefaultEdge>grafo;
	
	//quali sono i tipi di evento/coda prioritaria
	//l'unico evento da modellare � quello che succede all'evento T cio� che i migranti si spostano. 
	//non ci sono altri eventi che potrebbero succedere.
	//c'� un solo evento che viene modellata in una classe separata.
	private PriorityQueue<Evento>queque;
	
	
	//quali sono i parametri della simulazione
	private int N_MIGRANTI=1000;
	private Country partenza;
	
	//quali sono i valori di output
	//numero di passi che ci abbiamo messo per distribuire i migranti:
	private int T;
	private Map<Country, Integer>stanziali;
	
	//inizializzazione del simulatore
	
	public void init(Country partenza, Graph<Country, DefaultEdge>grafo) {
		//ricevo i parametri
		this.partenza=partenza;
		this.grafo=grafo;
	
		//impostazione stato iniziale, in cui devo impostare il tempo iniziale.
		this.T=1;
		
		//creo le altre strutture dati:
		stanziali=new HashMap<Country, Integer>();//la mappa � vuota, quindi dobbiamo inizializzarla con lo stato iniziale.
		for(Country c: this.grafo.vertexSet()) {
			stanziali.put(c, 0);
		}
	
		queque=new PriorityQueue<Evento>();
		//inserisco l'evento di partenza
		//inizializzo la coda con il primo evento
		this.queque.add(new Evento(T, N_MIGRANTI, partenza));
	}
	
	//run del simulatore
	public void run() {
		//estraggo un evento per volta dalla coda e lo eseguo finch� la coda non si svuota. 
		//questa � la condizione di terminazione. questa simulazione � molto smeplice perch� abbiamo un unico tipo di evento
		Evento e;
		
		while((e=queque.poll())!=null) {
			//c'� almeno un evento e quindi eseguo l'evento.
			//dobbiamo prendere le persone e spostarle in tutti gli stati 
			this.T=e.getT();//in questo modo teniamo traccia all'ultimo T a cui siamo arrivati.
			int nPersone=e.getN();
			Country stato=e.getStato();
			
			//spostiamo il 50% di queste persone in parti uguali negli stati confinanti.
			//troviamo gli stati confinanti:
			List<Country>confinanti=Graphs.neighborListOf(this.grafo, stato);
			//quante persone spostare nei confinanti?
			int migranti=(nPersone/2) / confinanti.size();//la divisione viene trancata per difetto gi� cos�.
			
			if(migranti>0) {
				//le persone si possono muovere: 
				//devo creare altri eventi di migrazione per ogni stato confinante
				for(Country cc: confinanti) {
					queque.add(new Evento(e.getT() +1, migranti, cc));
				}
			}
			
			int stanziali=nPersone-migranti*confinanti.size();
			this.stanziali.put(stato, this.stanziali.get(stato)+stanziali);
		}
	}
	
	public int getT() {
		return T;
	}
	
	public Map<Country, Integer>getMap(){
		return this.stanziali;
	}
	
	
	
	
	
	
	
	

}
