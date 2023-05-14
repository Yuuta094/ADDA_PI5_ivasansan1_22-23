package ejercicio4Manual;

import java.util.List;

import _datos.DatosClientes;
import _soluciones.SolucionClientes;
import us.lsi.common.List2;

public class ClientesState {
	ClientesProblem actual;
	Double acumulado;
	List<Integer> acciones;
	List<ClientesProblem> anteriores;

	private ClientesState(ClientesProblem p, Double a, List<Integer> ls1, List<ClientesProblem> ls2) {
		actual = p;
		acumulado = a;
		acciones = ls1;
		anteriores = ls2;
	}

	public static ClientesState initial() {
		ClientesProblem p = ClientesProblem.initial();
		Double a = 0.;
		List<Integer> ls1 = List2.empty();
		List<ClientesProblem> ls2 = List2.empty();
		return new ClientesState(p, a, ls1, ls2);
	}

	public static ClientesState of(ClientesProblem prob, Double acum, List<Integer> lsa, List<ClientesProblem> lsp) {
		return new ClientesState(prob, acum, lsa, lsp);
	}

	public void forward(Integer a) {
		acumulado += a * DatosClientes.getBeneficio(actual.index());
		acciones.add(a);
		anteriores.add(actual);
		actual = actual.neighbor(a);
	}

	public void back() {
		int last = acciones.size() - 1;
		ClientesProblem prob_ant = anteriores.get(last);
		acumulado = acciones.get(last) * DatosClientes.getBeneficio(prob_ant.index());
		acciones.remove(last);
		anteriores.remove(last);
		actual = prob_ant;

	}

	public List<Integer> alternativas() {
		return actual.actions();
	}

	public Double cota(Integer a) {
		Double weight = DatosClientes.getBeneficio(a);
		// Cota = acumulado + func(a, actual) + h(vecino(actual, a))

		return acumulado + weight + actual.neighbor(a).heuristic();
	}

	public Boolean esSolucion() {
		//Cuando todos los elementos del universo se han cubierto
		return actual.index() == 0 && actual.pendientes().isEmpty();
	}

	public Boolean esTerminal() {
		return actual.index() == 0 && actual.pendientes().isEmpty();
	}

	public SolucionClientes getSolucion() {
		return SolucionClientes.of_format(acciones);
	}

}
