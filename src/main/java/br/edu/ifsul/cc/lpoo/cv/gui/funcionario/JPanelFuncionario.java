/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.gui.funcionario;

import br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade.JPanelAFuncionarioFormulario;
import br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade.JPanelAFuncionarioListagem;
import br.edu.ifsul.cc.lpoo.cv1.Controle;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author José Henrique PG
 */
public class JPanelFuncionario extends JPanel {

    private CardLayout cardLayout;
    private Controle controle;
    private JPanelAFuncionarioListagem listf;
    ///private JPanelAFuncionarioFormulario formf;

    public JPanelFuncionario(Controle controle) {

        this.controle = controle;
        initComponents();
    }

    private void initComponents() {

        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
         listf = new JPanelFuncionarioListagem(this, controle);
        ///formf = new JPanelAFuncionarioFormulario(this, controle);      
        this.add(listf, "tela_jogador_listagem");
        ///this.add(getFormulario(), "tela_jogador_formulario");

    }

    public void showTela(String nomeTela) {
         if(nomeTela.equals("tela_funcionario_listagem")){           
            listf.populaTable();         
        }
        cardLayout.show(this, nomeTela);
    }

    /**
     * @return the controle
     */
    public Controle getControle() {
        return controle;
    }
}
