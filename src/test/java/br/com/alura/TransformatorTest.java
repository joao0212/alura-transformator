package br.com.alura;

import br.com.alura.domain.Endereco;
import br.com.alura.domain.Pessoa;
import br.com.alura.domain.PessoaDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

public class TransformatorTest {

    Pessoa pessoa = new Pessoa("João", 32, "04333958210");
    Endereco endereco = new Endereco("Brasília", 37);

    @Test
    public void shouldTransform() throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Transformator transformator = new Transformator();
        PessoaDTO transformated = transformator.transform(pessoa);

        Assertions.assertInstanceOf(PessoaDTO.class, transformated);

        Assertions.assertEquals("João", transformated.getNome());
        Assertions.assertEquals(32, transformated.getIdade());
        Assertions.assertEquals("04333958210", transformated.getCpf());
    }

    @Test
    public void shouldNotTransform() {
        Assertions.assertThrows(ClassNotFoundException.class, () -> {
            Transformator transformator = new Transformator();
            transformator.transform(endereco);
        });
    }
}
