package TP5;

import javax.swing.*;
import java.awt.*;
import java.lang.Math;

public class TP5 extends JFrame{  
    public static final int CHAINAGE = 1;
    public static final int DOUBLECHOIX = 2;
    public static final int LINEAIRE = 3;
    public static final int QUADRATIQUE = 4;
    public static String awnserString = "";

    public TP5() {
        setTitle("Schema de table de hachage");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private static boolean verify(Integer boxNumber, Integer ballNumber){
        if(boxNumber<=2*ballNumber && boxNumber>=ballNumber){
            return true;
        }
        else{
            return false;
        }
    }

    private static int findMax(int[] boxes) {
        int max = 0;
        for(int i=0; i<boxes.length; i++){
            max = Math.max(boxes[i], max);
        }
        return max;
    }

    private static int[] Chainage(Integer boxNumber, Integer ballNumber) {
        int[] boxes = new int[boxNumber];
        for(int i=0; i<ballNumber; i++){
            boxes[(int) Math.floor(Math.random()*boxNumber)] += 1;
        }
        awnserString = "Max dans une meme case = "+String.valueOf(findMax(boxes));
        return boxes;
    }

    private static int[] DoubleChoix(Integer boxNumber, Integer ballNumber) {
        int[] boxes = new int[boxNumber];
        int[] randomNumber = new int[2];
        for(int i=0; i<ballNumber; i++){
            randomNumber[0] = (int) Math.floor(Math.random()*boxNumber);
            randomNumber[1] = (int) Math.floor(Math.random()*boxNumber);
            if(boxes[randomNumber[0]]<boxes[randomNumber[1]]){
                boxes[randomNumber[0]] += 1;
            }
            else{
                boxes[randomNumber[1]] += 1;
            }
        }
        awnserString = "Max dans une meme case = "+String.valueOf(findMax(boxes));
        return boxes;
    }

    private static int[] Lineaire(Integer boxNumber, Integer ballNumber) {
        int[] boxes = new int[boxNumber];
        int randomNumber = (int) Math.floor(Math.random()*boxNumber);
        int cpt = 0;
        int max = 0;
        for(int i=0; i<ballNumber; i++){
            cpt=0;
            while(boxes[randomNumber] != 0){
                randomNumber++;
                cpt ++;
                if(randomNumber>=boxNumber){
                    randomNumber = 0;
                }
            }
            max = Math.max(cpt, max);
            boxes[randomNumber] = 1;
            randomNumber = (int) Math.floor(Math.random()*boxNumber);
        }
        awnserString = "Max de cases parcourues = "+String.valueOf(max);
        return boxes;
    }

    private static int[] Quadratique(Integer boxNumber, Integer ballNumber) {
        int[] boxes = new int[boxNumber];
        int randomNumber = (int) Math.floor(Math.random()*boxNumber);
        int cpt = 0;
        int max = 0;
        for(int i=0; i<ballNumber; i++){
            cpt=0;
            while(boxes[randomNumber] != 0){
                cpt ++;
                randomNumber = (randomNumber+(cpt*cpt))%boxNumber;
            }
            max = Math.max(cpt, max);
            boxes[randomNumber] = 1;
            randomNumber = (int) Math.floor(Math.random()*boxNumber);
        }
        awnserString = "Max de cases parcourues = "+String.valueOf(max);
        return boxes;
    }

    public static JPanel CreateGrid(Integer boxNumber, Integer ballNumber, int method){
        int[] boxes = new int[boxNumber];
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel awnserPanel = new JPanel();
        JPanel gridPanel = new JPanel(new GridLayout(0, 17));
        JLabel awnser = new JLabel();
        String text;
        if(verify(boxNumber, ballNumber) == true){
            switch(method){
                case 1 : //Chainage
                boxes = Chainage(boxNumber, ballNumber);
                break;
                case 2 : //Double Choix
                boxes = DoubleChoix(boxNumber, ballNumber);
                break;
                case 3 : //Lineaire
                boxes = Lineaire(boxNumber, ballNumber);
                break;
                case 4 : //Quadratique
                boxes = Quadratique(boxNumber, ballNumber);
                break;
            }
            awnser = new JLabel(awnserString);
            awnserPanel.add(awnser);
            for(int i=0; i<boxNumber; i++){
                text="";
                if(boxes[i] > 6){
                    text = String.valueOf(boxes[i]);
                }
                else{
                    for(int j=0; j<boxes[i]; j++){
                        text+=".";
                    }
                }            
                JTextField box = new JTextField(text);
                box.setPreferredSize(new Dimension(55,55));
                box.setFont(new Font("Serif", Font.BOLD, 30));
                gridPanel.add(box);
            }
        }
        else{
            JLabel erreur = new JLabel("Veuillez rentrer un nombre de billes compris entre (nombre de boites)/2 et (nombre de boites)");
            erreur.setPreferredSize(new Dimension(700,55));
            erreur.setFont(new Font("Serif", Font.BOLD, 15));
            erreur.setForeground(Color.RED);
            awnserPanel.add(erreur);
        }
        mainPanel.add(awnserPanel);
        mainPanel.add(gridPanel);
        return mainPanel;
    }

    public static void main(String[] args) {
    //Définition des panels swing
        TP5 window = new TP5();
        JPanel mainPanel = new JPanel(); //Panel principal
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel firstPanel = new JPanel(); //Prmeier panel du mainPanel
        JPanel textPanel = new JPanel(); //Panel de text avec saisie du nombre de boites et de balles
        JPanel buttonPanel = new JPanel(new GridLayout(0,4)); //Panel de boutons pour le choix de la méthode de hachage
        JPanel secondPanel = new JPanel(); //Deuxieme panel du mainPanel
    //Définition des objets de textPanel
        JLabel boxNumberLabel = new JLabel("Nombre de boites : ");
        JTextField boxNumberText = new JTextField();
        boxNumberText.setPreferredSize( new Dimension(200, 24));
        JLabel ballNumberLabel = new JLabel("Nombre de balles : ");
        JTextField ballNumberText = new JTextField();
        ballNumberText.setPreferredSize( new Dimension(200, 24));
    //Définition des boutons de buttonPanel
        JButton chainageButton = new JButton("Chainage");
        JButton doublechoixButton = new JButton("Double choix");
        JButton lineaireButton = new JButton("Addressage ouvert lineaire");
        JButton quadratiqueButton = new JButton("Adressage ouvert quadratique");
    //Affectation des fonctions aux boutons
        chainageButton.addActionListener(
            e -> {
                secondPanel.removeAll();
                secondPanel.add(CreateGrid(Integer.valueOf(boxNumberText.getText()), Integer.valueOf(ballNumberText.getText()), CHAINAGE));
                window.setVisible(true);
            });
        doublechoixButton.addActionListener(
            e -> {
                secondPanel.removeAll();
                secondPanel.add(CreateGrid(Integer.valueOf(boxNumberText.getText()), Integer.valueOf(ballNumberText.getText()), DOUBLECHOIX));
                window.setVisible(true);
            });
        lineaireButton.addActionListener(
            e -> {
                secondPanel.removeAll();
                secondPanel.add(CreateGrid(Integer.valueOf(boxNumberText.getText()), Integer.valueOf(ballNumberText.getText()), LINEAIRE));
                window.setVisible(true);
            });
        quadratiqueButton.addActionListener(
            e -> {
                secondPanel.removeAll();
                secondPanel.add(CreateGrid(Integer.valueOf(boxNumberText.getText()), Integer.valueOf(ballNumberText.getText()), QUADRATIQUE));
                window.setVisible(true);
            });
    //Remplissage de textPanel
        textPanel.add(boxNumberLabel);
        textPanel.add(boxNumberText);
        textPanel.add(ballNumberLabel);
        textPanel.add(ballNumberText);
    //Remplissage de buttonPanel
        buttonPanel.add(chainageButton);
        buttonPanel.add(doublechoixButton);
        buttonPanel.add(lineaireButton);
        buttonPanel.add(quadratiqueButton);
    //Remplissages de firstPanel
        firstPanel.add(textPanel);
        firstPanel.add(buttonPanel);
    //Remplissage de mainPanel
        mainPanel.add(firstPanel);
        mainPanel.add(secondPanel);
    //Remplissage de la fenêtre
        window.add(mainPanel);
        window.setVisible(true);
    }
}