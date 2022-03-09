/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.gui.venda.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import br.edu.ifsul.cc.lpoo.cv1.Controle;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import org.postgresql.jdbc2.optional.SimpleDataSource;

/**
 *
 * @author José Henrique PG
 */
public class JPanelAVendaFormulario extends JPanel implements ActionListener {

    private JPanelAVenda pnlAvenda;
    private Controle controle;
    private BorderLayout borderLayout;
    private JTabbedPane tbpAbas;
    private JPanel pnlDadosCadastrais;
    private JPanel pnlCentroDadosCadastrais;
    private GridBagLayout gridBagLayoutDadosCadastrais;

    private JLabel lblId;
    private JTextField txfId;
    private JLabel lblObservacao;
    private JTextField txfObservacao;
    private JLabel lblValorTotal;
    private JTextField txfValorTotal;
    private JLabel lbldataVenda;
    private JTextField txfdataVenda;

    private JLabel lblCliente;
    private JComboBox cbxCliente;
    private JLabel lblFuncionario;
    private JComboBox cbxFuncionario;
    private JLabel lblProduto;
    private JComboBox cbxProduto;

    private JPanel pnlDadosClientes;
    private JPanel pnlFuncionarios;
    private JPanel pnlProdutos;

    private Venda venda;
    private SimpleDateFormat format;
    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;

    JPanelAVendaFormulario(JPanelAVenda pnlAVenda, Controle controle) {
        this.pnlAvenda = pnlAVenda;
        this.controle = controle;
        initComponents();
    }

    public void PopulaComboCliente() {
        cbxCliente.removeAllItems();///zera o combo box
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxCliente.getModel();
        model.addElement("Selecione:");
        try {
            List<Cliente> listClientes = controle.getConexaoJDBC().listClientes();
            for (Cliente c : listClientes) {
                model.addElement(c);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar os Clientes -:" + e.getLocalizedMessage(), "Clientes", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void PopulaComboFuncionario() {
        cbxFuncionario.removeAllItems();
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxFuncionario.getModel();
        model.addElement("Selecione:");
        try {
            List<Funcionario> listfuncionarios = controle.getConexaoJDBC().listFuncionarios();
            for (Funcionario f : listfuncionarios) {
                model.addElement(f);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar os Funcionarios -:" + e.getLocalizedMessage(), "Funcionarios", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void PopulaComboProdutos() {
        cbxProduto.removeAllItems();
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxProduto.getModel();
        model.addElement("Selecione:");
        try {
            List<Produto> listProdutos = controle.getConexaoJDBC().listProdutos();
            for (Produto p : listProdutos) {
                model.addElement(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar os Produtos -:" + e.getLocalizedMessage(), "Produtos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public Venda getVendabyFormulario() {
        if (txfId != null && cbxCliente.getSelectedIndex() > 0) {
            Venda v = new Venda();
            v.setId(Integer.parseInt(txfId.getText().trim()));
            v.setObservacao(txfObservacao.getText().trim());
            v.setValortotal(Float.parseFloat(txfValorTotal.getText().trim()));
            if (venda != null) {
                v.setData(venda.getData());
            }
            v.setCliente((Cliente) cbxCliente.getSelectedItem());
            v.setFuncionario((Funcionario) cbxFuncionario.getSelectedItem());
            v.setProdutos((List<Produto>) (Produto) cbxProduto.getSelectedItem());
            return v;
        }
        return null;
    }

    public void setVendaFormulario(Venda v) {
        if (v == null) {
            txfId.setText("");
            txfObservacao.setText("");
            txfValorTotal.setText("");
            txfdataVenda.setText("");
            txfId.setEditable(true);
            venda = null;
        } else {
            venda = v;
            txfId.setEditable(false);
            txfId.setText(venda.getId().toString());
            txfObservacao.setText(venda.getObservacao());
            txfValorTotal.setText(venda.getValortotal().toString());
            txfdataVenda.setText(format.format(v.getData().getTime()));
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals(btnGravar.getActionCommand())) {
            Venda v = getVendabyFormulario();//recupera os dados do formulÃ¡rio       
            if (v != null) {
                try {
                    pnlAvenda.getControle().getConexaoJDBC().persist(v);
                    JOptionPane.showMessageDialog(this, "Venda armazenada!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                    pnlAvenda.showTela("tela_venda_listagem");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar a Venda! : " + ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {
            pnlAvenda.showTela("tela_venda_listagem");
        }
    }

    private void initComponents() {
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        tbpAbas = new JTabbedPane();
        this.add(tbpAbas, BorderLayout.CENTER);
        pnlDadosCadastrais = new JPanel();
        gridBagLayoutDadosCadastrais = new GridBagLayout();
        pnlDadosCadastrais.setLayout(gridBagLayoutDadosCadastrais);

        lblId = new JLabel("Id");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblId, posicionador);//o add adiciona o rotulo no painel  
        
        txfId = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;
        posicionador.gridx = 1;
        pnlDadosCadastrais.add(txfId, posicionador);
        
        lblObservacao = new JLabel("Observação: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblObservacao, posicionador);//o add adiciona o rotulo no painel  

        txfObservacao = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(txfObservacao, posicionador);//o add adiciona o rotulo no painel 

        lblValorTotal = new JLabel("Valor Venda: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblValorTotal, posicionador);//o add adiciona o rotulo no painel  

        txfValorTotal = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(txfValorTotal, posicionador);//o add adiciona o rotulo no painel 
        
       lbldataVenda = new JLabel("Data Venda: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lbldataVenda, posicionador);//o add adiciona o rotulo no painel  

        txfdataVenda = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(txfdataVenda, posicionador);//o add adiciona o rotulo no painel 
        
        tbpAbas.addTab("Dados Cdastrais", pnlDadosCadastrais);
        pnlDadosClientes = new JPanel();
       
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true);///acessibilidade    
        btnGravar.setToolTipText("btnGravarVenda");///acessibilidade
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_venda");
        pnlSul.add(btnGravar);
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true);///acessibilidade    
        btnCancelar.setToolTipText("btnCancelarvenda");///acessibilidade
        btnCancelar.setActionCommand("botao_cancelar_formulario_venda");
        pnlSul.add(btnCancelar);
        this.add(pnlSul, BorderLayout.SOUTH);
        format = new SimpleDateFormat("dd/MM/yyyy");
    }
}
