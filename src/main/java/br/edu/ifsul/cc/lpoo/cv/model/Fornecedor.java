/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author José Henrique PG
 */
@Entity
@Table(name = "tb_fornecedor")
@DiscriminatorValue("O")
public class Fornecedor extends Pessoa{
    ///Atributos
    @Column(nullable = false, length = 20)
    private String cnpj;
    
    @Column(nullable = false, length = 20)
    private String ie;
    ///Construtor
    public Fornecedor(){
        
    }
    ///Métodos Get e Set

    /**
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * @return the ie
     */
    public String getIe() {
        return ie;
    }

    /**
     * @param ie the ie to set
     */
    public void setIe(String ie) {
        this.ie = ie;
    }
    
}
