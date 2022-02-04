/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Especie;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PerssistenciaJDBC;
import javax.swing.JOptionPane;
import org.junit.Test;

/**
 *
 * @author José Henrique PG
 */
public class TestePerssistenciaJDBC {
     //@Test//ok
    public void testarConexao() throws Exception {
        PerssistenciaJDBC persistencia = new PerssistenciaJDBC();
        if (persistencia.conexaoAberta()) {
            System.out.println("Conexao com o BD VET aberta utilizando JDBC!");
            persistencia.fecharConexao();
        } else {
            System.out.println("Não abriu conexao BD VET via JDBC!");
        }
    }
    ///@Test//ok
    public void testeinsereEspecie() throws Exception{
        PerssistenciaJDBC perssist_especie = new PerssistenciaJDBC();
        if(perssist_especie.conexaoAberta()){
            Especie esp  = (Especie) perssist_especie.find(Especie.class, 6);
            if(esp == null){
                System.out.println("Nao encontrou a especie id = 1");
                esp= new Especie();
                esp.setNome("Cachorro");
                perssist_especie.persist(esp);
                System.out.println("Inseriu a especie "+esp.getNome());;
            }else{
                System.out.println("Lista de especie não esta nula"); 
            }
             perssist_especie.fecharConexao();
        }else{
             System.out.println("Não abriu conexao via JDBC");
        }
    }
    public void testeListaConsulta()throws Exception{
        PerssistenciaJDBC perssist_consulta = new PerssistenciaJDBC();
        if(perssist_consulta.conexaoAberta()){
            Consulta cons  = (Consulta) perssist_consulta.find(Consulta.class, 8);
            if(cons == null){
                JOptionPane.showMessageDialog(null,"Não encontrou nenhuma consulta!");
                cons = new Consulta();
            }
        }
    }
}
