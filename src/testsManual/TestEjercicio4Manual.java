package testsManual;

import java.util.List;

import _datos.DatosClientes;
import _utils.TestsPI5;
import ejercicio4Manual.ClientesBT;
import us.lsi.common.String2;

public class TestEjercicio4Manual {

	public static void main(String[] args) {
		List.of(1, 2).forEach(num_test -> {

			DatosClientes.iniDatos("ficheros/Ejercicio4DatosEntrada" + num_test + ".txt");
			ClientesBT.search();
			ClientesBT.getSoluciones().forEach(s -> String2.toConsole("Solucion obtenida: %s\n", s));
			TestsPI5.line("*");
		});
	}

}