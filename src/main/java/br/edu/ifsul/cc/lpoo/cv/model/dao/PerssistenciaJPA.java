/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.model.dao;

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
}
