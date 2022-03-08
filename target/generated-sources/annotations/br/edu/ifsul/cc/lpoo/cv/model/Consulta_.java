package br.edu.ifsul.cc.lpoo.cv.model;

import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-18T01:00:27")
@StaticMetamodel(Consulta.class)
public class Consulta_ { 

    public static volatile SingularAttribute<Consulta, String> observacao;
    public static volatile SingularAttribute<Consulta, Calendar> data_retorno;
    public static volatile SingularAttribute<Consulta, Medico> medico;
    public static volatile SingularAttribute<Consulta, Float> valor;
    public static volatile ListAttribute<Consulta, Receita> receitas;
    public static volatile SingularAttribute<Consulta, Calendar> data_consulta;
    public static volatile SingularAttribute<Consulta, Integer> id;
    public static volatile SingularAttribute<Consulta, Pet> pet;

}