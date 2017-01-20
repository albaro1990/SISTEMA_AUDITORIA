/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.utilitarios;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author rene.travez
 */
public class Utils {

    public static Double redondearNumero(BigDecimal numero, int lugares, boolean exceso) {
        if (numero == null) {
            return new Double(0);
        }
        return new BigDecimal(numero.doubleValue()).setScale(lugares, exceso ? RoundingMode.HALF_EVEN : RoundingMode.HALF_DOWN).doubleValue();
    }
}
