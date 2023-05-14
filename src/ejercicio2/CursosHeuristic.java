package ejercicio2;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosCursos;
import us.lsi.common.List2;

public class CursosHeuristic {
	// Se explica en practicas.
	public static Double heuristic(CursoVertex v1, Predicate<CursoVertex> goal, CursoVertex v2) {
		return v1.remaining().isEmpty() ? 0.
				: IntStream.range(v1.index(), DatosCursos.getNumCursos())
						.filter(i -> !List2.intersection(v1.remaining(), DatosCursos.getTematicasCurso(i)).isEmpty())
						.mapToDouble(i -> DatosCursos.getPrecioCurso(i)).min().orElse(100.);
	}
}
