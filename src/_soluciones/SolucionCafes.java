package _soluciones;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;

import _datos.DatosCafes;
import _datos.DatosCafes.Variedad;
import ejercicio1.CafeEdge;
import ejercicio1.CafeVertex;


public class SolucionCafes {

		public static SolucionCafes of_Range(List<Integer> ls) {
			return new SolucionCafes(ls);
		}

	// Ahora en la PI5
	public static SolucionCafes of(GraphPath<CafeVertex, CafeEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionCafes res = of_Range(ls);
		res.path = ls;
		return res;
	}

	// Ahora en la PI5
	private List<Integer> path;

	private Integer benef;

	private List<Variedad> variedades;
	private List<Integer> indices;

	private SolucionCafes() {
		benef = 0;
		variedades = new ArrayList<>();
		indices = new ArrayList<>();
	}

	private SolucionCafes(List<Integer> ls) {
		benef = 0;
		variedades = new ArrayList<>();
		indices = new ArrayList<>();

		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i) > 0) {
				Integer kgs = ls.get(i);
				Integer benefVar = DatosCafes.getBeneficioVariedad(i);
				variedades.add(DatosCafes.getVariedades().get(i));
				indices.add(ls.get(i));
				benef += benefVar * kgs;
			}
		}
	}

	public static SolucionCafes empty() {
		return new SolucionCafes();
	}

	// Ahora en la PI5
	@Override
	public String toString() {
		String res = String.format("Beneficio = %d", benef);
		return path == null ? res : String.format("%s\nPath de la solucion: %s", res, path);
		}
	
}