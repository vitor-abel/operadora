package com.darksun.service;

import com.darksun.model.Linha;
import com.darksun.model.Plano;
import com.darksun.model.type.Status;
import com.darksun.repository.LinhaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LinhaServiceTest {

    @InjectMocks
    private LinhaService service;

    @Mock
    private LinhaRepository repository;

    List<Linha> listaLinhas;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listaLinhas = new ArrayList<>();
        Linha linha = new Linha(1L, "21", "999999999", 1.00, LocalDate.parse("2023-10-05"), LocalDate.parse("2023-12-05"), null, null, Status.ATIVO);
        Linha linha2 = new Linha(2L, "21", "888888888", 0.00, LocalDate.parse("2023-10-06"), LocalDate.parse("2023-12-06"), null, null, Status.ATIVO);
        listaLinhas.add(linha);
        listaLinhas.add(linha2);
    }

    @Test
    void criar() {
        Linha linha = new Linha(null, "21", "999999999", 0.00, LocalDate.parse("2023-10-05"), LocalDate.parse("2023-12-05"), null, null, Status.ATIVO);
        when(repository.save(linha)).thenReturn(listaLinhas.get(0));
        service.criar(linha);
        verify(repository, times(1)).save(any());
    }

    @Test
    void buscar() {
        when(repository.findAll()).thenReturn(listaLinhas);
        List<Linha> linhas = service.buscar();
        Assertions.assertEquals(linhas, listaLinhas);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorTelefone_Sucesso() {
        when(repository.findAll()).thenReturn(listaLinhas);
        Linha retorno = service.buscarPorTelefone("21", "999999999");
        Assertions.assertEquals("21", retorno.getDdd());
        Assertions.assertEquals("999999999", retorno.getNumero());
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorTelefone_Falha() {
        when(repository.findAll()).thenReturn(listaLinhas);
        Linha retorno = service.buscarPorTelefone("21", "777777777");
        Assertions.assertEquals(null, retorno);
        verify(repository, times(1)).findAll();
    }

    @Test
    void atualizar() {
        Linha linha = listaLinhas.get(1);
        String newNumero = "777777777";
        linha.setNumero(newNumero);
        when(repository.save(linha)).thenReturn(linha);
        Linha response = service.atualizar(linha);
        Assertions.assertEquals(response.getNumero(), newNumero);
        verify(repository, times(1)).save(any());
    }

    @Test
    void deletar() {
        doNothing().when(repository).deleteById(any());
        service.deletar(1L);
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    void inserirSaldo() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        service.inserirSaldo("21", "999999999", 20.00);
        Assertions.assertEquals(21.00, listaLinhas.get(0).getSaldo());
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
    }

    @Test
    void realizarLigacao() {
        when(repository.findAll()).thenReturn(listaLinhas);
        Boolean teste = service.realizarLigacao("21", "999999999");
        Assertions.assertTrue(teste);
        verify(repository, times(1)).findAll();
    }

    @Test
    void alterarPlano() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        Plano plano = new Plano(1L, "Turbo", 20.00, 30, true, 4, null);
        service.alterarPlano("21", "999999999", plano);
        Assertions.assertEquals("Turbo", listaLinhas.get(0).getPlano().getNome());
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
    }

    @Test
    void bloquearPorPerda() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        service.bloquearPorPerda("21", "999999999");
        Assertions.assertEquals(Status.BLOQUEIO_PERDA, listaLinhas.get(0).getStatus());
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
    }

    @Test
    void barrarLinha() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        listaLinhas.get(0).setDataParaBarrar(LocalDate.parse("2023-09-13"));
        service.barrarLinha("21", "999999999");
        Assertions.assertEquals(Status.BARRADO, listaLinhas.get(0).getStatus());
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
    }

    @Test
    void cancelarLinha() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        listaLinhas.get(0).setDataParaBarrar(LocalDate.parse("2023-06-13"));
        service.cancelarLinha("21", "999999999");
        Assertions.assertEquals(Status.CANCELADO, listaLinhas.get(0).getStatus());
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
    }

    @Test
    void verStatus() {
        when(repository.findAll()).thenReturn(listaLinhas);
        service.verStatus("21", "999999999");
        Assertions.assertEquals(Status.ATIVO, listaLinhas.get(0).getStatus());
        verify(repository, times(1)).findAll();
    }
}