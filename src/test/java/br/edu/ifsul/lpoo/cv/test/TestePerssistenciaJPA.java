/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.dao.PerssistenciaJPA;
import javax.swing.JOptionPane;
import org.junit.Test;

/**
 *
 * @author José Henrique PG
 */
public class TestePerssistenciaJPA {

    @Test///ok
    ///Teste para verificar a conexao e criação automática de tabelas
    public void testarConexao() throws Exception {///Teste de Conexão            
        PerssistenciaJPA persistencia = new PerssistenciaJPA();
        if (persistencia.conexaoAberta()) {
            JOptionPane.showMessageDialog(null, "Conexao com o BD aberta utilizando JPA");
            persistencia.fecharConexao();
        } else {
            JOptionPane.showMessageDialog(null, "Não abriu conexao via jpa");
        }
    }
}
