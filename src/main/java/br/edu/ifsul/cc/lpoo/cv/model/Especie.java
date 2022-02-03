/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author José Henrique PG
 */
@Entity
@Table(name = "tb_especie")
public class Especie implements Serializable{
    ///Atributos
    @Id
    @SequenceGenerator(name = "seq_especie", sequenceName = "seq_especie_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_especie", strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    @Column(nullable = false)
    private String nome;
    
    ///Costrutor
    public Especie(){
        
    }
    
    ///Métodos Get e Set

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
