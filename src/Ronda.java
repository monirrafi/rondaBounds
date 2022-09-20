import java.awt.*;
import java.util.*;


import javax.swing.*;
import javax.swing.Timer;

import java.awt.event.*;

public class Ronda extends JFrame implements actionImage,Runnable{
/*======================================== Declaration =============================================== */    
static Paquet table;
static Paquet joeur;
static Paquet ordi;
static Paquet paquet;
static JButton btn1 = new JButton(); 
static JButton btn2 = new JButton();
static JButton btn3 = new JButton();
static JButton btnPaq = new JButton(new ImageIcon("src\\Ronda\\dos.png")); 

static JButton btnQuitter = new JButton("Quitter"); 
static JButton btnSimple = new JButton("Simple");
static JButton btnComplet = new JButton("Complet"); 
static JButton btnOK = new JButton("Ok"); 
static JLabel lblMessage;
static JLabel lblPointage1;
static JLabel lblPointage2;

static JLabel label1 = new JLabel();
static JLabel label2 = new JLabel();
static JLabel label3 = new JLabel();
static char gagnatDernierTour=' ';

static ArrayList<JLabel> listLabelJoeur = new ArrayList<>();
static Ronda ronda;
static int sizeJoeur;
static int pointsPartiJoeur=0;
static int pointsPartiOrdi=0;
static int pointsJeuJoeur=0;
static int pointsJeuOrdi=0;
static boolean complet = false;
static Paquet suiteJoeur = new Paquet();
static Paquet suiteOrdi = new Paquet();
final static int W = 1500;
final static int H = 800;
static Thread th;





/*======================================== constructeur =============================================== */    

public Ronda(){}
public Ronda(Paquet ptable, Paquet pjoeur, Paquet pordi) {
    table=ptable;
    joeur=pjoeur;
    ordi=pordi;
    btnPaq.setBounds(W-130,H/2,125,150);
    btnQuitter.setBounds(W-160,0,150,20);
    btnSimple.setBounds(W-315,0,150,20);
    btnComplet.setBounds(W-470,0,150,20);
    add(btnQuitter);
    add(btnSimple);
    add(btnComplet);
    add(btnPaq);

    lancer();
    clique();
}




public void lancer(){
    setTitle("Ronda");
    setPreferredSize(new Dimension(W, H));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null);
    getContentPane().setBackground(Color.DARK_GRAY);
    pack();
    setVisible(true);

}

@Override
public void clique() {
    btn1.addActionListener(this::actionBtn);
    btn2.addActionListener(this::actionBtn);
    btn3.addActionListener(this::actionBtn);
    btnQuitter.addActionListener(this::actionBtn);
    btnSimple.addActionListener(this::actionBtn);
    btnComplet.addActionListener(this::actionBtn);
    btnOK.addActionListener(this::actionBtn);
    btnPaq.addActionListener(this::actionBtn);

}
/*======================================== Fonctions Generales =============================================== */    

/*======================================== Fonctions Generales =============================================== */    
public static void chargerPaquet() {

    ArrayList<Carte> listPaquet = new ArrayList<Carte>();
    String suit_names[] = {"coupe", "épée", "baton", "denier"};
    int rank_names[] = {1, 2, 3, 4, 5, 6, 7, 10, 11, 12};
    for(String s:suit_names){
        for(int n:rank_names){
            listPaquet.add(new Carte(s, n));

        }
    }
    Collections.shuffle(listPaquet);
    paquet = new Paquet(listPaquet); 
}

public static ArrayList<ImageIcon> chargerListeImage(ArrayList<Carte> listPaquet,String repretoire){
    ArrayList<ImageIcon> listImage = new ArrayList<ImageIcon>(); 
    for(Carte card:listPaquet){
       listImage.add(new ImageIcon(repretoire + "\\"+card.toString().substring(0,card.toString().length()-2)+".png"));
       //listImage.add(new ImageIcon(repretoire + "\\baton1.png"));

    }
    return listImage;
}

