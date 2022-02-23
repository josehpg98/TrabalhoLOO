/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author José Henrique PG
 */
@Entity
@Table(name = "tb_funcionario")
@DiscriminatorValue("F")
public class Funcionario extends Pessoa{
    ///Atributos
    @Column(nullable = false, length = 20)
    private String numero_ctps;
    
    @Column(nullable = false, length = 20)
    private String numero_pis;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Cargo cargo;
    ///Construtor
    public Funcionario(){
        
    }
    ///Métodos Get e Set

    /**
     * @return the numero_ctps
     */
    public String getNumero_ctps() {
        return numero_ctps;
    }

    /**
     * @param numero_ctps the numero_ctps to set
     */
    public void setNumero_ctps(String numero_ctps) {
        this.numero_ctps = numero_ctps;
    }

    /**
     * @return the numero_pis
     */
    public String getNumero_pis() {
        return numero_pis;
    }

    /**
     * @param numero_pis the numero_pis to set
     */
    public void setNumero_pis(String numero_pis) {
        this.numero_pis = numero_pis;
    }

    /**
     * @return the cargo
     */
    public Cargo getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
}
