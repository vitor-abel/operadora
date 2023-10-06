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
import org.springframework.dao.EmptyResultDataAccessException;

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
        Plano plano = new Plano(1L, "Turbo", 20.00, 30, true, 4, null);
        listaLinhas = new ArrayList<>();
        Linha linha = new Linha(1L, "21", "999999999", 1.00, 0.00, LocalDate.now(), LocalDate.now(), null, plano, Status.ATIVO);
        Linha linha2 = new Linha(2L, "21", "888888888", 0.00, 0.00, null, null, null, plano, Status.ATIVO);
        listaLinhas.add(linha);
        listaLinhas.add(linha2);
    }

    @Test
    void criar() {
        Linha linha = new Linha(null, "21", "999999999", 0.00, 0.00, LocalDate.now(), LocalDate.now(), null, null, Status.ATIVO);
        when(repository.save(linha)).thenReturn(listaLinhas.get(0));
        service.criar(linha);
        Assertions.assertEquals(0., linha.getSaldo());
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
    void deletar_Sucesso() {
        doNothing().when(repository).deleteById(any());
        service.deletar(1L);
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    void deletar_Falha() {
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(any());
        Boolean errorThrown = false;
        try {
            service.deletar(3L);
        } catch (EmptyResultDataAccessException ex) {
            errorThrown = true;
        }
        Assertions.assertTrue(errorThrown);
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    void inserirSaldo_Sucesso_semSaldoBloqueado() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        service.inserirSaldo("21", "999999999", 20.00);
        Assertions.assertEquals(21.00, listaLinhas.get(0).getSaldo());
        Assertions.assertEquals(Status.ATIVO, listaLinhas.get(0).getStatus());
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
    }

    @Test
    void inserirSaldo_Sucesso_comSaldoBloqueado() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        listaLinhas.get(0).setSaldo(0.);
        listaLinhas.get(0).setSaldoBloqueado(20.);
        service.inserirSaldo("21", "999999999", 20.00);
        Assertions.assertEquals(40.00, listaLinhas.get(0).getSaldo());
        Assertions.assertEquals(Status.ATIVO, listaLinhas.get(0).getStatus());
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
    }

    @Test
    void inserirSaldo_Falha() {
        when(repository.findAll()).thenReturn(listaLinhas);
        service.inserirSaldo("21", "777777777", 20.00);
        Assertions.assertEquals(1.0, listaLinhas.get(0).getSaldo());
        verify(repository, times(1)).findAll();
        verify(repository, times(0)).save(any());
    }

    @Test
    void realizarLigacao_Sucesso_planoAtivo() {
        when(repository.findAll()).thenReturn(listaLinhas);
        Boolean teste = service.realizarLigacao("21", "999999999");
        Assertions.assertTrue(teste);
        Assertions.assertEquals(1., listaLinhas.get(0).getSaldo());
        Assertions.assertEquals(LocalDate.now(), listaLinhas.get(0).getDataFimAtivacao());
        verify(repository, times(1)).findAll();
    }

    @Test
    void realizarLigacao_Sucesso_planoAtivoAteHoje() {
        when(repository.findAll()).thenReturn(listaLinhas);
        listaLinhas.get(0).setDataFimAtivacao(LocalDate.now().plusDays(1));
        listaLinhas.get(0).setSaldo(25.);
        Boolean teste = service.realizarLigacao("21", "999999999");
        Assertions.assertTrue(teste);
        Assertions.assertEquals(25., listaLinhas.get(0).getSaldo());
        Assertions.assertEquals(LocalDate.now().plusDays(1), listaLinhas.get(0).getDataFimAtivacao());
        verify(repository, times(1)).findAll();
    }

    @Test
    void realizarLigacao_Sucesso_semPlanoComSaldo() {
        when(repository.findAll()).thenReturn(listaLinhas);
        listaLinhas.get(0).setDataFimAtivacao(LocalDate.now().minusDays(1));
        listaLinhas.get(0).setSaldo(25.);
        Boolean teste = service.realizarLigacao("21", "999999999");
        Assertions.assertTrue(teste);
        Assertions.assertEquals(5., listaLinhas.get(0).getSaldo());
        Assertions.assertEquals(LocalDate.now().plusDays(30), listaLinhas.get(0).getDataFimAtivacao());
        verify(repository, times(1)).findAll();
    }

    @Test
    void realizarLigacao_Falha_linhaInexistente() {
        when(repository.findAll()).thenReturn(listaLinhas);
        Boolean teste = service.realizarLigacao("21", "777777777");
        Assertions.assertFalse(teste);
        verify(repository, times(1)).findAll();
    }

    @Test
    void realizarLigacao_Falha_semPlanoSemSaldo() {
        when(repository.findAll()).thenReturn(listaLinhas);
        listaLinhas.get(0).setDataFimAtivacao(LocalDate.now().minusDays(1));
        Boolean teste = service.realizarLigacao("21", "999999999");
        Assertions.assertEquals(1., listaLinhas.get(0).getSaldo());
        Assertions.assertEquals(LocalDate.now().minusDays(1), listaLinhas.get(0).getDataFimAtivacao());
        Assertions.assertFalse(teste);
        verify(repository, times(1)).findAll();
    }

    @Test
    void realizarLigacao_Falha_linhaNaoAtiva() {
        when(repository.findAll()).thenReturn(listaLinhas);
        listaLinhas.get(0).setStatus(Status.BARRADO);
        listaLinhas.get(0).setSaldo(25.);
        Boolean teste = service.realizarLigacao("21", "999999999");
        Assertions.assertFalse(teste);
        Assertions.assertEquals(25., listaLinhas.get(0).getSaldo());
        Assertions.assertEquals(LocalDate.now(), listaLinhas.get(0).getDataFimAtivacao());
        verify(repository, times(1)).findAll();
    }

    @Test
    void alterarPlano_Sucesso() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        Plano semanal = new Plano(2L, "Semanal", 10., 7, true, 4, null);
        service.alterarPlano("21", "999999999", semanal);
        Assertions.assertEquals(semanal, listaLinhas.get(0).getPlano());
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
    }

    @Test
    void alterarPlano_Falha() {
        when(repository.findAll()).thenReturn(listaLinhas);
        Plano semanal = new Plano(2L, "Semanal", 10., 7, true, 4, null);
        service.alterarPlano("21", "777777777", semanal);
        verify(repository, times(1)).findAll();
        verify(repository, times(0)).save(any());
    }

    @Test
    void bloquearPorPerda_Sucesso() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        service.bloquearPorPerda("21", "999999999");
        Assertions.assertEquals(Status.BLOQUEIO_PERDA, listaLinhas.get(0).getStatus());
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
    }

    @Test
    void bloquearPorPerda_Falha() {
        when(repository.findAll()).thenReturn(listaLinhas);
        service.bloquearPorPerda("21", "777777777");
        verify(repository, times(1)).findAll();
        verify(repository, times(0)).save(any());
    }

    @Test
    void desbloquearPorPerda_Sucesso_Ativo() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        service.desbloquearPorPerda("21", "999999999");
        Assertions.assertEquals(Status.ATIVO, listaLinhas.get(0).getStatus());
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
    }

    @Test
    void desbloquearPorPerda_Sucesso_Barrado() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        listaLinhas.get(0).setDataParaBarrar(LocalDate.now().minusDays(1));
        service.desbloquearPorPerda("21", "999999999");
        Assertions.assertEquals(Status.BARRADO, listaLinhas.get(0).getStatus());
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
    }

    @Test
    void desbloquearPorPerda_Falha() {
        when(repository.findAll()).thenReturn(listaLinhas);
        service.desbloquearPorPerda("21", "777777777");
        verify(repository, times(1)).findAll();
        verify(repository, times(0)).save(any());
    }

    @Test
    void barrarLinha_Sucesso() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        listaLinhas.get(0).setDataParaBarrar(LocalDate.now().minusDays(1));
        service.barrarLinha("21", "999999999");
        Assertions.assertEquals(Status.BARRADO, listaLinhas.get(0).getStatus());
        Assertions.assertEquals(0.00, listaLinhas.get(0).getSaldo());
        Assertions.assertEquals(1.00, listaLinhas.get(0).getSaldoBloqueado());
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
    }

    @Test
    void barrarLinha_Falha_linhaInexistente() {
        when(repository.findAll()).thenReturn(listaLinhas);
        service.barrarLinha("21", "777777777");
        verify(repository, times(1)).findAll();
        verify(repository, times(0)).save(any());
    }

    @Test
    void barrarLinha_Falha_dataNaoBarra() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        listaLinhas.get(0).setDataParaBarrar(LocalDate.now());
        service.barrarLinha("21", "999999999");
        Assertions.assertEquals(Status.ATIVO, listaLinhas.get(0).getStatus());
        verify(repository, times(1)).findAll();
        verify(repository, times(0)).save(any());
    }

    @Test
    void cancelarLinha_Sucesso() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        listaLinhas.get(0).setDataParaBarrar(LocalDate.now().minusMonths(4));
        service.cancelarLinha("21", "999999999");
        Assertions.assertEquals(Status.CANCELADO, listaLinhas.get(0).getStatus());
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
    }

    @Test
    void cancelarLinha_Falha_linhaInexistente() {
        when(repository.findAll()).thenReturn(listaLinhas);
        service.cancelarLinha("21", "777777777");
        verify(repository, times(1)).findAll();
        verify(repository, times(0)).save(any());
    }

    @Test
    void cancelarLinha_Falha_dataNaoCancela() {
        when(repository.findAll()).thenReturn(listaLinhas);
        when(repository.save(any())).thenReturn(listaLinhas.get(0));
        service.cancelarLinha("21", "999999999");
        Assertions.assertEquals(Status.ATIVO, listaLinhas.get(0).getStatus());
        verify(repository, times(1)).findAll();
        verify(repository, times(0)).save(any());
    }

    @Test
    void verStatus_Sucesso() {
        when(repository.findAll()).thenReturn(listaLinhas);
        service.verStatus("21", "999999999");
        Assertions.assertEquals(Status.ATIVO, listaLinhas.get(0).getStatus());
        verify(repository, times(1)).findAll();
    }

    @Test
    void verStatus_Falha() {
        when(repository.findAll()).thenReturn(listaLinhas);
        Status status = service.verStatus("21", "777777777");
        Assertions.assertEquals(null, status);
        verify(repository, times(1)).findAll();
    }
}