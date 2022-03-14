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
    private JPanel pnlCentro;
    private JScrollPane scpListagem;
    private JTable tblListagem;
    private DefaultTableModel modeloTabela;

    private JPanel pnlSul;
    private JButton btnCriar;
    private JButton btnEditar;
    private JButton btnRemover;

    private SimpleDateFormat sdfformat;

    public JPanelAFuncionarioListagem(JPanelAFuncionario pnlAFuncionario, Controle controle) {
        this.pnlAFuncionario = pnlAFuncionario;
        this.controle = controle;
        initComponents();
    }

    public void populaTable() {
        DefaultTableModel model = (DefaultTableModel) tblListagem.getModel();
        model.setRowCount(0);
        try {
            List<Funcionario> listFuncionarios = controle.getConexaoJDBC().listFuncionarios();
            for (Funcionario f : listFuncionarios) {
                model.addRow(new Object[]{f.getCpf(), f.getNome(), sdfformat.format(f.getData_cadastro().getTime()), f.getCargo(), f.getNumero_ctps(), f.getNumero_pis(), f.getEmail(), f.getNumero_celular(), f.getEndereco()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, " Erro ao listar os Funcionários:" + e.getLocalizedMessage(), "Funcionários", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void initComponents() {
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);

        pnlCentro = new JPanel();
        pnlCentro.setLayout(new BorderLayout());

        scpListagem = new JScrollPane();
        tblListagem = new JTable();

        modeloTabela = new DefaultTableModel(
                new Object[][]{}, new String[]{
                    "CPF", "Nome", "Data Cadastro", "Cargo", "CTPS", "PIS", "E-mail", "Celular", "Endereço"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        tblListagem.setModel(modeloTabela);
        scpListagem.setViewportView(tblListagem);

        pnlCentro.add(scpListagem, BorderLayout.CENTER);
        this.add(pnlCentro, BorderLayout.CENTER);

        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());

        btnCriar = new JButton("Criar");
        btnCriar.addActionListener(this);
        btnCriar.setFocusable(true);
        btnCriar.setToolTipText("Criar");
        btnCriar.setMnemonic(KeyEvent.VK_N);
        btnCriar.setActionCommand("botao_criar");
        pnlSul.add(btnCriar);

        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(this);
        btnEditar.setFocusable(true);
        btnEditar.setToolTipText("btnAlterar");
        btnEditar.setActionCommand("botao_alterar");
        pnlSul.add(btnEditar);

        btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this);
        btnRemover.setFocusable(true);
        btnRemover.setToolTipText("btnRemvoer");
        btnRemover.setActionCommand("botao_remover");
        pnlSul.add(btnRemover);

        this.add(pnlSul, BorderLayout.SOUTH);
        sdfformat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals(btnCriar.getActionCommand())) {
            pnlAFuncionario.showTela("tela_funcionario_formulario");
            pnlAFuncionario.getFormulario().setFuncionarioFormulario(null);
        } else if (arg0.getActionCommand().equals(btnEditar.getActionCommand())) {
            int indice = tblListagem.getSelectedRow();
            if (indice > -1) {
                try {
                    DefaultTableModel model = (DefaultTableModel) tblListagem.getModel();
                    Vector linha = (Vector) model.getDataVector().get(indice);
                    ///Funcionario f = new Funcionario();
                    Funcionario f = (Funcionario) linha.get(0);
                    f = (Funcionario) controle.getConexaoJDBC().find(Funcionario.class, linha.get(0));
                    pnlAFuncionario.showTela("tela_funcionario_formulario");
                    pnlAFuncionario.getFormulario().setFuncionarioFormulario(f);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao selecionar o Funcionário para Editar! Tente novamente mais tarde. " + e.getMessage(), "Edição", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma linha para editar!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (arg0.getActionCommand().equals(btnRemover.getActionCommand())) {
            int indice = tblListagem.getSelectedRow();
            if (indice > -1) {
                DefaultTableModel model = (DefaultTableModel) tblListagem.getModel();
                Vector linha = (Vector) model.getDataVector().get(indice);
                Funcionario f = new Funcionario();
                f.setCpf((String) linha.get(0));
                try {
                    pnlAFuncionario.getControle().getConexaoJDBC().remover(f);
                    JOptionPane.showMessageDialog(this, "Funcionário removido!", "Funcionário", JOptionPane.INFORMATION_MESSAGE);
                    populaTable();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover Funcionário:" + e.getLocalizedMessage(), "Funcionário", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma linha para remover!", "Remoção", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
