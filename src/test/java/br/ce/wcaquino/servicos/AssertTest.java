package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;


public class AssertTest {

    @Test
    public void teste(){

        Assert.assertTrue(true);
        Assert.assertFalse(false);

        Assert.assertEquals("Erro de comparação",1,1);
        Assert.assertEquals(0.5233, 0.523,0.001);

        int i = 5;
        Integer i2 = 5;

        Assert.assertEquals(Integer.valueOf(i),i2);
        Assert.assertEquals(i,i2.intValue());

        //actual: recebeu
        Assert.assertEquals("bola","bola");
        Assert.assertNotEquals("bola","casa");
        Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
        Assert.assertTrue("bola".startsWith("bo"));

        Usuario u1 = new Usuario("Usuario 1");
        Usuario u2 = new Usuario("Usuario 1");
        Usuario u3 = null;

        Assert.assertEquals(u1,u2);

        //Mesmo objeto
        Assert.assertSame(u2,u2);
        Assert.assertNotSame(u1,u2);

        Assert.assertNull(u3);
        Assert.assertNotNull(u2);

    }


}
