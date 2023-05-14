package ejercicio2;

import _datos.DatosCursos;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CursoEdge(CursoVertex source, CursoVertex target, Integer action, Double weight)
		implements SimpleEdgeAction<CursoVertex, Integer> {

	public static CursoEdge of(CursoVertex s, CursoVertex t, Integer a) {
		return new CursoEdge(s, t, a, a * DatosCursos.getPrecioCurso(s.index()));
	}

	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}
}
