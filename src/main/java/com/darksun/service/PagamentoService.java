package com.darksun.service;

public class PagamentoService {
    /*@Autowired
    private PagamentoRepository repository;

    public List<Pagamento> buscar() {
        return repository.findAll();
    }

    public Pagamento atualizar(Pagamento pagamento) {
        return repository.save(pagamento);
    }

    public Pagamento cobrarFatura(String ddd, String numero) { //Criar pagamento com a data nula e com a data de vencimento no ciclo. Antes de fazer procedimento, verificar se tem fatura em aberto e se ele pagou a fatura pagou anterior

        Boolean criarParaProximoMes = false;
        for (Pagamento pagamento : linha.getPagamentos()) {
            if (pagamento.data == null) {
                //Setar inadimplente
                return pagamento;
            }
            if (pagamento.getDataVencimento().getMonthValue() == LocalDate.now().getMonthValue() && pagamento.getDataVencimento().getYear() == LocalDate.now().getYear()) {
                criarParaProximoMes = true;
            }
        }
        LocalDate data = LocalDate.now().withDayOfMonth(linha.getDiaPagamento());
        if (criarParaProximoMes) {
            data = data.plusMonths(1);
        }
        Pagamento pagamento = new Pagamento(null, null, data, linha);
        linha.getPagamentos().add(pagamento);
        return repository.save(pagamento);
    }

    public void pagarConta(Linha linha) { //Colocar data no pagamento e atualizar status de cliente.
        for (Pagamento pagamento : linha.getPagamentos()) {
            if (pagamento.getData() == null) {
                pagamento.setData(LocalDate.now());
                if (linha.getStatus() == Status.BLOQUEIO_PARCIAL || linha.getStatus() == Status.BLOQUEIO_TOTAL) {
                    linha.setStatus(Status.ATIVO);
                }
            }
        }
    }Passar linha e localizar fatura mais antiga*/
}

