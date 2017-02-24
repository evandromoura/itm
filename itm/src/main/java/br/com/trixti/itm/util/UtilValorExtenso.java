package br.com.trixti.itm.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Evandro Moura
 */
public class UtilValorExtenso {

    private static Map<String, String> centenas;

    private static Map<String, String> dezenas;

    private static Map<String, String> unidades;

    static {
	centenas = new HashMap<String, String>();
	centenas.put("1", "cento");
	centenas.put("2", "duzentos");
	centenas.put("3", "trezentos");
	centenas.put("4", "quatrocentos");
	centenas.put("5", "quinhentos");
	centenas.put("6", "seiscentos");
	centenas.put("7", "setecentos");
	centenas.put("8", "oitocentos");
	centenas.put("9", "novecentos");

	dezenas = new HashMap<String, String>();
	dezenas.put("1", "dez");
	dezenas.put("2", "vinte");
	dezenas.put("3", "trinta");
	dezenas.put("4", "quarenta");
	dezenas.put("5", "cinquenta");
	dezenas.put("6", "sessenta");
	dezenas.put("7", "setenta");
	dezenas.put("8", "oitenta");
	dezenas.put("9", "noventa");
	dezenas.put("11", "onze");
	dezenas.put("12", "doze");
	dezenas.put("13", "treze");
	dezenas.put("14", "quatorze");
	dezenas.put("15", "quinze");
	dezenas.put("16", "dezesseis");
	dezenas.put("17", "dezessete");
	dezenas.put("18", "dezoito");
	dezenas.put("19", "dezenove");

	unidades = new HashMap<String, String>();
	unidades.put("1", "um");
	unidades.put("2", "dois");
	unidades.put("3", "tr�s");
	unidades.put("4", "quatro");
	unidades.put("5", "cinco");
	unidades.put("6", "seis");
	unidades.put("7", "sete");
	unidades.put("8", "oito");
	unidades.put("9", "nove");
    }

    /**
     * M�todo que obt�m o valor extenso da moedas
     * 
     * @param value double
     * @return String
     */

    public static String valorExtensoMoeda(double value) {
	StringBuffer buf = new StringBuffer();
	int centavos = new Double(Math.round((value - Math.floor(value)) * 100)).intValue();
	if (value > 999999999.99) {
	    long bilhoes = ((long) value) / 1000000000L;
	    if (bilhoes > 1) {
		buf.append(translate(bilhoes) + " bilh�es");
	    } else {
		buf.append(translate(bilhoes) + " bilh�o");
	    }
	    value = value % (bilhoes * 1000000000);
	}
	if (value > 999999.99) {
	    if (buf.length() > 0) {
		buf.append(" e ");
	    }
	    long milhoes = ((long) value) / 1000000L;
	    if (milhoes > 1) {
		buf.append(translate(milhoes) + " milh�es");
	    } else {
		buf.append(translate(milhoes) + " milh�o");
	    }
	    value = value % (milhoes * 1000000);
	}
	if (value > 999.99) {
	    if (buf.length() > 0) {
		buf.append(" e ");
	    }
	    long milhares = ((long) value) / 1000L;
	    buf.append(translate(milhares) + " mil");
	    value = value % (milhares * 1000);
	}
	if (value > 0.99) {
	    if (buf.length() > 0) {
		buf.append(" e ");
	    }
	    if (((long)value) > 1) {
		buf.append(translate((long)value) + " reais ");
	    } else {
		buf.append(translate((long)value) + " real ");
	    }
	}
	if (centavos > 0) {
	    if (buf.length() > 0) {
		buf.append(" e ");
	    }
	    if (centavos > 1) {
		buf.append(translate(centavos) + " centavos");
	    } else {
		buf.append(translate(centavos) + " centavo");
	    }
	}
	if (buf.length() == 0) {
	    buf.append("zero reais");
	}
	return buf.toString();
    }
    
    /**
     * M�todo que obt�m o valor Extenso
     * 
     * @param value double
     * @return String
     */


    public static String valorExtenso(double value) {
	StringBuffer buf = new StringBuffer();
	int centavos = new Double(Math.round((value - Math.floor(value)) * 100))
		.intValue();

	// Bilh�es
	if (value > 999999999.99) {
	    long bilhoes = ((long) value) / 1000000000L;

	    if (bilhoes > 1) {
		buf.append(translate(bilhoes) + " bilh�es");
	    } else {
		buf.append(translate(bilhoes) + " bilh�o");
	    }

	    value = value % (bilhoes * 1000000000);
	}

	// Milh�es
	if (value > 999999.99) {
	    if (buf.length() > 0) {
		buf.append(" e ");
	    }

	    long milhoes = ((long) value) / 1000000L;

	    if (milhoes > 1) {
		buf.append(translate(milhoes) + " milh�es");
	    } else {
		buf.append(translate(milhoes) + " milh�o");
	    }

	    value = value % (milhoes * 1000000);
	}

	// Milhares
	if (value > 999.99) {
	    if (buf.length() > 0) {
		buf.append(" e ");
	    }

	    long milhares = ((long) value) / 1000L;

	    buf.append(translate(milhares) + " mil");

	    value = value % (milhares * 1000);
	}

	if (value > 0.99) {
	    if (buf.length() > 0) {
		buf.append(" e ");
	    }
	    if (((long)value) > 1) {
		buf.append(translate((long)value));
	    } else {
		buf.append(translate((long)value));
	    }
	}

	if (centavos > 0) {
	    if (buf.length() > 0) {
		buf.append(" e ");
	    }

	    if (centavos > 1) {
		buf.append(translate(centavos));
	    } else {
		buf.append(translate(centavos));
	    }
	}

	if (buf.length() == 0) {
	    buf.append("zero");
	}

	return buf.toString();
    }

    private static String translate(long value) {
	String centena = completeZeroLeft(value);
	StringBuffer buf = new StringBuffer();
	String d1 = centena.substring(0, 1);
	String d2 = centena.substring(1, 2);
	String d3 = centena.substring(2, 3);
	if (centenas.containsKey(d1)) { buf.append(centenas.get(d1).toString()); }
	if (d2.equals("0")) {
	    if (unidades.containsKey(d3)) {
		if (buf.length() > 0) {  buf.append(" e " + unidades.get(d3).toString());
		} else { buf.append(unidades.get(d3).toString()); }
	    }
	} else if (d2.equals("1") && !d3.equals("0")) {
	    d2 = centena.substring(1, 3);
	    if (dezenas.containsKey(d2)) {
		if (buf.length() > 0) { buf.append(" e " + dezenas.get(d2).toString());
		} else { buf.append(dezenas.get(d2).toString()); }
	    }
	} else {
	    if (dezenas.containsKey(d2)) {
		if (buf.length() > 0) { buf.append(" e " + dezenas.get(d2).toString());
		} else { buf.append(dezenas.get(d2).toString()); }
	    }
	    if (unidades.containsKey(d3)) {
		if (buf.length() > 0) { buf.append(" e " + unidades.get(d3).toString());
		} else { buf.append(unidades.get(d3).toString()); }
	    }
	}
	return buf.toString();
    }

    private static String completeZeroLeft(long value) {
	NumberFormat f = new DecimalFormat("#,##000");

	return f.format(new Long(value));
    }

    /**
     * Construtor
     */
    private UtilValorExtenso() {
    }

}