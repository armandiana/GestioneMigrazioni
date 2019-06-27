package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>{//ci fossero più tipi di vento , all'interno della classe ci sarebbe un enum per numerarli
	
	private int t; //tempo nel quale si verifica
	private int n; //numero migranti che sono arrivate e che di conseguenza si sposteranno.
	private Country stato; //paese in cui le persone arrivano, e da cui si sposteranno.
	
	public Evento(int t, int n, Country stato) {
		super();
		this.t = t;
		this.n = n;
		this.stato = stato;
	}

	public int getT() {
		return t;
	}

	public int getN() {
		return n;
	}

	public Country getStato() {
		return stato;
	}

	//a questo punto dobbiamo implementare il comparable 
	@Override
	public int compareTo(Evento o) {
		return this.t-o.t;//sto facendo l'ordinamento grazie al tempo.
		}
	
	
	
}
