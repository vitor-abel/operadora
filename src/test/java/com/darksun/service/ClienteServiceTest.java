package com.darksun.service;

import com.darksun.model.Cliente;
import com.darksun.repository.ClienteRepository;
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

class ClienteServiceTest {
    @InjectMocks
    private ClienteService service;

    @Mock
    private ClienteRepository repository;

    List<Cliente> listaClientes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listaClientes = new ArrayList<>();
        Cliente abel = new Cliente(1L, "Abel", LocalDate.now(), LocalDate.parse("1995-09-26"), "12345678900", null);
        Cliente krek = new Cliente(2L, "Careca", LocalDate.now(), LocalDate.parse("1995-03-07"), "78945612300", null);
        listaClientes.add(abel);
        listaClientes.add(krek);
    }

    @Test
    void criar() {
        Cliente cliente = new Cliente(null, "Abel", LocalDate.parse("2023-09-05"), LocalDate.parse("1995-09-26"), "12345678900", null);
        when(repository.save(cliente)).thenReturn(listaClientes.get(0));
        service.criar(cliente);
        verify(repository, times(1)).save(any());
    }

    @Test
    void buscar() {
        when(repository.findAll()).thenReturn(listaClientes);
        List<Cliente> clientes = service.buscar();
        Assertions.assertEquals(clientes, listaClientes);
        verify(repository, times(1)).findAll();
    }

    @Test
    void atualizar() {
        Cliente cliente = listaClientes.get(1);
        String newCpf = "12345678900";
        cliente.setCpf(newCpf);
        when(repository.save(cliente)).thenReturn(cliente);
        Cliente response = service.atualizar(cliente);
        Assertions.assertEquals(response.getCpf(), newCpf);
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