public static void distribuer(Paquet paquet,Paquet table,Paquet joeur,Paquet ordi){
    //Random rand = new Random();
    //int int_random = rand.nextInt(150); 
    //int max = H-400;
    int min =150;
    //int int_random = (int)Math.floor(Math.random()*(max-min+1)+min);
    if (paquet.getPaquet().size() !=0){
        if(paquet.getPaquet().size()==40){
            paquet.moveCardsNoDouble(table,' ',4);
        }
        for(Carte cart:table.getPaquet()){
            cart.setLabel(' ');
        }     
        paquet.moveCards(joeur,'j',3);
        paquet.moveCards(ordi,'o',3);

        //donner les points ronda et tiringa entre joeur et ordi
        joeur.pointageRandaTringa(ordi);
    }
    //charger les images
        ArrayList<ImageIcon> imageTable = chargerListeImage(table.getPaquet(),"src\\Ronda");
        ArrayList<ImageIcon> imageJoeur = chargerListeImage(joeur.getPaquet(),"src\\Ronda");
        ArrayList<ImageIcon> imageOrdi = chargerListeImage(ordi.getPaquet(),"src\\Ronda");
        btn1.setBounds(0,H-200,125,150);
        btn2.setBounds(130,H-200,125,150);
        btn3.setBounds(260,H-200,125,150);
        btn1.setIcon(imageJoeur.get(0));
        btn2.setIcon(imageJoeur.get(1));
        btn3.setIcon(imageJoeur.get(2));
        ronda.add(btn1);
        ronda.add(btn2);
        ronda.add(btn3);
        
        for (int i=0;i<imageOrdi.size();i++){
            JLabel label = new JLabel();
            label.setIcon(imageOrdi.get(i));
           //label.setIcon(new ImageIcon("src\\Ronda\\dos.png"));
            //label.setIcon(new ImageIcon("Ronda\\baton1.png"));
           label.setBounds(i*130,0,125,150);
           ronda.add(label);
        
            
        }
        for (int i=0;i<imageTable.size();i++){
            JLabel label = new JLabel();
            label.setIcon(imageTable.get(i));
            //label.setBounds((i+1)*int_random,H/4+int_random,125,150);
            label.setBounds((i+1)*130,H/4+min,125,150);
            ronda.add(label);
        
            
        }
    

}
public static void tour(Carte carte,Paquet table,Paquet joeur) {
    //Paquet suite = sequence(carte,table);
    suiteJoeur = sequence(carte,table);
    
    
    // si il ya une suite des cartes dans la table 
    if(suiteJoeur.getPaquet().size() != 0){
        table.getPaquet().remove(carte);
        for(Carte cart:suiteJoeur.getPaquet()){
            table.getPaquet().remove(cart);
        }
    
        // cas de missa
        if(table.getPaquet().size() ==0){
            joeur.ajoutPoints(1);
        }
        // cas de essti
        if(suiteJoeur.getPaquet().get(0).getLabel() == 'o'){
            joeur.ajoutPoints(1);
        }
        for(Carte cart:suiteJoeur.getPaquet()){
            joeur.getGainCartes().add(cart);
        }
        //joeur.getGainCartes().add(carte);
        gagnatDernierTour = 'j';
        for(Carte cart:table.getPaquet()){
            cart.setLabel(' ');
        }     
    
    }else{
        table.getPaquet().add(carte);
    }
    joeur.getPaquet().remove(carte);
    
    }
    public static int[] indexMeilleurCarte() {
        int totalPoint=0,totalCarte=0,plusPoints=0,plusCarte=2;
        //int ind =-1;
        int[] retourneIndex = new int[3];
        retourneIndex[0]= -1;
        retourneIndex[1]=totalPoint;
        retourneIndex[2]=totalCarte;
        Paquet gainTable;
        for(int i =0;i<ordi.getPaquet().size();i++){
            gainTable = sequence(ordi.getPaquet().get(i), table);
            if(gainTable.getPaquet().size() != 0){
                totalCarte = gainTable.getPaquet().size();
                // cas de missa
                if(table.getPaquet().size() ==0){
                    totalPoint++;
                }
                // cas de essti
                if(gainTable.getPaquet().get(0).getLabel() == 'j'){
                    totalPoint++;
                }
                if(totalPoint>=plusPoints && totalCarte >=plusCarte){
                    plusPoints=totalPoint;
                    plusCarte = totalCarte;
                    retourneIndex[0]=i;
                    retourneIndex[1]=totalPoint;
                    retourneIndex[2]=totalCarte;
                    
                }else if(totalPoint == 1 &&  totalCarte>=plusCarte ){
                    plusCarte = totalCarte;
                    retourneIndex[0]=i;
                    retourneIndex[1]=totalPoint;
                    retourneIndex[2]=totalCarte;
    
                }else if(totalPoint>= plusPoints ){
                    plusPoints = totalPoint;
                    retourneIndex[0]=i;
                    retourneIndex[1]=totalPoint;
                    retourneIndex[2]=totalCarte;
    
                }else if(totalCarte>=5 ){
                    plusCarte = totalCarte;
                    retourneIndex[0]=i;
                    retourneIndex[1]=totalPoint;
                    retourneIndex[2]=totalCarte;
    
                }else if(totalPoint == 1 && totalCarte ==2){
                    retourneIndex[0]=i;
                    retourneIndex[1]=totalPoint;
                    retourneIndex[2]=totalCarte;
    
                }else if(totalCarte>= plusCarte ){
                    plusCarte = totalCarte;
                    retourneIndex[0]=i;
                    retourneIndex[1]=totalPoint;
                    retourneIndex[2]=totalCarte;
    
                }else{
                    retourneIndex[0]=i;
                    retourneIndex[1]=totalPoint;
                    retourneIndex[2]=totalCarte;
                }
            }  
        }
        return retourneIndex;
    }
    
    public static void tourOrdi(Paquet ordi,Paquet table) {
        int[] listeMeileur = indexMeilleurCarte();
        if(listeMeileur[0] != -1){
            Carte carte = ordi.getPaquet().get(listeMeileur[0]);
            suiteOrdi = sequence(carte, table);
            table.getPaquet().remove(carte);
            for(Carte cart:suiteOrdi.getPaquet()){
                table.getPaquet().remove(cart);
            }
                
    
            ordi.ajoutPoints(listeMeileur[1]);
            for(Carte cart:suiteOrdi.getPaquet()){
                ordi.getGainCartes().add(cart);
            }
            ordi.getPaquet().remove(listeMeileur[0]);
    
            gagnatDernierTour = 'o';
            for(Carte cart:table.getPaquet()){
                cart.setLabel(' ');
            }     
    
        }else{
            table.getPaquet().add(ordi.getPaquet().get(0));
            ordi.getPaquet().remove(ordi.getPaquet().get(0));
    
        }
    }
    public static Paquet sequence(Carte card,Paquet table) {

        ArrayList<Carte> listSequenece = new ArrayList<Carte>();
        // verifier l'existance de la carte dans la table 
        // retourne son index
        int existe = table.trouveIndexRank(card);
        if(existe != -1){
        // sortir la carte semblable de la table
            Carte current = table.getPaquet().get(existe);
            //ordonner la table
            Collections.sort(table.getPaquet());    
            int indCurrent= table.getPaquet().indexOf(current);
            //table.getPaquet().get(indCurrent).setLabel(label);
            listSequenece.add(table.getPaquet().get(indCurrent));
            int suivant=0;
            for( int i=indCurrent+1;i<table.getPaquet().size();i++){
                    if(current.getRank()==7){
                        suivant = current.getRank()+3;
    
                    }else {
                        suivant = current.getRank()+1;
    
                    }
                    if(suivant==table.getPaquet().get(i).getRank()){
                        listSequenece.add(table.getPaquet().get(i));
                        current = table.getPaquet().get(i);
                    
                    }else{
                        break;
                    }
            }
            listSequenece.add(card);
    /*        
            table.getPaquet().remove(indCurrent);
            for(Carte cart:listSequenece){
                table.getPaquet().remove(cart);
            }*/
        }
        Paquet paq = new Paquet(listSequenece);
            return paq;     
    }
public void actionBtn(ActionEvent e) {
    Carte carte = new Carte();
    //setVisible(false);
    if(e.getSource() == btnPaq)
    {
        distribuer(paquet, table, joeur, ordi);
    }else if(e.getSource() == btnQuitter)
    {
        System.exit(0);
    }else if(e.getSource() == btn1)
    {
        carte = joeur.getPaquet().get(0);
        btn1.setBounds(50,H/3,125,150);
        tour(carte, table, joeur);
        th.start();
        btn1.setVisible(false);
    } 
    tourOrdi(ordi, table);
    //setVisible(true);
    repaint();

}



/*======================================== Main =============================================== */    


/*======================================== Main =============================================== */    
public static void main(String[] args) {
    table = new Paquet();
    joeur=new Paquet();
    ordi = new Paquet();
    paquet = new Paquet();

    chargerPaquet();
    //distribuer(paquet, table, joeur, ordi);

    //ronda = new JeuRonda("Jeu Ronda","Choisissez votre jeu ", "cliquer simple ou complet","");
    ronda = new Ronda(joeur,ordi,table);
    ronda.clique();
}
@Override
public void run() {
    
    try {
        th.sleep(2);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
}



}
