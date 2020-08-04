package generadorIdentificador;

import java.util.concurrent.atomic.AtomicInteger;

public interface GeneradorIdentificador {
    final static AtomicInteger ingreso = new AtomicInteger(0);
    final static AtomicInteger egreso = new AtomicInteger(0);
    final static AtomicInteger presupuesto = new AtomicInteger(0);

    static String generarIdentificadorIngreso(String format) {
        return "I"+String.format(format, ingreso.incrementAndGet());
    }

    static String generarIdentificadorEgreso(String format) {
        return "E"+String.format(format, egreso.incrementAndGet());
    }

    static String generarIdentificadorPresupuesto(String format) {
        return "P"+String.format(format, presupuesto.incrementAndGet());
    }
}
