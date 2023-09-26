package com.darksun.service;

import com.darksun.model.Plano;
import com.darksun.repository.PlanoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlanoServiceTest {

    @InjectMocks
    private PlanoService service;

    @Mock
    private PlanoRepository repository;

    List<Plano> listaPlanos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listaPlanos = new ArrayList<>();
        Plano turbo = new Plano(1L, "Turbo", 20.00, 30, true, 4, null);
        Plano turboQuinzenal = new Plano(2L, "Turbo Quinzenal", 15.00, 15, true, 4, null);
        listaPlanos.add(turbo);
        listaPlanos.add(turboQuinzenal);
    }

    @Test
    void criar() {
        Plano plano = new Plano(null, "Turbo", 20.00, 30, true, 4, null);
        when(repository.save(plano)).thenReturn(listaPlanos.get(0));
        service.criar(plano);
        verify(repository, times(1)).save(any());
    }

    @Test
    void buscar() {
        when(repository.findAll()).thenReturn(listaPlanos);
        List<Plano> planos = service.buscar();
        Assertions.assertEquals(planos, listaPlanos);
        verify(repository, times(1)).findAll();
    }

    @Test
    void atualizar() {
        Plano plano = listaPlanos.get(1);
        Double newPreco = 15.00;
        plano.setPreco(newPreco);
        when(repository.save(plano)).thenReturn(plano);
        Plano response = service.atualizar(plano);
        Assertions.assertEquals(response.getPreco(), newPreco);
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
}