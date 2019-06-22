package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
	
	
	Graph<State, DefaultWeightedEdge> grafo;
	NewUfoSightingsDAO dao = new NewUfoSightingsDAO();
	Map<String,State> MappaStati;
	
	public void CreaGrafo(int anno,String shape) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		MappaStati = new HashMap<String, State>();
		
		Graphs.addAllVertices(grafo, dao.loadAllStates());
		
		for(State stato : grafo.vertexSet()) {
			MappaStati.put(stato.getId(), stato);
		}
		
		List<Collegamento> links = new ArrayList<>(dao.loadAllColl(MappaStati, anno, shape));
		
		for(Collegamento c : links) {
			
			grafo.addEdge(c.getStato1(), c.getStato2());
			DefaultWeightedEdge e = grafo.getEdge(c.getStato1(), c.getStato2());
			grafo.setEdgeWeight(grafo.getEdge(c.getStato1(), c.getStato2()), c.getPeso());
			
		}
		System.out.format("creato grafo con %d vertici e %d archi", grafo.vertexSet().size(),grafo.edgeSet().size());
		
		
	}
	
	public String StampaStato() {
		
		String s="\n";
		
		List<DefaultWeightedEdge> arc;
		for(State st  : grafo.vertexSet()) {
			double somma=0;
			 //arc = new ArrayList<>(grafo.incomingEdgesOf(st));
			 
			List<State> vicini = Graphs.neighborListOf(grafo, st);
			
			for(State vicino :vicini) {
				somma+=grafo.getEdgeWeight(grafo.getEdge(st, vicino));
				
				
				
			}
			
			 /*for(DefaultWeightedEdge e : arc) {
				 DefaultWeightedEdge ev = e;
				 somma+=grafo.getEdgeWeight(e);
				 
			 }*/
			 
			 s+="\n"+st.getName()+" somma : "+somma;
			
		}
		
		return s;
	}

	public Graph<State, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(Graph<State, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}

	public Map<String, State> getMappaStati() {
		return MappaStati;
	}

	public void setMappaStati(Map<String, State> mappaStati) {
		MappaStati = mappaStati;
	}

	
	
}
