package it.polito.tdp.newufosightings.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	
	public enum TipoEvento{
		
		AVVISTAMENTO,
		TRASCORSI_T_GIORNI_INCREMENTO_PURO,
		TRASCORSI_T_GIORNI_INCREMENTO_VICINO,
		
		
	}
	
	
	private TipoEvento TipoEvento;
	
	private LocalDateTime data;
	
	
	private Sighting avvistamento;


	public Evento(it.polito.tdp.newufosightings.model.Evento.TipoEvento tipoEvento, LocalDateTime data,
			Sighting avvistamento) {
		super();
		TipoEvento = tipoEvento;
		this.data = data;
		this.avvistamento = avvistamento;
	}


	public TipoEvento getTipoEvento() {
		return TipoEvento;
	}


	public void setTipoEvento(TipoEvento tipoEvento) {
		TipoEvento = tipoEvento;
	}


	public LocalDateTime getData() {
		return data;
	}


	public void setData(LocalDateTime data) {
		this.data = data;
	}


	public Sighting getAvvistamento() {
		return avvistamento;
	}


	public void setAvvistamento(Sighting avvistamento) {
		this.avvistamento = avvistamento;
	}


	@Override
	public String toString() {
		return String.format("Evento [TipoEvento=%s, data=%s]", TipoEvento, data);
	}


	@Override
	public int compareTo(Evento e) {
		// TODO Auto-generated method stub
		return this.data.compareTo(e.getData());
	}
	
	
	

}
