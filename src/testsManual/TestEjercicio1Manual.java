package testsManual;

import java.util.List;

import _datos.DatosCafes;
import _utils.TestsPI5;
import ejercicio1Manual.CafePDR;
import us.lsi.common.String2;

public class TestEjercicio1Manual {

	public static void main(String[] args) {
		List.of(1, 2, 3).forEach(num_test -> {

			DatosCafes.iniDatos("ficheros/Ejercicio1DatosEntrada" + num_test + ".txt");
			String2.toConsole("Solucion obtenida: %s\n", CafePDR.search());
			TestsPI5.line("*");
		});
	}
}
