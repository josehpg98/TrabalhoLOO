/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Especie;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PerssistenciaJDBC;
import java.util.Calendar;
import java.util.List;
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
    @Test
    public void testeListaConsulta() throws Exception {
        PerssistenciaJDBC perssist_consulta = new PerssistenciaJDBC();
        if (perssist_consulta.conexaoAberta()) {
            List<Consulta> listc = perssist_consulta.ListPerssistConsulta();
            if (listc.isEmpty()) {
                System.out.println("Dados da consulta: ");
                for (Consulta cs : listc) {
                    System.out.println("\n\n----Dados de consulta----\n"
                            + "\n Id: " + cs.getId()
                            + "\n CPF Médico: " + cs.getMedico().getCpf()
                            + "\n CRMV Médico: " + cs.getMedico().getNumero_crmv()
                            + "\n Nome do Médico: " + cs.getMedico().getNome()
                            + "\n Id do Pet: " + cs.getPet().getId()
                            + "\n Nome do Pet: " + cs.getPet().getNome()
                            + "\n Receita da Consulta: ");
                            cs.getReceitas().stream().forEach(r -> System.out.println("Receita{"
                            + "id=" + r.getId()
                            + ", orientacao='" + r.getOrientacao() + '\''
                            + '}'));
                    cs.getReceitas().stream().forEach(r -> {
                        try {
                            perssist_consulta.remover(r);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    perssist_consulta.remover(cs);
                }
            } else {
                System.out.println("Nenhum dado encontrado em pessoa!");
                Pessoa pessoaMedico = new Pessoa();
                pessoaMedico.setTipo("M");
                pessoaMedico.setCep("6750432");
                pessoaMedico.setComplemento("Perto Meercadoo");
                pessoaMedico.setNome("José");
                pessoaMedico.setCpf("00000000000");
                pessoaMedico.setData_nascimento(Calendar.getInstance());
                pessoaMedico.setEmail("medico@mail.com");
                pessoaMedico.setEndereco("lugar 1, numero 012, Rua");
                pessoaMedico.setNumero_celular("549345322399");
                pessoaMedico.setRg("908797884");
                pessoaMedico.setSenha("123456");
                perssist_consulta.persist(pessoaMedico);
            }
        }
    }
}
