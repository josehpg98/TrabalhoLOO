/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Especie;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Raca;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author José Henrique PG
 */
public class PerssistenciaJDBC implements InterfacePerssistencia {

    private final String DRIVER = "org.postgresql.Driver";
    private final String USER = "postgres";
    private final String SENHA = "postgres";
    public static final String URL = "jdbc:postgresql://localhost:5432/LPOO_ClinicaVeterinaria";
    private Connection con = null;

    public PerssistenciaJDBC() {
        try {
            Class.forName(DRIVER); //carregamento do driver postgresql em tempo de execução
            System.out.println("Tentando estabelecer conexao JDBC com : " + URL + " ...");
            this.con = (Connection) DriverManager.getConnection(URL, USER, SENHA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Boolean conexaoAberta() {
        try {
            if (con != null) {
                return !con.isClosed();//verifica se a conexao está aberta
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void fecharConexao() {
        try {
            this.con.close();//fecha a conexao.
            System.out.println("Fechou conexao via JDBC!");
        } catch (SQLException e) {
            e.printStackTrace();//gera uma pilha de erro na saida.
        }
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        if (c == Especie.class) {
            PreparedStatement ps = this.con.prepareStatement("select id,nome from tb_especie where id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));
            ResultSet rs = ps.executeQuery();///inicialmente o  ponteiro do resultSet está na linha -1
            if (rs.next()) {
                Especie esp = new Especie();
                esp.setId(rs.getInt("id"));
                esp.setNome(rs.getString("nome"));
                return esp;
            }
        } else if (c == Consulta.class) {
            PreparedStatement ps = this.con.prepareStatement("select id,data,data_retorno,observacao,valor,medico_id,pet_id from tb_consulta where id = ? ");
            ps.setInt(1, Integer.parseInt(id.toString()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Consulta cons = new Consulta();
                cons.setId(rs.getInt("id"));
                Calendar dt1 = Calendar.getInstance();
                dt1.setTimeInMillis(rs.getDate("data").getTime());
                cons.setData(dt1);
                Calendar dt2 = Calendar.getInstance();
                dt1.setTimeInMillis(rs.getDate("data_retorno").getTime());
                cons.setData(dt2);
                cons.setObservacao(rs.getString("observacao"));
                cons.setValor(rs.getFloat("valor"));
                cons.setMedico((Medico) find(Medico.class, rs.getInt("medico_id")));
                cons.setPet((Pet) find(Pet.class, rs.getInt("pet_id")));
                return cons;
            }
        } else if (c == Medico.class) {
            PreparedStatement ps = this.con.prepareStatement("select numero_crmv, cpf from tb_medico where cpf = ?");
            ps.setString(1, id.toString());
            ResultSet rsm = ps.executeQuery();
            if (rsm.next()) {
                Medico med = new Medico();
                med.setNumero_crmv(rsm.getString("numero_crmv"));
                med.setCpf(rsm.getString("cpf"));
                return med;
            }
        } else if (c == Pet.class) {
            PreparedStatement ps = this.con.prepareStatement("select id, data_nascimento, nome, observacao, cliente_id, raca_id from tb_pet where id = ? ");
            ps.setInt(1, Integer.parseInt(id.toString()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pet pt = new Pet();
                pt.setId(rs.getInt("id"));
                Calendar dtU = Calendar.getInstance();
                dtU.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                pt.setData_nascimento(dtU);
                pt.setNome(rs.getString("nome"));
                pt.setObservacao(rs.getString("observacao"));
                pt.setCliente((Cliente) find(Cliente.class, rs.getString("cliente_id")));
                pt.setRaca((Raca) find(Raca.class, rs.getInt("raca_id")));
                return pt;
            }
        } else if (c == Cliente.class) {
            PreparedStatement ps = this.con.prepareStatement("select cpf, data_ultima_visita from tb_cliente where cpf = ? ");
            ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cliente cli = new Cliente();
                Calendar data1 = Calendar.getInstance();
                data1.setTimeInMillis(rs.getDate("data_ultima_visita").getTime());
                cli.setData_ultima_visita(data1);
                cli.setCpf(rs.getString("cpf"));
                return cli;
            }
        } else if (c == Raca.class) {
            PreparedStatement ps = this.con.prepareStatement("select id, nome, especie_id from tb_raca where id = ? ");
            ps.setInt(1, Integer.parseInt(id.toString()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Raca rc = new Raca();
                rc.setId(rs.getInt("id"));
                rc.setNome(rs.getString("nome"));
                rc.setEspecie((Especie) find(Especie.class, rs.getInt("especie_id")));
                return rc;
            }
        } else if (c == Receita.class) {
            PreparedStatement ps = this.con.prepareStatement("select id, orientacao, consulta_id from tb_receita where id = ? ");
            ps.setInt(1, Integer.parseInt(id.toString()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Receita re = new Receita();
                re.setId(rs.getInt("id"));
                re.setOrientacao((rs.getString("orientacao")));
                re.setConsulta((Consulta) find(Consulta.class, rs.getInt("consulta_id")));
                return re;
            }
        } else if (c == Venda.class) {
            PreparedStatement ps = this.con.prepareStatement("select id,data,observacao,pagamento,valortotal,cliente_id,funcionario_id from tb_venda where id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Venda vd = new Venda();
                vd.setId(rs.getInt("id"));
                Calendar data1 = Calendar.getInstance();
                data1.setTimeInMillis(rs.getDate("data").getTime());
                vd.setData(data1);
                vd.setObservacao(rs.getString("observacao"));
                vd.setPagamento(("pagamento"));
                vd.setValortotal(rs.getFloat("valortotal"));
                vd.setCliente((Cliente) find(Cliente.class, rs.getInt("cliente_id")));
                vd.setFuncionario((Funcionario) find(Cliente.class, rs.getInt("funcionario_id")));
                return vd;
            }
        }
        return null;
    }

    @Override
    public void persist(Object o) throws Exception {
        if (o instanceof Especie) {
            Especie esp = (Especie) o;///conversão de classe para objeto
            if (esp.getId() == null) {///se não houver resgistros
                //prepara a instrução de inserção de dados
                PreparedStatement ps = this.con.prepareStatement("insert into tb_especie (id, nome) values (nextval('seq_especie_id'), ? )");
                ps.setString(1, esp.getNome());
                ps.execute();
            } else {///update da tb_especie
                PreparedStatement ps = this.con.prepareStatement("update tb_especie set nome = ? where id = ? "); //prepara a instrução.
                ps.setString(1, esp.getNome());
                ps.setInt(2, esp.getId());
                ps.executeUpdate();
            }
        }
        if (o instanceof Consulta) {
            Consulta cons = (Consulta) o;
            if (cons.getId() == null) {///inserção
                PreparedStatement ps = this.con.prepareStatement("insert into tb_consulta id, medico_id, pet_id) value(nextval('seq_consulta_id'), ?, ?)");
                ps.setString(1, cons.getMedico().getCpf());
                ps.setInt(2, cons.getPet().getId());
                ps.execute();
            } else {///update
                PreparedStatement ps = this.con.prepareStatement("update tb_consulta set medico_id = ?, pet_id = ?where id = ?");
                ps.setString(1, cons.getMedico().getCpf());
                ps.setInt(2, cons.getPet().getId());
                ps.setInt(3, cons.getId());
                ps.executeUpdate();
            }
        } else if (o instanceof Receita) {///inserção
            Receita rc = (Receita) o;
            if (rc.getId() == null) {///insert
                PreparedStatement ps = this.con.prepareStatement("insert into tb_receita (id, orientacao, consulta_id) values (nextval('seq_receita_id'), ?, ?)");
                ps.setString(1, rc.getOrientacao());
                ps.setInt(2, rc.getConsulta().getId());
                ps.execute();
            } else {///update
                PreparedStatement ps = this.con.prepareStatement("update tb_receita set orientacao = ?, consulta_id = ? where id = ?");
                ps.setString(1, rc.getOrientacao());
                ps.setInt(2, rc.getConsulta().getId());
                ps.setInt(3, rc.getId());
                ps.executeUpdate();
            }
        } else if (o instanceof Medico) {
            Medico med = (Medico) o;
            if (med.getCpf() == null) {///inserção
                PreparedStatement ps = this.con.prepareStatement("insert into tb_medico (data_cadastro_medico, numero_crmv, cpf) values (now(),?, ?)");
                ps.setString(1, med.getNumero_crmv());
                ps.setString(2, med.getCpf());
                ps.execute();
            } else {//update
                PreparedStatement ps = this.con.prepareStatement("update tb_medico set umero_crmv = ?, cpf = ? where data_cadastro_medico = ?");
                ps.setString(1, med.getNumero_crmv());
                ps.setString(2, med.getCpf());
                ps.executeUpdate();
            }
        } else if (o instanceof Pet) {
            Pet pt = (Pet) o;
            if (pt.getId() == null) {///insert
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pet id, data_nascimento, nome, observacao, cliente_id, raca_id) values (nextval('seq_pet_id'), ?, ?, ?, ?, ?)");
                Date dtU = new Date(System.currentTimeMillis());
                dtU.setTime(pt.getData_nascimento().getTimeInMillis());
                ps.setDate(1, (java.sql.Date) dtU);
                ps.setString(2, pt.getNome());
                ps.setString(3, pt.getObservacao());
                ps.setString(4, pt.getCliente().getCpf());
                ps.setInt(5, pt.getRaca().getId());
                ps.executeUpdate();
            } else {///update
                PreparedStatement ps = this.con.prepareStatement("update tb_pet set data_nascimento = ?, nome = ?, observacao = ?, cliente_id = ?, raca_id = ? here id = ?");
                Date dtU = null;
                dtU.setTime(pt.getData_nascimento().getTimeInMillis());
                ps.setDate(1, (java.sql.Date) dtU);
                ps.setString(2, pt.getNome());
                ps.setString(3, pt.getObservacao());
                ps.setString(4, pt.getCliente().getCpf());
                ps.setInt(5, pt.getRaca().getId());
                ps.setInt(6, pt.getId());
                ps.executeUpdate();
            }
        } else if (o instanceof Cliente) {
            Cliente cli = (Cliente) o;
            if (cli.getCpf() == null) {
                PreparedStatement ps = this.con.prepareStatement("insert into tb_cliente (data_ultima_visita, cpf) values (?, ?)");
                Date dtU = new Date(System.currentTimeMillis());
                dtU.setTime(cli.getData_ultima_visita().getTimeInMillis());
                ps.setDate(1, (java.sql.Date) dtU);
                ps.setString(2, cli.getCpf());
                ps.execute();
            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_cliente set data_ultima_visita = ? where data_cadastro_cliente = ?");
                Date dtU = new Date(System.currentTimeMillis());
                dtU.setTime(cli.getData_ultima_visita().getTimeInMillis());
                ps.setDate(1, (java.sql.Date) dtU);
                dtU.setTime(System.currentTimeMillis());
                ps.setDate(2, (java.sql.Date) dtU);
                ps.executeUpdate();
            }
        } else if (o instanceof Raca) {
            Raca rac = (Raca) o;
            if (rac.getId() == null) {
                PreparedStatement ps = this.con.prepareStatement("insert into tb_raca (id, nome, especie_id) values (nextval('seq_raca_id'), ?, ?)");
                ps.setString(1, rac.getNome());
                ps.setInt(2, rac.getEspecie().getId());
                ps.execute();
            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_raca set nome = ?, especie_id = ?, where id = ?");
                ps.setString(1, rac.getNome());
                ps.setInt(2, rac.getEspecie().getId());
                ps.setInt(3, rac.getId());
                ps.executeUpdate();
            }
        } else if (o instanceof Pessoa) {
            Pessoa pes = (Pessoa) o;
            if (pes.getCpf() == null) {
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa  cpf, cep, complemento, data_nascimento, email, endereco, nome, numero_celular, rg, senha) values (?,?,?,?,?,?,?,?,?,?)");
                ps.setString(1, pes.getCpf());
                ps.setString(2, pes.getCep());
                ps.setString(3, pes.getComplemento());
                Date dtU = new Date(System.currentTimeMillis());
                dtU.setTime(pes.getData_nascimento().getTimeInMillis());
                ps.setDate(4, (java.sql.Date) dtU);
                ps.setString(5, pes.getEmail());
                ps.setString(6, pes.getEndereco());
                ps.setString(7, pes.getNome());
                ps.setString(8, pes.getNumero_celular());
                ps.setString(9, pes.getRg());
                ps.setString(10, pes.getSenha());
                ps.execute();
            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_pessoa set cpf = ?, cep = ?, complemento = ?, data_nascimento = ?, email = ?, endereco = ?, nome = ?, numero_celular = ?,rg = ?,senha = ?, where cpf = ?");
                ps.setString(1, pes.getCpf());
                ps.setString(2, pes.getCep());
                ps.setString(3, pes.getComplemento());
                Date dtU = new Date(System.currentTimeMillis());
                dtU.setTime(pes.getData_nascimento().getTimeInMillis());
                ps.setDate(4, (java.sql.Date) dtU);
                ps.setString(5, pes.getEmail());
                ps.setString(6, pes.getEndereco());
                ps.setString(7, pes.getNome());
                ps.setString(8, pes.getNumero_celular());
                ps.setString(9, pes.getRg());
                ps.setString(10, pes.getSenha());
                ps.setString(11, pes.getCpf());
                ps.executeUpdate();
            }
        } else if (o instanceof Venda) {
            Venda vd = (Venda) o;
            if (vd.getId() == null) {///insert
                PreparedStatement ps = this.con.prepareStatement("insert into tb_venda  id, data, observacao, pagamento, valortotal, cliente_id, funcionario_id) values (?,?,?,?,?,?,?)");
                ps.setInt(1, vd.getId());
                Date dtv = new Date(System.currentTimeMillis());
                dtv.setTime(vd.getData().getTimeInMillis());
                ps.setDate(2, (java.sql.Date) dtv);
                ps.setString(3, vd.getObservacao());
                ps.setString(4, "pagamento");
                ps.setFloat(5, vd.getValortotal());
                ps.setString(6, vd.getCliente().getCpf());
                ps.setString(7, vd.getFuncionario().getNumero_pis());
                ps.execute();
            } else {///update
                PreparedStatement ps = this.con.prepareStatement("update tb_venda set data = ?, observacao = ?, pagamento = ?, valortotal = ?, cliente_id = ?, funcionario_id = ?, where id = ?");
                ps.setInt(1, vd.getId());
                Date dtv = new Date(System.currentTimeMillis());
                dtv.setTime(vd.getData().getTimeInMillis());
                ps.setDate(2, (java.sql.Date) dtv);
                ps.setString(3, vd.getObservacao());
                ps.setString(4, "pagamento");
                ps.setFloat(5, vd.getValortotal());
                ps.setString(6, vd.getCliente().getCpf());
                ps.setString(7, vd.getFuncionario().getNumero_pis());
                ps.setInt(8, vd.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void remover(Object o) throws Exception {
        if (o instanceof Especie) {
            Especie esp = (Especie) o;//conversao
            PreparedStatement ps = this.con.prepareStatement("delete from tb_especie where id = ? ");
            ps.setInt(1, esp.getId());
            ps.execute();
        } else if (o instanceof Consulta) {
            Consulta cons = (Consulta) o;
            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_consulta where id = ?");
            ps2.setInt(1, cons.getId());
            ps2.execute();
        } else if (o instanceof Receita) {
            Receita rec = (Receita) o;
            PreparedStatement ps = this.con.prepareStatement("delete from tb_receita where id = ?");
            ps.setInt(1, rec.getId());
            ps.execute();
        } else if (o instanceof Medico) {
            Medico med = (Medico) o;
            PreparedStatement ps = this.con.prepareStatement("delete from tb_medico where cpf = ?");
            ps.setString(1, med.getCpf());
            ps.execute();
        } else if (o instanceof Cliente) {
            Cliente cli = (Cliente) o;
            PreparedStatement ps = this.con.prepareStatement("delete from tb_cliente where cpf = ?");
            ps.setString(1, cli.getCpf());
            ps.execute();
        } else if (o instanceof Pet) {
            Pet pt = (Pet) o;
            PreparedStatement ps = this.con.prepareStatement("delete from tb_pet where id = ?");
            ps.setInt(1, pt.getId());
            ps.execute();
        } else if (o instanceof Raca) {
            Raca rac = (Raca) o;
            PreparedStatement ps = this.con.prepareStatement("delete from tb_raca where id = ?");
            ps.setInt(1, rac.getId());
            ps.execute();
        } else if (o instanceof Venda) {
            Venda vd = (Venda) o;
            PreparedStatement ps = this.con.prepareStatement("delete from tb_venda where id = ?");
            ps.setInt(1, vd.getId());
            ps.execute();
        }
    }
}
