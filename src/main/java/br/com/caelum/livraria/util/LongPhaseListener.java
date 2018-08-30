package br.com.caelum.livraria.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;




/*Nesse exercício vamos implementar o PhaseListener para acompanhar o 
  ciclo da vida JSF, igual foi apresentado na aula.*/

public class LongPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("FASE: " + event.getPhaseId());
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
