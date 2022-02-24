/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import java.util.List;

/**
 *
 * @author José Henrique PG
 */
public interface InterfacePerssistencia {

    public Boolean conexaoAberta();

    public void fecharConexao();

    public Object find(Class c, Object id) throws Exception;

    public void persist(Object o) throws Exception;

    public void remover(Object o) throws Exception;

    public List<Consulta> ListPerssistConsulta() throws Exception;

    public List<Venda> ListPerssistVenda() throws Exception;
    
    public Funcionario doLogin(String cpf, String senha) throws Exception;
    
    public List<Funcionario> getFuncionarios() throws Exception;
}
