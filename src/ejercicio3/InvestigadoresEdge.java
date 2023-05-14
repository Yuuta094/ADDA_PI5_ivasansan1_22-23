package ejercicio3;

import java.util.List;

import _datos.DatosInvestigadores;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record InvestigadoresEdge(InvestigadoresVertex source, InvestigadoresVertex target, Integer action,
		Double weight) implements SimpleEdgeAction<InvestigadoresVertex, Integer> {

	public static InvestigadoresEdge of(InvestigadoresVertex s, InvestigadoresVertex t, Integer a) {
		Integer j = s.index() % DatosInvestigadores.getNumTrabajos();
		Double peso = 0.;

		List<Integer> listaTarget = t.distribucion().get(j);
		List<Integer> listaSource = s.distribucion().get(j);

		if (listaSource.stream().allMatch(x -> x.equals(0))) {
			return new InvestigadoresEdge(s, t, a, 0.);
		} else {

			peso = listaTarget.stream().allMatch(x -> x.equals(0)) ? DatosInvestigadores.getCalidadTrabajo(j) : 0.;

			return new InvestigadoresEdge(s, t, a, peso);
		}
	}
}