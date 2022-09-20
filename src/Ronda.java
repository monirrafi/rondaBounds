import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class Ronda extends JFrame implements actionImage{
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
public void actionBtn(ActionEvent e){
    setVisible(false);
    if(e.getSource() == btnPaq)
    {
        distribuer(paquet, table, joeur, ordi);
    }else if(e.getSource() == btnQuitter)
    {
        System.exit(0);
    }
    setVisible(true);

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



}
