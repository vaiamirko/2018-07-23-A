package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graphs;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;
import it.polito.tdp.newufosightings.model.Evento.TipoEvento;

public class Simulatore {
	
	PriorityQueue<Evento> queue;
	
	int T1;
	
	int alfa;
	
	Map<String,Double> MappaStati;
	Map<String,State> MappaStatiId;
	Model model;
	
	public void init(int T1,int alfa,Model model,int anno,String shape) {
		NewUfoSightingsDAO dao = new NewUfoSightingsDAO();
		this.alfa=alfa;
		this.T1=T1;
		this.model=model;
		int year=anno;
		String forma=shape;
		MappaStati=new HashMap<String, Double>();
		MappaStatiId=new HashMap<String,State>();
		
		queue = new PriorityQueue<>();
		//iniziazlizzo la mappa degli stati a defcon 5
		for(State s : model.getGrafo().vertexSet()) {
			MappaStati.put(s.getId(), (double) 5);
			MappaStatiId.put(s.getId(), s);
			
		}
		
		for(Sighting avv : dao.loadavvistamentianno(anno, shape)) {
			
			queue.add(new Evento(TipoEvento.AVVISTAMENTO,avv.getDatetime(),avv));
					
			
		}
		
		
		
		
	}
	
	public void run() {
		
		
		
		
		while(!queue.isEmpty()) {
			Evento e =queue.poll();
			
			switch(e.getTipoEvento()) {
			case AVVISTAMENTO:
				//se il valore di defcon è maggiore di 1 aggionro
				String temp = e.getAvvistamento().getState();
				State attuale = MappaStatiId.get(e.getAvvistamento().getState().toUpperCase());
				
				double DEFCON = MappaStati.get(attuale.getId());
				if(MappaStati.get(attuale.getId())>=1.5) {
					MappaStati.put(attuale.getId(),
							MappaStati.get(attuale.getId())-1 );
					
					queue.add(new Evento(TipoEvento.TRASCORSI_T_GIORNI_INCREMENTO_PURO,e.getData().plusDays(T1),e.getAvvistamento()));
					System.out.println("STATO INCREMENTATO");
							
																		}
				Random ran = new Random();
				
				List<State> vicini = new ArrayList<>(Graphs.neighborListOf(model.getGrafo(),attuale));
				
				for(State vicino : vicini) {
					int prob = ran.nextInt(101);//RITORNA un numero tra 0 e 100
									if(prob<alfa) {
										System.out.println("VICINO INCREMENTATO");
										if(MappaStati.get(vicino.getId())>=1.5)
										MappaStati.put(vicino.getId(), MappaStati.get(vicino.getId())-0.5);
										
										queue.add(new Evento(TipoEvento.TRASCORSI_T_GIORNI_INCREMENTO_VICINO,e.getData().plusDays(T1),e.getAvvistamento()));
												
												}
					
					
					
											}
				
				break;
			
			case TRASCORSI_T_GIORNI_INCREMENTO_PURO:
				String st= e.getAvvistamento().getState();
				State puro = MappaStatiId.get(st.toUpperCase());
				if(MappaStati.get(puro.getId())<=4)
				MappaStati.put(puro.getId(), MappaStati.get(puro.getId())+1);
				
				
				break;
				
			case TRASCORSI_T_GIORNI_INCREMENTO_VICINO:
				String st1= e.getAvvistamento().getState();
				State collaterale = MappaStatiId.get(st1.toUpperCase());
				
				if(MappaStati.get(collaterale.getId())<=4.5)
				MappaStati.put(collaterale.getId(), MappaStati.get(collaterale.getId())+0.5);
				break;

				
			}
			
		}
			
			
		}
		
	public String Stampa()
	{
		String result="\n";
		for(String s : MappaStati.keySet()) {
			
			result+=" "+s+MappaStati.get(s)+"\n";
			
		}
		
		return result;
		
	}
}
