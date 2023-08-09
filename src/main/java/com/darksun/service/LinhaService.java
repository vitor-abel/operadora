package com.darksun.service;

import com.darksun.model.Linha;
import com.darksun.model.Plano;
import com.darksun.model.type.Status;
import com.darksun.repository.LinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

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
            linha.setSaldo(linha.getSaldo() + saldo);
        }
    }

    public Boolean realizarLigacao(String ddd, String numero) {
        Linha linha = buscarPorTelefone(ddd, numero);
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

    public void alterarPlano(String ddd, String numero, Plano plano) {
        Linha linha = buscarPorTelefone(ddd, numero);
        linha.setPlano(plano);
    }
}
