package com.isaackennedy.curso.services;

import com.isaackennedy.curso.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {
    public void preencherPgtoComBoleto(PagamentoComBoleto pgto, Date instantePedido) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(instantePedido);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        pgto.setDataVencimento(cal.getTime());
    }
}
