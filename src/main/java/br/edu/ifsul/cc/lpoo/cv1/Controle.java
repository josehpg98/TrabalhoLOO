package br.edu.ifsul.cc.lpoo.cv1;

import br.edu.ifsul.cc.lpoo.cv.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cv.gui.JMenuBarHome;
import br.edu.ifsul.cc.lpoo.cv.gui.JPanelHome;
import br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade.JPanelAFuncionario;
import br.edu.ifsul.cc.lpoo.cv.gui.venda.acessibilidade.JPanelAVenda;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PerssistenciaJDBC;
import br.edu.ifsul.lpoo.cv.gui.autenticacao.JPanelAutenticacao;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author José Henrique PG
 */
public class Controle {

    private PerssistenciaJDBC conexaoJDBC;
    private JFramePrincipal frame;///frame principal da minha aplicação gráfica
    private JPanelAutenticacao pnlAutenticacao;///painel para a autenticacao do funcionario.
    private JMenuBarHome menuBar;///menu principal
    private JPanelHome pnlHome;/// painel de boas vindas (home)
    ///private JPanelFuncionario pnlFuncionario;/// painel de manutencao para funcionario.
    private JPanelAFuncionario pnlAFuncionario;///painel manutenção funcionario
    private JPanelAVenda pnlAVenda;

    ///construtor.
    public Controle() {

    }

    public boolean conectarBD() throws Exception {
        conexaoJDBC = new PerssistenciaJDBC();///nova conexão jdbc
        if (conexaoJDBC != null) {///se não for nula
            return conexaoJDBC.conexaoAberta();///retorna aberta
        }///caso contrario
        return false;
    }

    public void fecharBD() {
        System.out.println("Fechando conexao com o Banco de Dados!");
        conexaoJDBC.fecharConexao();
    }

    public void initComponents() {
        frame = new JFramePrincipal();
        pnlAutenticacao = new JPanelAutenticacao(this);
        menuBar = new JMenuBarHome(this);
        pnlHome = new JPanelHome(this);
        pnlAFuncionario = new JPanelAFuncionario(this);
        pnlAVenda = new JPanelAVenda(this);
        frame.addTela(pnlAutenticacao, "tela_autenticacao");//carta 1
        frame.addTela(pnlHome, "tela_home");//carta 2
         frame.addTela(pnlAVenda, "tela_venda_a");
        frame.addTela(pnlAFuncionario, "tela_funcionario_a");//carta 3 - poderia adicionar opcionalmente: pnlJogador
        frame.showTela("tela_autenticacao"); // apreseta a carta cujo nome é "tela_autenticacao"  
        frame.setVisible(true); // torna visível o jframe
    }

    public void autenticar(String cpf, String senha) {
        try {
            Pessoa p = getConexaoJDBC().doLogin(cpf, senha);
            if (p != null) {
                JOptionPane.showMessageDialog(pnlAutenticacao, "Funcionario  "+p.getNome()+" autenticado com sucesso!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
                frame.setJMenuBar(menuBar);//adiciona o menu de barra no frame
                frame.showTela("tela_home");//muda a tela para o painel de boas vindas (home)
            } else {
                JOptionPane.showMessageDialog(pnlAutenticacao, "Dados inválidos!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pnlAutenticacao, "Erro ao executar a autenticação no Banco de Dados!", "Autenticação", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showTela(String nomeTela) {
        if (nomeTela.equals("tela_autenticacao")) {
            pnlAutenticacao.cleanForm();
            frame.showTela(nomeTela);
            pnlAutenticacao.requestFocus();
        } else if(nomeTela.equals("tela_home")) {
            frame.showTela(nomeTela);
        } else if(nomeTela.equals("tela_funcionario_a")) {
            pnlAFuncionario.showTela("tela_funcionario_listagem");
            frame.showTela(nomeTela);
        }else if(nomeTela.equals("tela_venda_a")){
            pnlAVenda.showTela("tela_venda_listagem");
            frame.showTela(nomeTela);
        }
    }

    public PerssistenciaJDBC getConexaoJDBC() {
        return conexaoJDBC;
    }
}
