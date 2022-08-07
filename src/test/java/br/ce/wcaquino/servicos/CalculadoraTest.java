
package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exception.NaoPodedividirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {

    private Calculadora calculadora;

    @Before
    public void setup(){
        calculadora = new Calculadora();
    }

    @Test
    public void deveSomarDoisValores(){

        int a = 4;
        int b = 5;

        int result = calculadora.somar(a,b);

        Assert.assertEquals(9, result);
    }

    @Test
    public void deveSubtrairDoisVlores(){
        int a = 4;
        int b = 5;

        int result = calculadora.subtrair(a,b);

        Assert.assertEquals(-1,result);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodedividirPorZeroException {
        int a = 10;
        int b = 2;

        int resultado = calculadora.dividir(a,b) ;

        Assert.assertEquals(5,resultado);
    }

    @Test(expected = NaoPodedividirPorZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws NaoPodedividirPorZeroException {

        int a = 5;
        int b = 0;

        int resultado = calculadora.dividir(a,b);
    }



}
