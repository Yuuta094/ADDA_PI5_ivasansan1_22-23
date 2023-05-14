package ejercicio2Manual;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosCursos;
import ejercicio2.CursoVertex;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record CursosProblem(Integer index, Set<Integer> remaining, Set<Integer> centros) {

	public static CursosProblem of(Integer ind, Set<Integer> rem, Set<Integer> cen) {
		return new CursosProblem(ind, rem, cen);
	}

	public static CursosProblem initial() {
		return of(0, Set2.copy(DatosCursos.getTematicas()), Set2.empty());
	}

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
					// aqui tenemos q replicar la estructura anterior

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

	public CursosProblem neighbor(Integer a) {
		Set<Integer> rest1 = a == 0 ? Set2.copy(remaining)
				: Set2.difference(remaining, DatosCursos.getTematicasCurso(index));

		Set<Integer> coles = Set2.copy(centros);
		if (a == 1) {
			coles.add(DatosCursos.getCentroCurso(index));
		}
		return of(index + 1, rest1, coles);
	}

	public Double heuristic(CursoVertex v1, Predicate<CursoVertex> goal, CursoVertex v2) {
		return v1.remaining().isEmpty() ? 0.
				: IntStream.range(v1.index(), DatosCursos.getNumCursos())
						.filter(c -> !List2.intersection(v1.remaining(), DatosCursos.getTematicasCurso(c)).isEmpty())
						.mapToDouble(i -> DatosCursos.getPrecioCurso(i)).min().orElse(100.);
	}
}
