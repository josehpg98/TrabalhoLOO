/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.gui;

import br.edu.ifsul.cc.lpoo.cv1.Controle;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author José Henrique PG
 */
public class JMenuBarHome extends JMenuBar implements ActionListener {

    private JMenu menuArquivo;
    private JMenuItem menuItemLogout;
    private JMenuItem menuItemSair;

    private JMenu menuCadastro;
    private JMenuItem menuItemFuncionario;
    private JMenuItem menuItemVenda;///add um item a barra
    private JMenuItem menuItemConsulta;

    private Controle controle;

    public JMenuBarHome(Controle controle) {

        this.controle = controle;

        initComponents();
    }

    private void initComponents() {

        menuArquivo = new JMenu("Arquivo");
        menuArquivo.setMnemonic(KeyEvent.VK_A);//ativa o ALT + A para acessar esse menu - acessibilidade
        menuArquivo.setToolTipText("Arquivo"); //acessibilidade
        menuArquivo.setFocusable(true); //acessibilidade

        menuItemSair = new JMenuItem("Sair");
        menuItemSair.setToolTipText("Sair"); //acessibilidade
        menuItemSair.setFocusable(true);     //acessibilidade

        menuItemLogout = new JMenuItem("Logout");
        menuItemLogout.setToolTipText("Logout"); //acessibilidade
        menuItemLogout.setFocusable(true);     //acessibilidade

        menuItemLogout.addActionListener(this);
        menuItemLogout.setActionCommand("menu_logout");
        menuArquivo.add(menuItemLogout);

        menuItemSair.addActionListener(this);
        menuItemSair.setActionCommand("menu_sair");
        menuArquivo.add(menuItemSair);

        menuCadastro = new JMenu("Cadastros");
        menuCadastro.setMnemonic(KeyEvent.VK_C);//ativa o ALT + C para acessar esse menu - acessibilidade
        menuCadastro.setToolTipText("Cadastro"); //acessibilidade
        menuCadastro.setFocusable(true); //acessibilidade

        menuItemConsulta = new JMenuItem("Consultas");
        menuItemConsulta.setToolTipText("Consultas"); //acessibilidade
        menuItemConsulta.setFocusable(true); //acessibilidade

        menuItemConsulta.addActionListener(this);
        menuItemConsulta.setActionCommand("menu_consulta");
        menuCadastro.add(menuItemConsulta);

        menuItemFuncionario = new JMenuItem("Funcionário");
        menuItemFuncionario.setToolTipText("Funcionario"); //acessibilidade
        menuItemFuncionario.setFocusable(true); //acessibilidade

        menuItemFuncionario.addActionListener(this);
        menuItemFuncionario.setActionCommand("menu_funcionario");
        menuCadastro.add(menuItemFuncionario);

        ///adiciona um item ao menu, no caso venda
        menuItemVenda = new JMenuItem("Vendas");
        menuItemVenda.setToolTipText("Vendas");
        menuItemVenda.setFocusable(true);

        ///adiciona o item venda ao menu cadastros
        menuItemVenda.addActionListener(this);
        menuItemVenda.setActionCommand("menu_Venda");
        menuCadastro.add(menuItemVenda);

        this.add(menuArquivo);
        this.add(menuCadastro);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(menuItemSair.getActionCommand())) {

            //se o usuario clicou no menuitem Sair
            int d = JOptionPane.showConfirmDialog(this, "Deseja realmente sair do sistema? ", "Sair", JOptionPane.YES_NO_OPTION);
            if (d == 0) {
                controle.fecharBD();//fecha a conexao com o banco de dados.
                System.exit(0);//finaliza o processo do programa.
            }

        } else if (e.getActionCommand().equals(menuItemFuncionario.getActionCommand())) {

            //se o usuario clicou no menuitem Usuario            
            controle.showTela("tela_funcionario_a");

        } else if (e.getActionCommand().equals(menuItemLogout.getActionCommand())) {

            controle.showTela("tela_autenticacao");
        }

    }
}
