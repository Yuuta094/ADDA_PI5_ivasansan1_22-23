package ejercicio2;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import _datos.DatosCursos;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CursoVertex(Integer index, Set<Integer> remaining, Set<Integer> centros)
		implements VirtualVertex<CursoVertex, CursoEdge, Integer> {
	public static CursoVertex of(Integer i, Set<Integer> set, Set<Integer> centros) {
		return new CursoVertex(i, set, centros);
	}

	public static CursoVertex initial() {
		return of(0, Set2.copy(DatosCursos.getTematicas()), Set2.empty());
	}

	public static Predicate<CursoVertex> goal() {
		return v -> v.index() == DatosCursos.getNumCursos();
	}

	public static Predicate<CursoVertex> goalHasSolution() {
		return v -> v.remaining().isEmpty();
	}

	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();

		if (index < DatosCursos.getNumCursos()) {
			if (remaining.isEmpty()) {
				alternativas = List2.of(0);
			} else {
				Set<Integer> restantesActualizados = Set2.difference(remaining, DatosCursos.getTematicasCurso(index));
				if (index == DatosCursos.getNumCursos() - 1) {

					if (centros.contains(DatosCursos.getCentroCurso(index))
							|| (centros.size() < DatosCursos.maxCentros)) {
						alternativas = restantesActualizados.isEmpty() ? List2.of(1) : List2.of(0);
					}

				} else if (restantesActualizados.equals(remaining)) {
					alternativas = List2.of(0);

				} else {

					if (centros.contains(DatosCursos.getCentroCurso(index))
							|| (centros.size() < DatosCursos.maxCentros)) {
						alternativas = List2.of(0);
						alternativas.add(1);
					} else {
						alternativas = List2.of(0);
					}
				}
			}
		}
		return alternativas;
	}

	@Override
	public CursoVertex neighbor(Integer a) {
		Set<Integer> rest1 = a == 0 ? Set2.copy(remaining)
				: Set2.difference(remaining, DatosCursos.getTematicasCurso(index));
		Set<Integer> coles = Set2.copy(centros);
		if (a == 1) {
			coles.add(DatosCursos.getCentroCurso(index));
		}

		return of(index + 1, rest1, coles);
	}

	@Override
	public CursoEdge edge(Integer a) {
		return CursoEdge.of(this, neighbor(a), a);
	}

	// el greedy del voraz:
	public CursoEdge greedyEdge() {
		CursoEdge res = null;
		Set<Integer> restantesActualizados = Set2.difference(remaining, DatosCursos.getTematicasCurso(index));

		if (centros.contains(DatosCursos.getCentroCurso(index)) || (centros.size() < DatosCursos.maxCentros)) {
			res = restantesActualizados.equals(remaining) ? edge(0) : edge(1);
		} else {
			res = edge(0);
		}
		return res;
	}

	public String toString() {
		return String.format("%d; %d", index, remaining.size());
	}

}