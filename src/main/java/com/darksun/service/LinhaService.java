package com.darksun.service;

import com.darksun.model.Linha;
import com.darksun.model.Plano;
import com.darksun.model.type.Status;
import com.darksun.repository.LinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LinhaService {

    @Autowired
    private LinhaRepository repository;

    public Linha criar(Linha linha) {
        return repository.save(linha);
    }

    public List<Linha> buscar() {
        return repository.findAll();
    }

    public Linha buscarPorTelefone(String ddd, String numero) {
        List<Linha> linhas = buscar();
        for (Linha linha : linhas) {
            if (linha.getDdd().equals(ddd) && linha.getNumero().equals(numero)) {
                return linha;
            }
        }
        return null;
    }

    public Linha atualizar(Linha linha) {
        return repository.save(linha);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public void inserirSaldo(String ddd, String numero, Double saldo) {
        Linha linha = buscarPorTelefone(ddd, numero);
        if (linha != null) {
            linha.setSaldo(linha.getSaldo() + linha.getSaldoBloqueado() + saldo);
            linha.setSaldoBloqueado(0.);
            linha.setDataParaBarrar(LocalDate.now().plusMonths(30));
            linha.setStatus(Status.ATIVO);
            repository.save(linha);
        }
    }

    public Boolean realizarLigacao(String ddd, String numero) {
        Linha linha = buscarPorTelefone(ddd, numero);
        if (linha != null) {
            if (!linha.getStatus().equals(Status.ATIVO)) {
                return false;
            }
            if (linha.getDataFimAtivacao().isAfter(LocalDate.now()) || linha.getDataFimAtivacao().isEqual(LocalDate.now())) {
                return true;
            } else {
                if (linha.getSaldo() >= linha.getPlano().getPreco()) {
                    linha.setSaldo(linha.getSaldo() - linha.getPlano().getPreco());
                    linha.setDataFimAtivacao(LocalDate.now().plusDays(linha.getPlano().getDuracaoDias()));
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public void alterarPlano(String ddd, String numero, Plano plano) {
        Linha linha = buscarPorTelefone(ddd, numero);
        if (linha != null) {
            linha.setPlano(plano);
            repository.save(linha);
        }
    }

    public void bloquearPorPerda(String ddd, String numero) {
        Linha linha = buscarPorTelefone(ddd, numero);
        if (linha != null) {
            linha.setStatus(Status.BLOQUEIO_PERDA);
            repository.save(linha);
        }
    }

    public void desbloquearPorPerda(String ddd, String numero) {
        Linha linha = buscarPorTelefone(ddd, numero);
        if (linha != null) {
            if (LocalDate.now().isAfter(linha.getDataParaBarrar())) {
                linha.setStatus(Status.BARRADO);
            } else {
                linha.setStatus(Status.ATIVO);
            }
            repository.save(linha);
        }
    }

    public void barrarLinha(String ddd, String numero) {
        Linha linha = buscarPorTelefone(ddd, numero);
        if (linha != null) {
            if (LocalDate.now().isAfter(linha.getDataParaBarrar())) {
                linha.setStatus(Status.BARRADO);
                linha.setSaldoBloqueado(linha.getSaldo() + linha.getSaldoBloqueado());
                linha.setSaldo(0.);
                repository.save(linha);
            }
        }
    }

    public void cancelarLinha(String ddd, String numero) {
        Linha linha = buscarPorTelefone(ddd, numero);
        if (linha != null) {
            if (LocalDate.now().isAfter(linha.getDataParaBarrar().plusDays(90))) {
                linha.setStatus(Status.CANCELADO);
                repository.save(linha);
            }
        }
    }

    public Status verStatus(String ddd, String numero) {
        Linha linha = buscarPorTelefone(ddd, numero);
        if (linha != null) {
            return linha.getStatus();
        }
        return null;
    }
}
