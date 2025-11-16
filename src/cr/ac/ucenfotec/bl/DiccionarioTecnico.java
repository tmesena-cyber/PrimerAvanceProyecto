package cr.ac.ucenfotec.bl;

import java.util.ArrayList;
import java.util.Collections;

public class DiccionarioTecnico {

    private ArrayList<PalabraTecnica> palabras = new ArrayList<>();

    public DiccionarioTecnico() {
        cargarDiccionarioBase();
    }

    private void cargarDiccionarioBase() {

        // REDES
        palabras.add(new PalabraTecnica("router", "REDES"));
        palabras.add(new PalabraTecnica("switch", "REDES"));
        palabras.add(new PalabraTecnica("latencia", "REDES"));
        palabras.add(new PalabraTecnica("dns", "REDES"));
        palabras.add(new PalabraTecnica("dhcp", "REDES"));
        palabras.add(new PalabraTecnica("wifi", "REDES"));

        // HARDWARE
        palabras.add(new PalabraTecnica("memoria ram", "HARDWARE"));
        palabras.add(new PalabraTecnica("procesador", "HARDWARE"));
        palabras.add(new PalabraTecnica("disco duro", "HARDWARE"));
        palabras.add(new PalabraTecnica("unidad ssd", "HARDWARE"));
        palabras.add(new PalabraTecnica("tarjeta madre", "HARDWARE"));
        palabras.add(new PalabraTecnica("fuente de poder", "HARDWARE"));

        // IMPRESORAS
        palabras.add(new PalabraTecnica("tóner", "IMPRESORAS"));
        palabras.add(new PalabraTecnica("cartucho", "IMPRESORAS"));
        palabras.add(new PalabraTecnica("atasco de papel", "IMPRESORAS"));

        // SOFTWARE
        palabras.add(new PalabraTecnica("controlador", "SOFTWARE"));
        palabras.add(new PalabraTecnica("antivirus", "SOFTWARE"));
        palabras.add(new PalabraTecnica("firewall", "SOFTWARE"));
        palabras.add(new PalabraTecnica("actualización", "SOFTWARE"));
        palabras.add(new PalabraTecnica("navegador", "SOFTWARE"));

        // SEGURIDAD
        palabras.add(new PalabraTecnica("phishing", "SEGURIDAD"));
        palabras.add(new PalabraTecnica("malware", "SEGURIDAD"));
        palabras.add(new PalabraTecnica("ransomware", "SEGURIDAD"));
        palabras.add(new PalabraTecnica("encriptación", "SEGURIDAD"));
        palabras.add(new PalabraTecnica("vulnerabilidad", "SEGURIDAD"));
    }

    public PalabraTecnica buscar(String palabra) {
        if (palabra == null) return null;

        String buscada = palabra.trim().toLowerCase();

        for (PalabraTecnica p : palabras) {
            if (p.getPalabra().equals(buscada)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<PalabraTecnica> listar() {
        ArrayList<PalabraTecnica> copia = new ArrayList<>(palabras);
        Collections.sort(copia, (a, b) -> a.getPalabra().compareTo(b.getPalabra()));
        return copia;
    }
}
