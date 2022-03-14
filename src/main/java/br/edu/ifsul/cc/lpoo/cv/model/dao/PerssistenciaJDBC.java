/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pagamento;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Pet;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import br.edu.ifsul.cc.lpoo.cv.model.TipoProduto;
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
            PreparedStatement ps = this.con.prepareStatement("select id,data_venda,observacao,pagamento,valortotal,cliente_id,funcionario_id from tb_venda where id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Venda vd = new Venda();
                vd.setId(rs.getInt("id"));
                Calendar dtv = Calendar.getInstance();
                dtv.setTimeInMillis(rs.getDate("data_venda").getTime());
                vd.setData(dtv);
                vd.setObservacao(rs.getString("observacao"));
                vd.setPagamento(Pagamento.valueOf(rs.getString("Pagamento")));
                vd.setValortotal(rs.getFloat("valortotal"));
                vd.setCliente((Cliente) find(Cliente.class, rs.getString("cliente_id")));
                vd.setFuncionario((Funcionario) find(Cliente.class, rs.getString("funcionario_id")));
                return vd;
            }
        } else if (c == Cliente.class) {
            PreparedStatement ps = this.con.prepareStatement("select cpf,data_ultima_visita from tb_cliente where cpf = ?");
            ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cliente cli = new Cliente();
                cli.setCpf(rs.getString("cpf"));
                Calendar dtuv = Calendar.getInstance();
                dtuv.setTimeInMillis(rs.getDate("data_ultima_visita").getTime());
                cli.setData_ultima_visita(dtuv);
                return cli;
            }
        } else if (c == Funcionario.class) {
            PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.cep, pes.complemento, pes.data_nascimento, pes.data_cadastro, pes.email, pes.endereco, pes.nome, pes.numero_celular, pes.rg, pes.senha, fun.cargo, fun.numero_ctps, fun.numero_pis\n"
                    + "from tb_pessoa as pes "
                    + "INNER JOIN tb_funcionario as fun on pes.cpf = fun.cpf WHERE "
                    + "pes.cpf = ?;");
            ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Funcionario func = new Funcionario();
                func.setCpf(rs.getString("cpf"));
                func.setComplemento(rs.getString("complemento"));
                func.setCep(rs.getString("cep"));
                if (rs.getDate("data_nascimento") != null) {
                    Calendar dtNascimento = Calendar.getInstance();
                    dtNascimento.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                    func.setData_nascimento(dtNascimento);
                }
                Calendar dtCadastro = Calendar.getInstance();
                dtCadastro.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                func.setData_cadastro(dtCadastro);
                func.setEmail(rs.getString("email"));
                func.setEndereco(rs.getString("endereco"));
                func.setNome(rs.getString("nome"));
                func.setNumero_celular(rs.getString("numero_celular"));
                func.setRg(rs.getString("rg"));
                func.setSenha(rs.getString("senha"));
                func.setCargo(Cargo.valueOf(rs.getString("cargo")));
                func.setNumero_ctps(rs.getString("numero_ctps"));
                func.setNumero_pis(rs.getString("numero_pis"));
                ps.close();
                return func;
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
                PreparedStatement ps = this.con.prepareStatement("insert into tb_venda(id,data_venda,observacao,pagamento,valortotal,cliente_id,funcionario_id) values (?,?,?,?,?,?,?)");
                ps.setInt(1, vd.getId());
                ps.setTimestamp(2, new Timestamp(vd.getData().getTimeInMillis()));
                ps.setString(3, vd.getObservacao());
                ps.setString(4, vd.getPagamento().toString());
                ps.setFloat(5, vd.getValortotal());
                ps.setString(6, vd.getCliente().getCpf());
                ps.setString(7, vd.getFuncionario().getNumero_pis());
                ps.execute();
            } else {///update
                PreparedStatement ps = this.con.prepareStatement("update tb_venda set data_venda = ?, observacao = ?, pagamento = ?, valortotal = ?, cliente_id = ?, funcionario_id = ? where id = ?");
                ///ps.setInt(1, vd.getId());
                ps.setTimestamp(1, new Timestamp(vd.getData().getTimeInMillis()));
                ps.setString(2, vd.getObservacao());
                ps.setString(3, vd.getPagamento().toString());
                ps.setFloat(4, vd.getValortotal());
                ps.setString(5, vd.getCliente().getCpf());
                ps.setString(6, vd.getFuncionario().getNumero_pis());
                ps.setInt(7, vd.getId());
                ps.executeUpdate();
            }
        } else if (o instanceof Funcionario) {
            Funcionario f = (Funcionario) o;
            if (f.getData_cadastro() == null) {
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa "
                        + "(cpf, complemento, cep, data_nascimento, data_cadastro, email, endereco, nome, numero_celular, rg, senha, tipo) values "
                        + "(?, ?, ?, ?, now(), ?, ?, ?, ?, ?, ?, ?); ");
                ps.setString(1, f.getCpf());
                ps.setString(2, f.getComplemento());
                ps.setString(3, f.getCep());
                if (f.getData_nascimento() != null) {
                    ps.setTimestamp(4, new Timestamp(f.getData_nascimento().getTimeInMillis()));
                } else {
                    ps.setTimestamp(4, null);
                }
                ps.setString(5, f.getEmail());
                ps.setString(6, f.getEndereco());
                ps.setString(7, f.getNome());
                ps.setString(8, f.getNumero_celular());
                ps.setString(9, f.getRg());
                ps.setString(10, f.getSenha());
                ps.setString(11, "F");
                ps.execute();
                PreparedStatement ps2 = this.con.prepareStatement("insert into tb_funcionario "
                        + "(cargo, numero_ctps, numero_pis, cpf) values "
                        + "(?, ?, ?, ?); ");
                ps2.setString(1, f.getCargo().toString());
                ps2.setString(2, f.getNumero_ctps());
                ps2.setString(3, f.getNumero_pis());
                ps2.setString(4, f.getCpf());
                ps2.execute();
                System.out.println("Funcionário com o CPF: " + f.getCpf() + " cadastrado com sucesso!\n");
            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_funcionario set cargo = ?, numero_ctps = ?, numero_pis = ? where cpf = ?; ");
                ps.setString(1, f.getCargo().toString());
                ps.setString(2, f.getNumero_ctps());
                ps.setString(3, f.getNumero_pis());
                ps.setString(4, f.getCpf());
                ps.execute();
                PreparedStatement ps2 = this.con.prepareStatement("update tb_pessoa set "
                        + "nome = ?, tipo = 'F', cep = ?, complemento = ?, data_nascimento = ?,"
                        + " email = ?, endereco = ?, numero_celular = ?, rg = ?, senha = ? where cpf = ?");
                ps2.setString(1, f.getNome());
                ps2.setString(2, f.getCep());
                ps2.setString(3, f.getComplemento());
                ps2.setTimestamp(4, new Timestamp(f.getData_nascimento().getTimeInMillis()));
                ps2.setString(5, f.getEmail());
                ps2.setString(6, f.getEndereco());
                ps2.setString(7, f.getNumero_celular());
                ps2.setString(8, f.getRg());
                ps2.setString(9, f.getSenha());
                ps2.setString(10, f.getCpf());
                ps2.execute();
                System.out.println("Funcionário com o CPF: " + f.getCpf() + " alterado com sucesso!\n");
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
            System.out.println("Consulta com ID: " + cons.getId() + " deletada com sucesso.");
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
            System.out.println("Venda com ID: " + vd.getId() + " deletada com sucesso.");
        } else if (o instanceof Funcionario) {
            Funcionario f = (Funcionario) o;
            PreparedStatement ps = this.con.prepareStatement("delete from tb_funcionario where cpf = (?);");
            ps.setString(1, f.getCpf());
            ps.execute();
            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_pessoa where cpf = (?);");
            ps2.setString(1, f.getCpf());
            ps2.execute();
            System.out.println("Funcionário com CPF: " + f.getCpf() + " deletado com sucesso.");
        }
    }

    @Override
    public List<Consulta> ListPerssistConsulta() throws Exception {
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
        List<Venda> list_venda = null;
        PreparedStatement ps = this.con.prepareStatement("select id,data_venda,observacao,pagamento,valortotal,cliente_id,funcionario_id from tb_venda");
        ResultSet rs = ps.executeQuery();
        list_venda = new ArrayList<>();
        while (rs.next()) {
            Venda vd = new Venda();
            vd.setId(rs.getInt("id"));
            if (rs.getDate("data_venda") != null) {
                Calendar dtVenda = Calendar.getInstance();
                dtVenda.setTimeInMillis(rs.getDate("data_venda").getTime());
                vd.setData(dtVenda);
            }
            vd.setObservacao(rs.getString("observacao"));
            vd.setPagamento(Pagamento.valueOf(rs.getString("Pagamento")));
            vd.setValortotal(rs.getFloat("valortotal"));
            vd.setCliente((Cliente) find(Cliente.class, rs.getString("cliente_id")));
            vd.setFuncionario((Funcionario) find(Funcionario.class, rs.getString("funcionario_id")));
            list_venda.add(vd);
        }
        return list_venda;
    }

    @Override
    public Pessoa doLogin(String cpf, String senha) throws Exception {
        Pessoa p = null;
        PreparedStatement ps = this.con.prepareStatement("select p.cpf,p.senha,p.nome from tb_pessoa as p,tb_funcionario as f where p.cpf = f.cpf and p.cpf = ? and p.senha = ?");
        ps.setString(1, cpf);
        ps.setString(2, senha);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            p = new Pessoa();
            p.setCpf(rs.getString("cpf"));
            p.setSenha(rs.getString("senha"));
            p.setNome(rs.getString("nome"));
        }
        ps.close();
        return p;
    }

    @Override
    public List<Funcionario> getFuncionarios() throws Exception {
        List<Funcionario> listfunc = null;
        PreparedStatement ps = this.con.prepareStatement("select f.cpf,f.numero_ctps,f.numero_pis from tb_funcionario as f");
        ResultSet rs = ps.executeQuery();
        listfunc = new ArrayList<>();
        while (rs.next()) {
            Funcionario f = new Funcionario();
            f.setCpf(rs.getString("CPF"));
            f.setNumero_ctps(rs.getString("numero_ctps"));
            f.setNumero_pis(rs.getString("numero_pis"));
            listfunc.add(f);
        }
        return listfunc;
    }

    public List<Cliente> listClientes() throws Exception {
        List<Cliente> listaC = null;
        String URL = "select cpf,data_ultima_visita from tb_cliente";
        PreparedStatement ps = this.con.prepareStatement(URL);
        ResultSet rs = ps.executeQuery();
        listaC = new ArrayList<>();
        while (rs.next()) {
            Cliente cli = new Cliente();
            cli.setCpf(rs.getString("cpf"));
            Calendar dtuv = Calendar.getInstance();
            dtuv.setTimeInMillis(rs.getDate("data_ultima_visita").getTime());
            cli.setData_ultima_visita(dtuv);
            listaC.add(cli);
        }
        return listaC;
    }

    public List<Produto> listProdutos() throws Exception {
        List<Produto> listprodutos = null;
        String URL = "select id,nome,quantidade,tipo,valor,fornecedor_id from tb_produto order by id asc";
        PreparedStatement ps = this.con.prepareStatement(URL);
        ResultSet rs = ps.executeQuery();
        listprodutos = new ArrayList<>();
        while (rs.next()) {
            Produto pd = new Produto();
            pd.setId(rs.getInt("id"));
            pd.setNome(rs.getString("nome"));
            pd.setQuantidade(rs.getFloat("quantidade"));
            pd.setTipo(TipoProduto.valueOf("TipoProduto"));
            pd.setValor(rs.getFloat("valor"));
            pd.setFornecedor((Fornecedor) find(Fornecedor.class, rs.getInt("fornecedor_id")));
            listprodutos.add(pd);
        }
        return listprodutos;
    }

    @Override
    public List<Funcionario> listFuncionarios() throws Exception {
        List<Funcionario> lista = null;
        PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.cep, pes.complemento, pes.data_nascimento, pes.data_cadastro, pes.email, pes.endereco, pes.nome, pes.numero_celular, pes.rg, pes.senha, fun.cargo, fun.numero_ctps, fun.numero_pis from tb_pessoa as pes INNER JOIN tb_funcionario as fun on pes.cpf = fun.cpf");
        ResultSet rs = ps.executeQuery();
        lista = new ArrayList();
        while (rs.next()) {
            Funcionario fun = new Funcionario();
            fun.setCpf(rs.getString("cpf"));
            fun.setComplemento(rs.getString("complemento"));
            fun.setCep(rs.getString("cep"));
            Calendar dtCadastro = Calendar.getInstance();
            dtCadastro.setTimeInMillis(rs.getDate("data_cadastro").getTime());
            fun.setData_cadastro(dtCadastro);
            if (rs.getDate("data_nascimento") != null) {
                Calendar dtNascimento = Calendar.getInstance();
                dtNascimento.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                fun.setData_nascimento(dtNascimento);
            }
            fun.setEmail(rs.getString("email"));
            fun.setEndereco(rs.getString("endereco"));
            fun.setNome(rs.getString("nome"));
            fun.setNumero_celular(rs.getString("numero_celular"));
            fun.setRg(rs.getString("rg"));
            fun.setSenha(rs.getString("senha"));
            fun.setCargo(Cargo.valueOf(rs.getString("cargo")));
            fun.setNumero_ctps(rs.getString("numero_ctps"));
            fun.setNumero_pis(rs.getString("numero_pis"));
            lista.add(fun);
        }
        return lista;
    }
}
