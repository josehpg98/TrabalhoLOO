/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.model;

/**
 *
 * @author José Henrique PG
 */
public enum Pagamento {
    CARTAO_DEBITO, CARTAO_CREDITO, DINHEIRO, PIX, BOLETO;
    ///Método que retorna uma enum
    public static Pagamento getPagamento(String nameEnum){
         if(nameEnum.equals(Pagamento.CARTAO_DEBITO.toString())){
            return Pagamento.CARTAO_DEBITO;
        }else if(nameEnum.equals(Pagamento.CARTAO_CREDITO.toString())){
            return Pagamento.CARTAO_CREDITO;
        }else if(nameEnum.equals(Pagamento.DINHEIRO.toString())){
            return Pagamento.DINHEIRO;
        }else if(nameEnum.equals(Pagamento.PIX.toString())){
            return Pagamento.PIX;
        }else if(nameEnum.equals(Pagamento.BOLETO.toString())){
            return Pagamento.BOLETO;
        }else{
            return null;
        }
    }
}
