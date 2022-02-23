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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author José Henrique PG
 */
public class PerssistenciaJPA implements InterfacePerssistencia {

    public EntityManagerFactory factory;//fabrica de gerenciadores de entidades
    public EntityManager entity;//gerenciador de entidades JPA

    public PerssistenciaJPA() {///construtor
        //parametro: é o nome da unidade de persistencia (Persistence Unit)
        factory = Persistence.createEntityManagerFactory("Pu_Clinica");
        entity = factory.createEntityManager();
    }

    @Override
    public Boolean conexaoAberta() {
        return entity.isOpen();
    }

    @Override
    public void fecharConexao() {
        entity.close();
        System.out.println("Fechou a conexão via JPA!");
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        return entity.find(c, id);//encontra um determinado registro
    }

    @Override
    public void persist(Object o) throws Exception {
        entity.getTransaction().begin();//abre transacao
        entity.persist(o);//persiste (update ou insert)
        entity.getTransaction().commit();//commit na transacao   
    }

    @Override
    public void remover(Object o) throws Exception {
        entity.getTransaction().begin();//abre transacao
        entity.remove(o);//persiste (update ou insert)
        entity.getTransaction().commit();//commit na transacao
    }

    @Override
    public List<Consulta> ListPerssistConsulta() throws Exception {
        throw new UnsupportedOperationException("Funcionalidade não suportada até o momento!"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Venda> ListPerssistVenda() throws Exception {
        throw new UnsupportedOperationException("Funcionalidade Não suportada até o momento!"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Funcionario doLogin(String cpf, String senha) throws Exception {
         throw new UnsupportedOperationException("Funcionalidade Não suportada até o momento!");
    }
}
