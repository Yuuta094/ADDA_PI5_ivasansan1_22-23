package ejercicio1;

import _datos.DatosCafes;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CafeEdge(CafeVertex source, CafeVertex target, Integer action, Double weight)
		implements SimpleEdgeAction<CafeVertex, Integer> {

	public static CafeEdge of(CafeVertex s, CafeVertex t, Integer a) {
		return new CafeEdge(s, t, a, a * Double.valueOf(DatosCafes.getBeneficioVariedad(s.index())));
	}
}
