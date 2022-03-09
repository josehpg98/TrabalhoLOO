/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv1.Controle;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author José Henrique PG
 */
public class JPanelAFuncionarioListagem extends JPanel implements ActionListener {

    private JPanelAFuncionario pnlAFuncionario;
    private Controle controle;
    private BorderLayout borderLayout;
    private JPanel pnlNorte;
    private JLabel lblFiltro;
    private JTextField txfFiltro;
    private JButton btnFiltro;
    private JPanel pnlCentro;
    private JScrollPane scpListagem;
    private JTable tblListagem;
    private DefaultTableModel modeloTabela;
    private JPanel pnlSul;
    private JButton btnNovo;
    private JButton btnAlterar;
    private JButton btnRemover;
    private SimpleDateFormat format;

    public JPanelAFuncionarioListagem(JPanelAFuncionario pnlAFuncionario, Controle controle) {
        this.pnlAFuncionario = pnlAFuncionario;
        this.controle = controle;
        initComponents();
    }

    public void populaTable() {
        ///throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        DefaultTableModel model = (DefaultTableModel) tblListagem.getModel();//recuperacao do modelo da tabela
        model.setRowCount(0);//elimina as linhas existentes (reset na tabela)
        try {
            List<Funcionario> listFuncionarios = controle.getConexaoJDBC().listFuncionarios();
            ///for (Funcionario f : listFuncionarios) {
                ///model.addRow(new Object() f, format(f.getCpf().gettex));
                ///j.getData_cadastro().getTime()), j.getData_ultimo_login(), j.getEndereco().getCep()}
            ///}
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar Funcionarios -:" + ex.getLocalizedMessage(), "  Funcionarios ", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void initComponents() {
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);///seta o gerenciado border para este painel
        pnlNorte = new JPanel();
        pnlNorte.setLayout(new FlowLayout());
        lblFiltro = new JLabel("Filtrar por CPF:");
        pnlNorte.add(lblFiltro);
        txfFiltro = new JTextField(20);
        pnlNorte.add(txfFiltro);
        btnFiltro = new JButton("Filtrar");
        btnFiltro.addActionListener(this);
        btnFiltro.setFocusable(true);///acessibilidade    
        btnFiltro.setToolTipText("btnFiltrar");///acessibilidade  
        btnFiltro.setActionCommand("botao_filtro");
        pnlNorte.add(btnFiltro);
        this.add(pnlNorte, BorderLayout.NORTH);//adiciona o painel na posicao norte.
        pnlCentro = new JPanel();
        pnlCentro.setLayout(new BorderLayout());
        scpListagem = new JScrollPane();
        tblListagem = new JTable();
        modeloTabela = new DefaultTableModel(
                new String[]{
                    "CPF", "CTPS", "PIS", "Cargo"
                }, 0);
        tblListagem.setModel(modeloTabela);
        scpListagem.setViewportView(tblListagem);
        pnlCentro.add(scpListagem, BorderLayout.CENTER);
        this.add(pnlCentro, BorderLayout.CENTER);//adiciona o painel na posicao norte.
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(this);
        btnNovo.setFocusable(true);///acessibilidade    
        btnNovo.setToolTipText("btnNovo");///acessibilidade
        btnNovo.setMnemonic(KeyEvent.VK_N);
        btnNovo.setActionCommand("botao_novo");
        pnlSul.add(btnNovo);
        btnAlterar = new JButton("Editar");
        btnAlterar.addActionListener(this);
        btnAlterar.setFocusable(true);///acessibilidade    
        btnAlterar.setToolTipText("btnAlterar");///acessibilidade
        btnAlterar.setActionCommand("botao_alterar");
        pnlSul.add(btnAlterar);
        btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this);
        btnRemover.setFocusable(true);///acessibilidade    
        btnRemover.setToolTipText("btnRemvoer");///acessibilidade
        btnRemover.setActionCommand("botao_remover");
        pnlSul.add(btnRemover);//adiciona o botao na fila organizada pelo flowlayout
        this.add(pnlSul, BorderLayout.SOUTH);//adiciona o painel na posicao norte.
        format = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        ///throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (arg0.getActionCommand().equals(btnNovo.getActionCommand())) {
            pnlAFuncionario.showTela("tela_funncionario_formulario");
            pnlAFuncionario.getFormulario().setFuncionarioFormulario(null);///limpando o formulário.                        
        } else if (arg0.getActionCommand().equals(btnAlterar.getActionCommand())) {
            int indice = tblListagem.getSelectedRow();///recupera a linha selecionada
            if (indice > -1) {
                DefaultTableModel model = (DefaultTableModel) tblListagem.getModel();///recuperacao do modelo da table
                Vector linha = (Vector) model.getDataVector().get(indice);///recupera o vetor de dados da linha selecionada
                Funcionario f = (Funcionario) linha.get(0); //model.addRow(new Object[]{u, u.getNome(), ...
                pnlAFuncionario.showTela("tela_funcionario_formulario");
                pnlAFuncionario.getFormulario().setFuncionarioFormulario(f);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma linha para editar!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (arg0.getActionCommand().equals(btnRemover.getActionCommand())) {
            int indice = tblListagem.getSelectedRow();//recupera a linha selecionada
            if (indice > -1) {
                DefaultTableModel model = (DefaultTableModel) tblListagem.getModel(); //recuperacao do modelo da table
                Vector linha = (Vector) model.getDataVector().get(indice);//recupera o vetor de dados da linha selecionada
                Funcionario f = (Funcionario) linha.get(0); //model.addRow(new Object[]{u, u.getNome(), ...
                try {
                    pnlAFuncionario.getControle().getConexaoJDBC().remover(f);
                    JOptionPane.showMessageDialog(this, "Funcionario removido!", "Funcionario", JOptionPane.INFORMATION_MESSAGE);
                    populaTable();////refresh na tabela
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover Funcionario -:" + ex.getLocalizedMessage(), "Funcionarios", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma linha para remover!", "Remoção", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
