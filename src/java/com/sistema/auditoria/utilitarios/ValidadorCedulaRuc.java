package com.sistema.auditoria.utilitarios;

@SuppressWarnings("all")
public class ValidadorCedulaRuc {

    private static final Short PROVINCIA_INICIAL = 1;
    private static final Short PROVINCIA_FINAL = 24;

    public static TipoDocumentoIdentidad validaRucCedula(String rucCedula) {

        boolean nat = false;
        boolean pri = false;
        boolean pub = false;
        int[] personaNatural = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int[] sociedadPublica = {3, 2, 7, 6, 5, 4, 3, 2};
        int[] sociedadExtranjera = {4, 3, 2, 7, 6, 5, 4, 3, 2};
        int modulo = 11;

        if (rucCedula.length() == 13 || rucCedula.length() == 10) {
            try { // comprobamos si ruc es num�rico
                long l = Long.parseLong(rucCedula);
            } catch (NumberFormatException e) {
                return TipoDocumentoIdentidad.RUC_CEDULA_NO_VALIDA;
            }
            try {
                int provincia = Integer.parseInt(rucCedula.substring(0, 2));

                if (provincia < PROVINCIA_INICIAL || provincia > PROVINCIA_FINAL) {// recomendable
                    // utilizar
                    // archivo de
                    // propiedades
                    return TipoDocumentoIdentidad.RUC_CEDULA_NO_VALIDA;
                }
                /*
				 * Aqui almacenamos los digitos de la cedula en variables.
                 */

                int d3 = Integer.parseInt(rucCedula.substring(2, 3));

                if ((d3 == 7) || (d3 == 8)) {
                    // System.out.print("El tercer d�gito es inv�lido");
                    return TipoDocumentoIdentidad.RUC_CEDULA_NO_VALIDA;
                }
                // RUC para Personas Naturales o c�dula
                if (d3 < 6) { // RUC para Personas Naturales o c�dula
                    nat = true;

                    int numeros[] = new int[9];
                    for (int i = 0; i < 9; i++) {
                        numeros[i] = Integer.parseInt(rucCedula.substring(i, i + 1));
                        personaNatural[i] *= numeros[i];
                        if (personaNatural[i] >= 10) {
                            personaNatural[i] -= 9;
                        }
                    }
                    modulo = 10;
                } else if (d3 == 6) { // del RUC para Sociedades P�blicas
                    pub = true;
                    int numeros[] = new int[8];
                    for (int i = 0; i < 8; i++) {
                        if (i == 8) {
                            sociedadPublica[i] = 0;
                        } else {
                            numeros[i] = Integer.parseInt(rucCedula.substring(i, i + 1));
                            sociedadPublica[i] *= numeros[i];
                        }
                    }
                    // p9=0;
                } else if (d3 == 9) { // es RUC para Sociedades Privadas y
                    // Extranjeros sin c�dula
                    pri = true;

                    int numeros[] = new int[9];
                    for (int i = 0; i < 9; i++) {
                        numeros[i] = Integer.parseInt(rucCedula.substring(i, i + 1));
                        sociedadExtranjera[i] *= numeros[i];
                    }
                }
                if (pub == true) {
                    int suma = 0;
                    for (int i = 0; i < 8; i++) {
                        suma += sociedadPublica[i];
                    }
                    int residuo = suma % modulo;
                    int digitVerificador;
                    if (residuo == 0) {
                        digitVerificador = 0;
                    } else {
                        digitVerificador = modulo - residuo;
                    }
                    if (digitVerificador != Integer.parseInt(rucCedula.substring(8, 9))) {
                        return TipoDocumentoIdentidad.RUC_CEDULA_NO_VALIDA;
                        // System.out.print("El ruc de la empresa del sector p�blico es incorrecto.");
                    }
                    if (rucCedula.length() == 10) {
                        if (!(rucCedula.substring(9, 13)).equals("0001")) {
                            return TipoDocumentoIdentidad.RUC_CEDULA_NO_VALIDA;
                            // System.out.print("El ruc de la empresa del sector p�blico debe terminar con 0001");
                        } else {
                            return TipoDocumentoIdentidad.RUC_SOCIEDADES_PUBLICAS;
                        }
                    }
                    return TipoDocumentoIdentidad.CEDULA_VALIDA;
                } else if (pri == true) {
                    int suma = 0;
                    for (int i = 0; i < 9; i++) {
                        suma += sociedadExtranjera[i];
                    }
                    int residuo = suma % modulo;
                    int digitVerificador;
                    if (residuo == 0) {
                        digitVerificador = 0;
                    } else {
                        digitVerificador = modulo - residuo;
                    }
                    if (digitVerificador != Integer.parseInt(rucCedula.substring(9, 10))) {
                        return TipoDocumentoIdentidad.RUC_CEDULA_NO_VALIDA;
                        // System.out.print("El ruc de la empresa del sector privado es incorrecto.");
                    }
                    if (rucCedula.length() > 10) {
                        if (!(rucCedula.substring(10, 13)).equals("001")) {
                            return TipoDocumentoIdentidad.RUC_CEDULA_NO_VALIDA;
                            // System.out.print("El ruc de la empresa del sector privado debe terminar con 001.");
                        } else {
                            return TipoDocumentoIdentidad.RUC_SOCIEDADES_PRIVADAS_EXTRANJEROS;
                        }
                    }
                    return TipoDocumentoIdentidad.CEDULA_VALIDA;
                } else if (nat == true) {
                    int suma = 0;
                    for (int i = 0; i < 9; i++) {
                        suma += personaNatural[i];
                    }
                    int residuo = suma % modulo;
                    int digitVerificador;
                    if (residuo == 0) {
                        digitVerificador = 0;
                    } else {
                        digitVerificador = modulo - residuo;
                    }
                    if (digitVerificador != Integer.parseInt(rucCedula.substring(9, 10))) {
                        return TipoDocumentoIdentidad.RUC_CEDULA_NO_VALIDA;
                        // System.out.print("El ruc de la empresa del sector privado es incorrecto.");
                    }
                    if (rucCedula.length() > 10) {
                        if (!(rucCedula.substring(10, 13)).equals("001")) {
                            return TipoDocumentoIdentidad.RUC_CEDULA_NO_VALIDA;
                            // System.out.print("El ruc de la empresa del sector privado debe terminar con 001.");
                        } else {
                            return TipoDocumentoIdentidad.RUC_PERSONAS_NATURALES;
                        }
                    }
                    return TipoDocumentoIdentidad.CEDULA_VALIDA;
                }
            } catch (NumberFormatException e) {
                // LOG.error("{}: {} ", nombreMetodo, e);
                return TipoDocumentoIdentidad.RUC_CEDULA_NO_VALIDA;
            }
        }
        return TipoDocumentoIdentidad.RUC_CEDULA_NO_VALIDA; // ruc o c�dula
        // incorrecta
    }

  
    public static boolean isCedulaValido(String cedula) {
        return validaRucCedula(cedula) == TipoDocumentoIdentidad.CEDULA_VALIDA;
    }

   
    public static boolean isRucValido(String ruc) {
        switch (validaRucCedula(ruc)) {
            case RUC_SOCIEDADES_PRIVADAS_EXTRANJEROS: // 1
            case RUC_SOCIEDADES_PUBLICAS:// 2
            case RUC_PERSONAS_NATURALES:// 3
                return true;
            default:
                return false;
        }
    }

    public static boolean isRucCedulaValido(String rucCedula) {
        switch (validaRucCedula(rucCedula)) {
            case RUC_SOCIEDADES_PRIVADAS_EXTRANJEROS: // 1
            case RUC_SOCIEDADES_PUBLICAS:// 2
            case RUC_PERSONAS_NATURALES:// 3
            case CEDULA_VALIDA:// 4
                return true;
            default:
                return false;
        }
    }

}
