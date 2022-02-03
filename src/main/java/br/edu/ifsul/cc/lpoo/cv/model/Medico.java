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
@Table(name = "tb_medico")
@DiscriminatorValue("M")
public class Medico extends Pessoa{
    ///Atributos
    @Column(nullable = false)
    private String numero_crmv;
    
    ///Construtor
    public Medico(){
        
    }
    
    ///Métodos Get e Set

    /**
     * @return the numero_crmv
     */
    public String getNumero_crmv() {
        return numero_crmv;
    }

    /**
     * @param numero_crmv the numero_crmv to set
     */
    public void setNumero_crmv(String numero_crmv) {
        this.numero_crmv = numero_crmv;
    }
    
}
