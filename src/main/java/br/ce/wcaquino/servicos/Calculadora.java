package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exception.NaoPodedividirPorZeroException;

public class Calculadora {

    public int somar(int a, int b){
        return a+b;
    }

    public int subtrair(int a, int b) {
        return a - b;
    }

    public int dividir(int a, int b) throws NaoPodedividirPorZeroException {

        if(b > 0){
            return a/b;
        }
        throw new NaoPodedividirPorZeroException();
    }
}
