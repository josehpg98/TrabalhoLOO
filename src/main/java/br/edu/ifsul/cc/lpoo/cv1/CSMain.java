package br.edu.ifsul.cc.lpoo.cv1;


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
public class CSMain {
    private Controle controle;
    //construtor.
    //"caminho feliz" : passo 2
    public CSMain() {
        //primeiramente - estabelecer uma conexao com o banco de dados.
        try {
            controle = new Controle();//cria a instancia e atribui para o atributo controle.
            //"caminho feliz" : passo 3
            if (controle.conectarBD()) {
                //"caminho feliz" : passo 4
                controle.initComponents();

            } else {
                JOptionPane.showMessageDialog(null, "Não conectou no Banco de Dados!", "Banco de Dados", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar conectar no Banco de Dados: "+ex.getLocalizedMessage(), "Banco de Dados", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        //em caso de sucesso - iniciar a interface gráfica.
    }

    public static void main(String[] args) {

        //"caminho feliz" : passo 1
        new CSMain();

    }
}
