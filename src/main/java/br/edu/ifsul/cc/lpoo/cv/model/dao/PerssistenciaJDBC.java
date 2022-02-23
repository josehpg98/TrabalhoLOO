/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pagamento;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
            System.out.println("M~so foi possivel estabelecer uma conexão!");
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
            System.out.println("Falha ao conectar ao via JDBC: " + URL + "'");
        }
        return false;
    }

    @Override
    public void fecharConexao() {
        try {
            this.con.close();//fecha a conexao via JDBC.
            System.out.println("Fechou conexao via JDBC!");
        } catch (SQLException e) {
            System.out.println("Falha ao fechar conexao com JDBC: '" + URL + "'");
        }
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        if (c == Consulta.class) {
            PreparedStatement ps = this.con.prepareStatement("select c.id,c.data,c.data_retorno,c.observacao,c.valor,c.medico_id,c.pet_id from tb_consulta as c inner join tb_receita as r on c.id = r.consulta_id where c.id = ?");
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
            PreparedStatement ps = this.con.prepareStatement("select data_cadastro_medico,numero_crmv, cpf from tb_medico where cpf = ?");
            ps.setString(1, id.toString());
            ResultSet rsm = ps.executeQuery();
            if (rsm.next()) {
                Medico med = new Medico();
                Calendar dcm = Calendar.getInstance();
                dcm.setTimeInMillis(rsm.getDate("data_cadastro_medico").getTime());
                med.setNumero_crmv(rsm.getString("numero_crmv"));
                med.setCpf(rsm.getString("cpf"));
                return med;
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
                Calendar dtv = Calendar.getInstance();
                dtv.setTimeInMillis(rs.getDate("data").getTime());
                vd.setData(dtv);
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
                ps.setTimestamp(1, new Timestamp(med.getData_cadastro_Medico().getTimeInMillis()));
                ps.setString(2, med.getNumero_crmv());
                ps.setString(3, med.getCpf());
                ps.execute();
            } else {//update
                PreparedStatement ps = this.con.prepareStatement("update tb_medico set umero_crmv = ?, cpf = ? where data_cadastro_medico = ?");
                ps.setString(1, med.getNumero_crmv());
                ps.setString(2, med.getCpf());
                ps.executeUpdate();
            }
        } else if (o instanceof Venda) {
            Venda vd = (Venda) o;
            if (vd.getId() == null) {///insert
                PreparedStatement ps = this.con.prepareStatement("insert into tb_venda  id, data, observacao, pagamento, valortotal, cliente_id, funcionario_id) values (?,?,?,?,?,?,?)");
                ps.setInt(1, vd.getId());
                ps.setTimestamp(2, new Timestamp(vd.getData().getTimeInMillis()));
                ps.setString(3, vd.getObservacao());
                ps.setString(4, "pagamento");
                ps.setFloat(5, vd.getValortotal());
                ps.setString(6, vd.getCliente().getCpf());
                ps.setString(7, vd.getFuncionario().getNumero_pis());
                ps.execute();
            } else {///update
                PreparedStatement ps = this.con.prepareStatement("update tb_venda set data = ?, observacao = ?, pagamento = ?, valortotal = ?, cliente_id = ?, funcionario_id = ?, where id = ?");
                ps.setInt(1, vd.getId());
                ps.setTimestamp(2, new Timestamp(vd.getData().getTimeInMillis()));
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
        if (o instanceof Consulta) {
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
        } else if (o instanceof Venda) {
            Venda vd = (Venda) o;
            PreparedStatement ps = this.con.prepareStatement("delete from tb_venda where id = ?");
            ps.setInt(1, vd.getId());
            ps.execute();
        }
    }

    @Override
    public List<Consulta> ListPerssistConsulta() throws Exception {
        ///Falta data e data_reotrno
        List<Consulta> lista_cons = null;
        PreparedStatement ps = this.con.prepareStatement("select id,observacao,valor,medico_id,pet_id from tb_consulta");
        ResultSet rs = ps.executeQuery();
        lista_cons = new ArrayList<>();
        while (rs.next()) {
            Consulta cons = new Consulta();
            cons.setId(rs.getInt("id"));
            cons.setObservacao(rs.getString("observacao"));
            cons.setValor(rs.getFloat("valor"));
            cons.setMedico((Medico) find(Medico.class, rs.getInt("medico_id")));
            cons.setPet((Pet) find(Pet.class, rs.getInt("pet_id")));
            lista_cons.add(cons);
        }
        return lista_cons;
    }

    @Override
    public List<Venda> ListPerssistVenda() throws Exception {
        ///Faltou a data.
        List<Venda> list_venda = null;
        PreparedStatement ps = this.con.prepareStatement("select id,observacao,pagamento,valortotal,cliente_id,funcionario_id from tb_venda");
        ResultSet rs = ps.executeQuery();
        list_venda = new ArrayList<>();
        while (rs.next()) {
            Venda vd = new Venda();
            vd.setId(rs.getInt("id"));
            vd.setObservacao(rs.getString("observaco"));
            vd.setPagamento(Pagamento.DINHEIRO);
            vd.setValortotal(rs.getFloat("valortotal"));
            vd.setCliente((Cliente) find(Cliente.class, rs.getInt("cliente_id")));
            vd.setFuncionario((Funcionario) find(Funcionario.class, rs.getInt("funcionario_id")));
            list_venda.add(vd);
        }
        return list_venda;
    }
    
      @Override
    public Funcionario doLogin(String cpf, String senha) throws Exception {
        
                
        Funcionario func = null;
        
         PreparedStatement ps = 
            this.con.prepareStatement("select j.nickname, j.senha from tb_jogador j where j.nickname= ? and j.senha = ? ");
                        
            ps.setString(1, cpf);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery();//o ponteiro do REsultSet inicialmente está na linha -1
            
            if(rs.next()){//se a matriz (ResultSet) tem uma linha

                func = new Funcionario();
                func.setCpf(rs.getString("CPF"));                
            }
        
            ps.close();
            return func;        
        
    }
}
