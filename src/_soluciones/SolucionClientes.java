package _soluciones;

import java.util.List;

import org.jgrapht.GraphPath;

import _datos.DatosClientes;
import ejercicio4.ClientesEdge;
import ejercicio4.ClientesVertex;

public class SolucionClientes implements Comparable<SolucionClientes> {

	public static SolucionClientes of_format(List<Integer> ls) {
		return new SolucionClientes(ls);
	}

// Ahora en la PI5
	public static SolucionClientes of(GraphPath<ClientesVertex, ClientesEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionClientes res = of_format(ls);
		res.path = ls;
		return res;
	}

	private Double total;
	private Double kms;

	// Ahora en la PI5
	private List<Integer> path;

	private SolucionClientes(List<Integer> ls) {
		kms = DatosClientes.getPeso(0, ls.get(0));
		total = DatosClientes.getBeneficio(ls.get(0)) - kms;
		for (int i = 1; i < ls.size(); i++) {
			if (i == ls.size() - 1) {
				total += DatosClientes.getBeneficio(ls.get(i))
						- (kms + DatosClientes.getPeso(ls.get(i - 1), ls.get(i)));
			} else {
				kms += DatosClientes.getPeso(ls.get(i - 1), ls.get(i));
				total += DatosClientes.getBeneficio(ls.get(i)) - kms;
			}
		}

	}

	// Ahora en la PI5
	@Override
	public String toString() {
		String res = String.format("Beneficio total:" + total + "\nKMs: " + kms);
		return path == null ? res : String.format("%s\nPath de la solucion partiendo desde 0: %s", res, path);
	}

	@Override
	public int compareTo(SolucionClientes s) {
		return total.compareTo(s.total);
	}

}