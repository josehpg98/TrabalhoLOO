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
public enum Cargo {
    ADESTRADOR, ATENDENTE, AUXILIAR_VETERINARIO;
    ///Método que reorna uma enum
    public static Cargo getCargo(String nameEnum){
        if(nameEnum.equals(Cargo.ADESTRADOR.toString())){
            return Cargo.ADESTRADOR;
        }else if(nameEnum.equals(Cargo.ATENDENTE.toString())){
            return Cargo.ATENDENTE;
        }else if(nameEnum.equals(Cargo.AUXILIAR_VETERINARIO.toString())){
            return Cargo.AUXILIAR_VETERINARIO;
        }else{
            return null;
        }
    }
}
