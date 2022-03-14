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
import java.awt.Color;
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
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

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

    private GridBagLayout gridBagLayoutDadosCadastrais;

    private JLabel lblNome;
    private JTextField txfNome;

    private JLabel lblSenha;
    private JPasswordField txfSenha;

    private JLabel lblCpf;
    private JTextField txfCpf;

    private JLabel lblCep;
    private JTextField txfCep;

    private JLabel lblComplemento;
    private JTextField txfComplemento;

    private JLabel lblDataCadastro;
    private JTextField txfDataCadastro;

    private JLabel lblDataNascimento;
    private JTextField txfDataNascimento;

    private JLabel lblEmail;
    private JTextField txfEmail;

    private JLabel lblEndereco;
    private JTextField txfEndereco;

    private JLabel lblNumeroCelular;
    private JTextField txfNumeroCelular;

    private JLabel lblRg;
    private JTextField txfRg;

    private JLabel lblCargo;
    private JComboBox cbxCargo;

    private JLabel lblCtps;
    private JTextField txfCtps;

    private JLabel lblPis;
    private JTextField txfPis;

    private Funcionario funcionario;
    private SimpleDateFormat sdfformat;

    private JPanel pnlSul;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public JPanelAFuncionarioFormulario(JPanelAFuncionario pnlAFuncionario, Controle controle) {
        this.pnlAFuncionario = pnlAFuncionario;
        this.controle = controle;
        this.sdfformat = new SimpleDateFormat("dd/MM/yyyy");

        initComponents();
    }

    public void populaComboCargo() {
        cbxCargo.removeAllItems();
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxCargo.getModel();
        model.addElement("Selecione");
        try {
            List<Cargo> listCargos = Arrays.asList(Cargo.values());
            listCargos.forEach(c -> {
                model.addElement(c);
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar os Cargos:" + e.getLocalizedMessage(), "Cargos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public Funcionario getFuncionarioByFormulario() throws ParseException {
        if (txfNome.getText().trim().length() > 4) {
            if (new String(txfSenha.getPassword()).trim().length() > 4) {
                if (txfCpf.getText().trim().length() == 11) {
                    if (txfDataNascimento.getText().trim().length() == 10) {
                        if (txfEmail.getText().trim().length() > 5) {
                            if (txfEndereco.getText().trim().length() > 8) {
                                if (txfNumeroCelular.getText().trim().length() >= 10) {
                                    if (cbxCargo.getSelectedIndex() != 0) {    
                                        if (txfCtps.getText().trim().length() == 12) {
                                            if (txfPis.getText().trim().length() > 10) {
                                                Funcionario f = new Funcionario();
                                                f.setNome(txfNome.getText().trim());
                                                f.setSenha(new String(txfSenha.getPassword()).trim());
                                                f.setEmail(txfEmail.getText().trim());
                                                f.setEndereco(txfEndereco.getText().trim());
                                                f.setNumero_celular(txfNumeroCelular.getText().trim());
                                                f.setCargo((Cargo) cbxCargo.getSelectedItem());
                                                f.setNumero_ctps(txfCtps.getText().trim());
                                                f.setNumero_pis(txfPis.getText().trim());
                                                f.setCep(txfCep.getText().trim());
                                                f.setComplemento(txfComplemento.getText().trim());
                                                f.setRg(txfRg.getText().trim());
                                                f.setCpf(txfCpf.getText().trim());
                                                if (txfDataNascimento.getText().trim().length() > 1) {
                                                    Calendar dtNascimento = Calendar.getInstance();
                                                    dtNascimento.setTime(sdfformat.parse(txfDataNascimento.getText()));
                                                    f.setData_nascimento(dtNascimento);
                                                }
                                                if (funcionario != null) {
                                                    f.setData_cadastro(funcionario.getData_cadastro());
                                                }
                                                return f;
                                            } else {
                                                JOptionPane.showMessageDialog(this, " PIS é um campo obrigatório", "Funcionario", JOptionPane.ERROR_MESSAGE);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(this, " CTPS inválido! ", "Funcionario", JOptionPane.ERROR_MESSAGE);
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(this, " selecione algum Cargo!", "Funcionario", JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this, "Celular inválido!", "Funcionario", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Endereço é obrigatório!", "Funcionario", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "E-mail é obrigatório! ", "Funcionario", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Data de Nascimento inválida! ", "Funcionario", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "CPF inválido! ", "Funcionario", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Senha é obrigatório!.", "Funcionario", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório! .", "Funcionario", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void setFuncionarioFormulario(Funcionario f) {
        if (f == null) {
            txfNome.setText("");
            txfSenha.setText("");
            txfCpf.setText("");
            txfCep.setText("");
            txfComplemento.setText("");
            txfDataNascimento.setText("");
            txfEmail.setText("");
            txfEndereco.setText("");
            txfNumeroCelular.setText("");
            txfRg.setText("");
            cbxCargo.setSelectedIndex(0);
            txfCtps.setText("");
            txfPis.setText("");
            txfDataCadastro.setText(sdfformat.format(Calendar.getInstance().getTime()));
        } else {
            funcionario = f;
            txfNome.setEditable(false);
            txfNome.setText(funcionario.getNome());
            txfSenha.setText(funcionario.getSenha());
            txfCpf.setText(funcionario.getCpf());
            txfCpf.setEditable(false);
            txfEmail.setText(funcionario.getEmail());
            txfEndereco.setText(funcionario.getEndereco());
            txfNumeroCelular.setText(funcionario.getNumero_celular());
            cbxCargo.getModel().setSelectedItem(funcionario.getCargo());
            txfCtps.setText(funcionario.getNumero_ctps());
            txfPis.setText(funcionario.getNumero_pis());
            txfDataCadastro.setEditable(false);
            if (f.getData_nascimento() != null) {
                txfDataNascimento.setText(sdfformat.format(funcionario.getData_nascimento().getTime()));
            }
            if (f.getData_cadastro() != null) {
                txfDataCadastro.setText(sdfformat.format(funcionario.getData_cadastro().getTime()));
            }
            if (f.getCep() != null) {
                txfCep.setText(funcionario.getCep());
            }
            if (f.getComplemento() != null) {
                txfComplemento.setText(funcionario.getComplemento());
            }
            if (f.getRg() != null) {
                txfRg.setText(funcionario.getRg());
            }
        }
    }

    private void initComponents() {
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);

        tbpAbas = new JTabbedPane();
        this.add(tbpAbas, borderLayout.CENTER);

        pnlDadosCadastrais = new JPanel();
        gridBagLayoutDadosCadastrais = new GridBagLayout();
        pnlDadosCadastrais.setLayout(gridBagLayoutDadosCadastrais);

        lblNome = new JLabel("Nome:");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblNome, posicionador);

        txfNome = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfNome, posicionador);

        lblSenha = new JLabel("Senha:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblSenha, posicionador);

        txfSenha = new JPasswordField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfSenha, posicionador);

        lblCpf = new JLabel("CPF:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblCpf, posicionador);

        txfCpf = new JTextField(14);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfCpf, posicionador);

        lblCep = new JLabel("CEP:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblCep, posicionador);

        txfCep = new JTextField(9);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfCep, posicionador);

        lblComplemento = new JLabel("Complemento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblComplemento, posicionador);

        txfComplemento = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfComplemento, posicionador);

        lblDataNascimento = new JLabel("Data de Nascimento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblDataNascimento, posicionador);

        txfDataNascimento = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfDataNascimento, posicionador);

        lblDataCadastro = new JLabel("Data de Cadastro:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblDataCadastro, posicionador);

        txfDataCadastro = new JTextField(10);
        txfDataCadastro.setEditable(false);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfDataCadastro, posicionador);

        lblEmail = new JLabel("E-mail:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblEmail, posicionador);

        txfEmail = new JTextField(40);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfEmail, posicionador);

        lblEndereco = new JLabel("Endereço:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblEndereco, posicionador);

        txfEndereco = new JTextField(40);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfEndereco, posicionador);

        lblNumeroCelular = new JLabel("Número de Celular:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblNumeroCelular, posicionador);

        txfNumeroCelular = new JTextField(40);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfNumeroCelular, posicionador);

        lblRg = new JLabel("RG:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblRg, posicionador);

        txfRg = new JTextField(12);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfRg, posicionador);

        lblCargo = new JLabel("Cargo:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblCargo, posicionador);

        cbxCargo = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(cbxCargo, posicionador);

        lblCtps = new JLabel("Número CTPS:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 12;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblCtps, posicionador);

        txfCtps = new JTextField(18);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 12;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfCtps, posicionador);

        lblPis = new JLabel("Número PIS:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 13;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblPis, posicionador);

        txfPis = new JTextField(40);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 13;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfPis, posicionador);

        tbpAbas.addTab("Dados Cadastrais", pnlDadosCadastrais);

        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());

        btnSalvar = new JButton("Gravar");
        btnSalvar.addActionListener(this);
        btnSalvar.setFocusable(true);
        btnSalvar.setToolTipText("btnGravarFuncionario");
        btnSalvar.setMnemonic(KeyEvent.VK_G);
        btnSalvar.setActionCommand("botao_gravar_formulario_fucionario");

        pnlSul.add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true);
        btnCancelar.setToolTipText("btnCancelarFuncionario");
        btnCancelar.setActionCommand("botao_cancelar_formulario");
        pnlSul.add(btnCancelar);

        this.add(pnlSul, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals(btnSalvar.getActionCommand())) {
            Funcionario f;
            try {
                f = getFuncionarioByFormulario();

                if (f != null) {
                    try {
                        pnlAFuncionario.getControle().getConexaoJDBC().persist(f);
                        JOptionPane.showMessageDialog(this, "Funcionario salvo com sucesso!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                        pnlAFuncionario.showTela("tela_funcionario_listagem");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Erro ao salvar o Funcionário! : " + e.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o Funcionário! : " + e.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else if (arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {
            setFuncionarioFormulario(null);
            pnlAFuncionario.showTela("tela_funcionario_listagem");
        }
    }
}
