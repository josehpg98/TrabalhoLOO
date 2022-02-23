/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author José Henrique PG
 */
@Entity
@Table(name = "tb_medico")
@DiscriminatorValue("M")
public class Medico extends Pessoa {

    ///Atributos
    @Column(nullable = false, length = 10)
    private String numero_crmv;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_cadastro_Medico;

    ///Construtor
    public Medico() {

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

    /**
     * @return the data_cadastro_Medico
     */
    public Calendar getData_cadastro_Medico() {
        return data_cadastro_Medico;
    }

    /**
     * @param data_cadastro_Medico the data_cadastro_Medico to set
     */
    public void setData_cadastro_Medico(Calendar data_cadastro_Medico) {
        this.data_cadastro_Medico = data_cadastro_Medico;
    }

}
