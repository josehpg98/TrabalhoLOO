/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.gui.venda.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pagamento;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    private GridBagLayout gridBagLayoutDadosCadastrais;

    private JLabel lblId;
    private JTextField txfId;
    private JLabel lbldataVenda;
    private JTextField txfdataVenda;
    private JLabel lblObservacao;
    private JTextField txfObservacao;
    private JLabel lblPagaamento;
    private JComboBox cbxPagamento;
    private JLabel lblValorTotal;
    private JTextField txfValorTotal;
    private JLabel lblCliente;
    private JComboBox cbxCliente;
    private JLabel lblFuncionario;
    private JComboBox cbxFuncionario;
    private JLabel lblProduto;
    private JComboBox cbxProduto;

    private Venda venda;
    private SimpleDateFormat format;
    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;

    JPanelAVendaFormulario(JPanelAVenda pnlAVenda, Controle controle) {
        this.pnlAvenda = pnlAVenda;
        this.controle = controle;
        this.format = new SimpleDateFormat("dd/MM/yyyy");
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

    public void PopulaComboPagamento() {
        cbxPagamento.removeAllItems();
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxPagamento.getModel();
        model.addElement("Selecione");
        try {
            List<Pagamento> listPagamento = Arrays.asList(Pagamento.values());
            listPagamento.forEach(c -> {
                model.addElement(c);
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar os Pagamentos:" + e.getLocalizedMessage(), "Pagamentos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public Venda getVendabyFormulario() throws ParseException {
        if (txfId == null && cbxPagamento.getSelectedIndex() > 0) {
            Venda v = new Venda();
            v.setId(Integer.parseInt(txfId.getText().trim()));
            if (txfdataVenda.getText().trim().length() > 1) {
                Calendar dtVenda = Calendar.getInstance();
                dtVenda.setTime(format.parse(txfdataVenda.getText()));
                v.setData(dtVenda);
            }
            v.setObservacao(txfObservacao.getText());
            v.setPagamento((Pagamento) cbxPagamento.getSelectedItem());
            v.setValortotal(Float.parseFloat(txfValorTotal.getText().trim()));
            if (venda != null) {
                v.setData(venda.getData());
            }
            v.setCliente((Cliente) cbxCliente.getSelectedItem());
            v.setFuncionario((Funcionario) cbxFuncionario.getSelectedItem());
            return v;
        }
        return null;
    }

    public void setVendaFormulario(Venda v) {
        if (v == null) {
            txfId.setText("");
            txfdataVenda.setText(format.format(Calendar.getInstance().getTime()));
            txfObservacao.setText("");
            cbxPagamento.setSelectedIndex(0);
            txfValorTotal.setText("");
            cbxCliente.setSelectedIndex(0);
            cbxFuncionario.setSelectedIndex(0);
            txfId.setEditable(true);
            venda = null;
        } else {
            venda = v;
            txfId.setEditable(false);
            txfId.setText(venda.getId().toString());
            txfdataVenda.setText(format.format(v.getData().getTime()));
            txfObservacao.setText(venda.getObservacao());
            cbxPagamento.getModel().setSelectedItem(venda.getPagamento());
            txfValorTotal.setText(venda.getValortotal().toString());
            cbxCliente.getModel().setSelectedItem(venda.getCliente());
            cbxFuncionario.getModel().setSelectedItem(venda.getFuncionario());
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

        lbldataVenda = new JLabel("Data Venda:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lbldataVenda, posicionador);

        txfdataVenda = new JTextField(10);
        txfdataVenda.setEditable(false);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfdataVenda, posicionador);

        lblObservacao = new JLabel("Observação: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblObservacao, posicionador);//o add adiciona o rotulo no painel  

        txfObservacao = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(txfObservacao, posicionador);//o add adiciona o rotulo no painel 

        lblPagaamento = new JLabel("Pagamento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblPagaamento, posicionador);

        cbxPagamento = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(cbxPagamento, posicionador);

        lblValorTotal = new JLabel("Valor Venda: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblValorTotal, posicionador);//o add adiciona o rotulo no painel  

        txfValorTotal = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(txfValorTotal, posicionador);//o add adiciona o rotulo no painel 

       lblCliente = new JLabel("Cliente:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblCliente, posicionador);

        cbxCliente = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(cbxCliente, posicionador);
        
        lblFuncionario = new JLabel("Funcionario:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblFuncionario, posicionador);

        cbxFuncionario = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(cbxFuncionario, posicionador);

        tbpAbas.addTab("Dados Cdastrais", pnlDadosCadastrais);

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

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals(btnGravar.getActionCommand())) {
            Venda v;
            try {
                v = getVendabyFormulario();
                if (v != null) {
                    try {
                        pnlAvenda.getControle().getConexaoJDBC().persist(v);
                        JOptionPane.showMessageDialog(this, "Venda salva com sucesso!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                        pnlAvenda.showTela("tela_venda_listagem");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Erro ao salvar o Venda! : " + e.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar a Venda! : " + e.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else if (arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {
            setVendaFormulario(null);
            pnlAvenda.showTela("tela_venda_listagem");
        }
    }
}
