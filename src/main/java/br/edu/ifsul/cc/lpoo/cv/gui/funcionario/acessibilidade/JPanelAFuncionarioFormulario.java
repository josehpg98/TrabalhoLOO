/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv1.Controle;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author José Henrique PG
 */
public class JPanelAFuncionarioFormulario extends JPanel implements ActionListener {

    private JPanelAFuncionario pnlAFuncionario;
    private Controle controle;
    private BorderLayout borderLayout;
    private JTabbedPane tbpAbas;
    private JPanel pnlDadosCadastrais;
    private JPanel pnlCentroDadosCadastrais;
    private GridBagLayout gridBagLayoutDadosCadastrais;

    private JLabel lblCPF;
    private JTextField txfCPF;
    private JLabel lblSenha;
    private JPasswordField txfSenha;
    private JLabel lblCTPS;
    private JTextField txfCTPS;
    private JLabel lblPIS;
    private JTextField txfPIS;
    private JTextField lblcargo;
    private JComboBox cbxcargo;

    private Funcionario funcionario;
    private SimpleDateFormat format;
    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;

    ///private JPanel pnlDadosCompras;
    ///private JPanel pnlDadosArtefatos;
    ///private JPanel pnlDadosPatentes;
    public JPanelAFuncionarioFormulario(JPanelAFuncionario pnlAFuncionario, Controle controle) {
        this.pnlAFuncionario = pnlAFuncionario;
        this.controle = controle;
        initComponents();
    }

    ///ver popula combo box
    public Funcionario getFuncionariobyFormulario() {
        ///validação do form.
        if (txfCPF.getText().trim().length() > 10 && new String(txfSenha.getPassword()).trim().length() > 3) {
            Funcionario f = new Funcionario();
            f.setCpf(txfCPF.getText().trim());
            f.setSenha(new String(txfSenha.getPassword()).trim());
            f.setNumero_ctps(txfCTPS.getText().trim());
            f.setNumero_pis(txfPIS.getText().trim());
            f.setCargo(Cargo.getCargo(cbxcargo.getSelectedItem().toString()));
            return f;
        }
        return null;
    }

    public void setFuncionarioFormulario(Funcionario f) {
        if (f == null) {///se o parâmetro estiver nulo, limpa o form
            txfCPF.setText("");
            ///txfSenha.setText("");
            txfCTPS.setText("");
            txfPIS.setText("");
            cbxcargo.setSelectedIndex(0);
            funcionario = null;
        } else {
            funcionario = f;
            txfCPF.setEditable(false);///para não editar o cpf
            txfCPF.setText(funcionario.getCpf());
            ///txfSenha.setText(funcionario.getSenha());
            txfCTPS.setText(funcionario.getNumero_ctps());
            txfPIS.setText(funcionario.getNumero_pis());
            cbxcargo.getModel().setSelectedItem(funcionario.getCargo());
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        ///throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            if(arg0.getActionCommand().equals(btnGravar.getActionCommand())){          
            Funcionario f = getFuncionariobyFormulario();//recupera os dados do formulÃ¡rio       
            if(f != null){
                try {                  
                    pnlAFuncionario.getControle().getConexaoJDBC().persist(f);
                    JOptionPane.showMessageDialog(this, "Funcionario armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);                
                    pnlAFuncionario.showTela("tela_funcionario_listagem");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar Funcionario! : "+ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }else{
                JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }          
        }else if(arg0.getActionCommand().equals(btnCancelar.getActionCommand())){
                pnlAFuncionario.showTela("tela_funcionario_listagem");   
        }
    }

    private void initComponents() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        tbpAbas = new JTabbedPane();
        this.add(tbpAbas, BorderLayout.CENTER);
        pnlDadosCadastrais = new JPanel();
        gridBagLayoutDadosCadastrais = new GridBagLayout();
        pnlDadosCadastrais.setLayout(gridBagLayoutDadosCadastrais);

        lblCPF = new JLabel("CPF: ");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblCPF, posicionador);//o add adiciona o rotulo no painel  

        txfCPF = new JTextField(12);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(txfCPF, posicionador);//o add adiciona o rotulo no painel  

        ///lblSenha = new JLabel("Senha:");
        ///posicionador = new GridBagConstraints();
        ///posicionador.gridy = 1;//policao da linha (vertical)
        ///posicionador.gridx = 0;// posição da coluna (horizontal)
        ///pnlDadosCadastrais.add(lblSenha, posicionador);//o add adiciona o rotulo no painel  

        ///txfSenha = new JPasswordField(12);
        ///posicionador = new GridBagConstraints();
        ///posicionador.gridy = 1;//policao da linha (vertical)
        ///posicionador.gridx = 1;// posição da coluna (horizontal)
        ///posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        ///pnlDadosCadastrais.add(txfSenha, posicionador);//o add adiciona o rotulo no painel  

        lblCTPS = new JLabel("Número CTPS: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblCTPS, posicionador);//o add adiciona o rotulo no painel  

        txfCTPS = new JTextField(12);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(txfCTPS, posicionador);//o add adiciona o rotulo no painel  

        lblPIS = new JLabel("Número PIS:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(lblPIS, posicionador);//o add adiciona o rotulo no painel  

        txfPIS = new JTextField(12);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfPIS, posicionador);

        lblcargo = new JTextField(12);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(lblcargo, posicionador);//o add adiciona o rotulo no painel     
        tbpAbas.addTab("Dados Cadastrais", pnlDadosCadastrais);

        cbxcargo = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(cbxcargo, posicionador);

        ///pnlDadosCompras = new JPanel();
        ///tbpAbas.addTab("Compras", pnlDadosCompras);
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true);///acessibilidade    
        btnGravar.setToolTipText("btnGravarFuncionario");///acessibilidade
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_funcionario");
        pnlSul.add(btnGravar);
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true);///acessibilidade    
        btnCancelar.setToolTipText("btnCancelarFuncionario");///acessibilidade
        btnCancelar.setActionCommand("botao_cancelar_formulario_funcionario");
        pnlSul.add(btnCancelar);
        this.add(pnlSul, BorderLayout.SOUTH);
        format = new SimpleDateFormat("dd/MM/yyyy");
    }
}
