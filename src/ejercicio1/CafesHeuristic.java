package ejercicio1;

import java.util.function.Predicate;

import _datos.DatosCafes;

public class CafesHeuristic {

	public static Double heuristic(CafeVertex v1, Predicate<CafeVertex> goal, CafeVertex v2) {

		Double res = 0.;
		if (v1.index()<DatosCafes.getNumVariedades()) {
			res+=100.;
			}
		return 150000.;
		}

}
