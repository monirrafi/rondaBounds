import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class JeuRonda extends JFrame implements actionImage{
/*======================================== Declaration =============================================== */    
static Paquet table;
static Paquet joeur;
static Paquet ordi;
static Paquet paquet;
static JButton btn1 = new JButton(); 
static JButton btn2 = new JButton();
static JButton btn3 = new JButton();
static JButton btnPaq = new JButton(new ImageIcon("Ronda\\dos.png")); 

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

static JPanel panePrincipal;
static ArrayList<JLabel> listLabelJoeur = new ArrayList<>();
static JeuRonda ronda;
static GridBagConstraints gc;
static int sizeJoeur;
static JPanel construirePane;
//static JPanel boutonChoix = new JPanel();
static int pointsPartiJoeur=0;
static int pointsPartiOrdi=0;
static int pointsJeuJoeur=0;
static int pointsJeuOrdi=0;
static boolean complet = false;
static Paquet suiteJoeur = new Paquet();
static Paquet suiteOrdi = new Paquet();




/*======================================== constructeur =============================================== */    

public JeuRonda(){}
public JeuRonda(String message, String pointage1, String pointage2, String type) {
    
    panePrincipal = new JPanel();
    if(type.equalsIgnoreCase("msg")){
        panePrincipal = paneMessage(message, pointage1,pointage2);
    }else{
        panePrincipal = paneMenu(message, pointage1,pointage2);

    }
    super.add(panePrincipal);
    super.setTitle("JeuRonda");
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    super.setPreferredSize(new Dimension(1500, 800));
    super.pack();
    super.setVisible(true);

}

public JeuRonda(Paquet ptable, Paquet pjoeur, Paquet pordi) {
    table=ptable;
    joeur=pjoeur;
    ordi=pordi;
    //ArrayList<ImageIcon> imageTable = chargerListeImage(table.getPaquet(),"Ronda");
    ArrayList<ImageIcon> imageJoeur = chargerListeImage(joeur.getPaquet(),"Ronda");
    //ArrayList<ImageIcon> imageOrdi = chargerListeImage(ordi.getPaquet(),"Ronda");
    setPreferredSize(new Dimension(1500, 800));
    int w=1500;//getWidth();
    int h=800;//getHeight();
    btnPaq.setBounds(w-130,h/2,125,150);
    btnQuitter.setBounds(w-160,0,150,20);
    btnSimple.setBounds(w-315,0,150,20);
    btnComplet.setBounds(w-470,0,150,20);
    btn1.setBounds(0,h-200,125,150);
    btn2.setBounds(130,h-200,125,150);
    btn3.setBounds(260,h-200,125,150);
    btn1.setIcon(imageJoeur.get(0));
    btn2.setIcon(imageJoeur.get(1));
    btn3.setIcon(imageJoeur.get(2));
    add(btnQuitter);
    add(btnSimple);
    add(btnComplet);
    add(btnPaq);
    add(btn1);
    add(btn2);
    add(btn3);
    lancer();

}
public void lancer(){
    setTitle("Ronda");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null);
    pack();
    
    setVisible(true);

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


/*======================================== Affichage =============================================== */    

/*======================================== Affichage =============================================== */    

public static JPanel construirePane(Paquet table, Paquet joeur, Paquet ordi) {

    //les listes des images selon les paquets        
    ArrayList<ImageIcon> imageTable = chargerListeImage(table.getPaquet(),"Ronda");
    ArrayList<ImageIcon> imageJoeur = chargerListeImage(joeur.getPaquet(),"Ronda");
    ArrayList<ImageIcon> imageOrdi = chargerListeImage(ordi.getPaquet(),"Ronda");
    ArrayList<ImageIcon> imageSuiteJoeur = chargerListeImage(suiteJoeur.getPaquet(),"Ronda_Petit");
    ArrayList<ImageIcon> imageSuiteOrdi = chargerListeImage(suiteOrdi.getPaquet(),"Ronda_Petit");

        
    //Declaration des panes
    JPanel pane = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    pane.setBackground(Color.lightGray);

    JPanel paneTable = new JPanel(new GridLayout(1,2));
    paneTable.setBackground(Color.DARK_GRAY);
    GridBagConstraints gc = new GridBagConstraints();

    JPanel paneLabelTable = new JPanel(new GridLayout(3,1));
    paneLabelTable.setBackground(Color.ORANGE);

    JPanel paneImageTable = new JPanel(new GridLayout(2,1));
    paneImageTable.setBackground(Color.DARK_GRAY);

    JPanel paneJoeur = new JPanel(new FlowLayout(FlowLayout.LEFT));
    paneJoeur.setBackground(Color.orange);

    JPanel paneOrdi = new JPanel(new FlowLayout(FlowLayout.LEFT));
    paneOrdi.setBackground(Color.orange);

    JPanel paneSuiteJoeur = new JPanel(new FlowLayout(FlowLayout.LEFT));
    paneSuiteJoeur.setBackground(Color.ORANGE);
    paneSuiteJoeur.add(new JLabel("    "));

    JPanel paneSuiteOrdi = new JPanel(new FlowLayout(FlowLayout.LEFT));
    paneSuiteOrdi.setBackground(Color.ORANGE);
    paneSuiteOrdi.add(new JLabel("    "));

    JPanel paneSuite = new JPanel(new GridLayout(4,1));
    paneSuite.setBackground(Color.ORANGE);

    JPanel paneImageJouer = new JPanel();
    paneImageJouer.setBackground(Color.orange);

    JPanel paneImageOrdi = new JPanel();
    paneImageOrdi.setBackground(Color.orange);

//===================================== Preparation du panel du joeur ==========================================

    //Preparation de la panel des infos
    JLabel labelTable1 =new JLabel("    Les cartes restantes " + paquet.getPaquet().size()+"    ");
    labelTable1.setFont(new Font(null,Font.BOLD,20));
    JLabel labelTable2 =new JLabel("    Les gains cartes du joeur " + joeur.sizeGain()+"    ");
    labelTable2.setFont(new Font(null,Font.BOLD,20));
    JLabel labelTable3 =new JLabel("    Les gains cartes du ordi " + ordi.sizeGain()+"    ");
    labelTable3.setFont(new Font(null,Font.BOLD,20));

    paneLabelTable.add(labelTable1);
    paneLabelTable.add(labelTable2);
    paneLabelTable.add(labelTable3);

    //label des points
    JLabel joeurLabel =  new JLabel("         Vos Points   " + joeur.getPoints()+"                                ");
    joeurLabel.setFont(new Font(null,Font.BOLD,40));

    Dimension dimension = new Dimension(125,150);
    btn1.setPreferredSize(dimension);
    btn2.setPreferredSize(dimension);
    btn3.setPreferredSize(dimension);
    
    if(imageJoeur.size()==2){
        if(btn1.isVisible()){
            btn1.setIcon(imageJoeur.get(0));
            btn2.setIcon(imageJoeur.get(1));
        }else{
            btn2.setIcon(imageJoeur.get(0));
            btn3.setIcon(imageJoeur.get(1));
        }
    }else if(imageJoeur.size()==1){
        btn1.setIcon(imageJoeur.get(0));
    }else if(imageJoeur.size()==3){
        btn1.setIcon(imageJoeur.get(0));
        btn2.setIcon(imageJoeur.get(1));
        btn3.setIcon(imageJoeur.get(2));
    }
    paneImageJouer.add(btn1);
    paneImageJouer.add(btn2);
    paneImageJouer.add(btn3);

    paneJoeur.add(paneImageJouer);
    paneJoeur.add(joeurLabel);
    paneJoeur.add(paneLabelTable);

//===================================== Preparation du panel de l'ordi ==========================================
    //label des points
    JLabel ordiLabel =  new JLabel("           Ordi Points   " + ordi.getPoints());
    ordiLabel.setFont(new Font(null,Font.BOLD,40));

    for (int i=0;i<imageOrdi.size();i++){
        JLabel label = new JLabel();
        //label.setIcon(imageOrdi.get(i));
       label.setIcon(new ImageIcon("Ronda\\dos.png"));
        //label.setIcon(new ImageIcon("Ronda\\baton1.png"));
       
        paneImageOrdi.add(label);
        
    }
    paneOrdi.add(paneImageOrdi);
    paneOrdi.add(ordiLabel);

//===================================== Preparation du panel du dernier carte gagnée ==========================================

    for (int i=0;i<imageSuiteJoeur.size();i++){
        JLabel label = new JLabel();
       label.setIcon(imageSuiteJoeur.get(i));
       paneSuiteJoeur.add(label);
        
    }
    for (int i=0;i<imageSuiteOrdi.size();i++){
        JLabel label = new JLabel();
       label.setIcon(imageSuiteOrdi.get(i));
       paneSuiteOrdi.add(label);
        
    }
    JLabel labelSuiteOrdi =new JLabel("    Dernières cartes gagnées par l'Ordi ");
    labelSuiteOrdi.setFont(new Font(null,Font.BOLD,16));
    paneSuite.add(labelSuiteOrdi);
    paneSuite.add(paneSuiteOrdi);
    JLabel labelSuiteJoeur =new JLabel("    Dernières cartes gagnées par le joeur ");
    labelSuiteJoeur.setFont(new Font(null,Font.BOLD,16));

    paneSuite.add(labelSuiteJoeur);
    paneSuite.add(paneSuiteJoeur);

//===================================== Preparation du panel de la table ==========================================

    for (int i=0;i<imageTable.size();i++){
        JLabel label = new JLabel();
       label.setIcon(imageTable.get(i));
        paneImageTable.add(label);
        
    }

    paneTable.add(paneImageTable);
    paneTable.add(paneSuite);
            
//===================================== Preparation du panel final ==========================================

    c.ipadx = 1500;      //make this component tall
    c.ipady = 60;      //make this component tall
    c.weightx = 0.0;
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth=1;
    pane.add(paneOrdi,c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipadx = 1500;      //make this component tall
    c.ipady = 100;      //make this component tall
    c.weightx = 0.0;
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth=2;
    pane.add(paneTable,c);
    //c.fill = GridBagConstraints.HORIZONTAL;
    c.ipadx = 1500;      //make this component tall
    c.ipady = 60;      //make this component tall
    c.weightx = 0.0;
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth=1;
    pane.add(paneJoeur,c);
    return pane;
}

//===================================== Panel Menu ================================================================
public static JPanel paneMenu(String message, String pointage1, String pointage2) {
    lblMessage = new JLabel(message);
    lblMessage.setFont(new Font(message,Font.PLAIN,64));
    lblPointage1 = new JLabel(pointage1);
    lblPointage1.setFont(new Font(message,Font.PLAIN,64));
    lblPointage2 = new JLabel(pointage2);
    lblPointage2.setFont(new Font(message,Font.PLAIN,64));

    JPanel paneMessage = new JPanel();
    paneMessage.add(lblMessage);

    JPanel panePointage1 = new JPanel();
    panePointage1.add(lblPointage1);
    JPanel panePointage2 = new JPanel();
    panePointage2.add(lblPointage2);

    JPanel paneButton = new JPanel();
    paneButton.add(btnQuitter);
    paneButton.add(btnSimple);
    paneButton.add(btnComplet);

    
    JPanel pane = new JPanel(new GridLayout(4,1));
    pane.add(paneMessage);
    pane.add(panePointage1);
    pane.add(panePointage2);
    pane.add(paneButton);

    return pane;

}
//===================================== Panel Message ================================================================
public static JPanel paneMessage(String message, String pointage1, String pointage2) {
    lblMessage = new JLabel(message);
    lblMessage.setFont(new Font(message,Font.PLAIN,64));
    lblPointage1 = new JLabel(pointage1);
    lblPointage1.setFont(new Font(message,Font.PLAIN,64));
    lblPointage2 = new JLabel(pointage2);
    lblPointage2.setFont(new Font(message,Font.PLAIN,64));

    JPanel paneMessage = new JPanel();
    paneMessage.add(lblMessage);

    JPanel panePointage1 = new JPanel();
    panePointage1.add(lblPointage1);
    JPanel panePointage2 = new JPanel();
    panePointage1.add(lblPointage2);

    JPanel paneButton = new JPanel();
    paneButton.add(btnOK);
    
    
    JPanel pane = new JPanel(new GridLayout(4,1));
    pane.add(paneMessage);
    pane.add(panePointage1);
    pane.add(panePointage2);
    pane.add(paneButton);

    return pane;

}
 
/*======================================== Fonctions =============================================== */    

/*======================================== Fonctions =============================================== */    
public static void distribuer(Paquet paquet,Paquet table,Paquet joeur,Paquet ordi){

    if (paquet.getPaquet().size() !=0){
        if(paquet.getPaquet().size()==40){
            paquet.moveCardsNoDouble(table,' ',4);
        }
        for(Carte cart:table.getPaquet()){
            cart.setLabel(' ');
        }     
        paquet.moveCards(joeur,'j',3);
        paquet.moveCards(ordi,'o',3);

        //donner les points ronda et tiringa
        joeur.pointageRandaTringa(ordi);
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
    
    /*
    boolean trouve = false;
    for(Carte co:ordi.getPaquet()){
        suiteOrdi = sequence(co, table);
        if(suiteOrdi.getPaquet().size() != 0){
            // cas de missa
            if(table.getPaquet().size() ==0){
                ordi.ajoutPoints(1);
            }
            // cas de essti
            if(suiteOrdi.getPaquet().get(0).getLabel() == 'j'){
                ordi.ajoutPoints(1);
            }
            for(Carte cart:suiteOrdi.getPaquet()){
                ordi.getGainCartes().add(cart);
            }
            //ordi.getGainCartes().add(co);
            ordi.getPaquet().remove(co);

            gagnatDernierTour = 'o';
            trouve = true;
            for(Carte cart:table.getPaquet()){
                cart.setLabel(' ');
            }     

            break;
        }  
    }
    if(trouve==false){
        table.getPaquet().add(ordi.getPaquet().get(0));
        ordi.getPaquet().remove(ordi.getPaquet().get(0));
    }*/
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

public static void simple() {
    table = new Paquet();
    joeur=new Paquet();
    ordi = new Paquet();
    paquet = new Paquet();
    chargerPaquet();
    distribuer(paquet, table, joeur, ordi);
    ronda = new JeuRonda(table, joeur, ordi);
    btn1.setVisible(true);
    btn2.setVisible(true);
    btn3.setVisible(true);

}
public void actionBtn(ActionEvent e){
        boolean entrerMenu = false;
        boolean nouveauJeu = false;
        Carte carte = new Carte();
        setVisible(false);
        //panePrincipal.setVisible(false);
        if(e.getSource() == btn1)
        {
            carte = joeur.getPaquet().get(0);
            btn1.setBounds(600,400,125,150);
            //btn1.setVisible(false);
        }
        else if(e.getSource() == btn2)
        {
            if(joeur.getPaquet().size()==3){
                carte = joeur.getPaquet().get(1);
            }
            else if(joeur.getPaquet().size()==2)
            {
                if(btn1.isVisible()){
                    carte = joeur.getPaquet().get(1);
                }else if(btn3.isDisplayable()){
                    carte = joeur.getPaquet().get(0);
                }    

            }else{
                carte = joeur.getPaquet().get(0);

            }
            btn2.setVisible(false);
        }
        else if(e.getSource() == btn3)
        {
            if(joeur.getPaquet().size()==3){
                carte = joeur.getPaquet().get(2);
            }
            else if(joeur.getPaquet().size()==2){
                carte = joeur.getPaquet().get(1);
            }
            else{
                carte = joeur.getPaquet().get(0);
            }
            btn3.setVisible(false);
        }
        else if(e.getSource() == btnQuitter)
        {
            System.exit(0);
        }
        else if(e.getSource() == btnSimple)
        {
            simple();
            nouveauJeu=true;

        }
        else if(e.getSource() == btnComplet)
        {
            simple();
            nouveauJeu=true;
            complet = true;
        }
        else if(e.getSource() == btnOK)
        {
            simple();
            nouveauJeu=true;
        }
        /*
        if(!nouveauJeu){
            tour(carte, table, joeur);
            tourOrdi(ordi, table);
            if(joeur.getPaquet().size()==0){
                if(paquet.getPaquet().size() == 0){
                                                    
                    pointsPartiJoeur = joeur.getPoints();
                    pointsPartiOrdi = ordi.getPoints();
                    calculerGainCarte(gagnatDernierTour);
                    if(!complet){
                        ronda = new JeuRonda("Score final", " Vos points " + pointsPartiJoeur , " Points d'Ordi "+ pointsPartiOrdi ,"");
                        entrerMenu=true;
                    }else{
                        pointsJeuJoeur +=pointsPartiJoeur;
                        pointsJeuOrdi +=pointsPartiOrdi;
                        if(pointsJeuJoeur >=41 || pointsJeuOrdi >= 41){
                            ronda = new JeuRonda("Score finale du jeu complet ", " Vos points " , pointsJeuJoeur + " Points d'Ordi "+ pointsJeuOrdi ,"");
                        }else{
                            ronda =new JeuRonda("Score apres cette partie", " Vos points " , pointsJeuJoeur + " Points d'Ordi "+ pointsJeuOrdi , "msg");
                        }
                        entrerMenu=true;
                    }
                }else {
                distribuer(paquet, table, joeur, ordi);
                btn1.setVisible(true);
                btn2.setVisible(true);
                btn3.setVisible(true);
                }
            }
        
            if(!entrerMenu){
            ronda = new JeuRonda(table,joeur,ordi);

            }
        }
    //panePrincipal.setVisible(true);    */
    setVisible(true);
}

    public static void calculerGainCarte(char dernier) {
        int joeurGainDernierTour=0, ordiGainDernierTour=0, totalTable=table.getPaquet().size();

        if(dernier == 'o'){
            joeurGainDernierTour = joeur.getGainCartes().size();
            ordiGainDernierTour = ordi.getGainCartes().size() + totalTable;

        }else{
            ordiGainDernierTour = ordi.getGainCartes().size();
            joeurGainDernierTour = joeur.getGainCartes().size() + totalTable;

        }
        if(ordiGainDernierTour > joeurGainDernierTour){
            pointsPartiOrdi += ordiGainDernierTour-20;
        }else if(ordiGainDernierTour < joeurGainDernierTour){
            pointsPartiJoeur += joeurGainDernierTour-20;

        }
    }

    public static void setPaquet(Paquet paquet) {
        JeuRonda.paquet = paquet;
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
    }
/*======================================== Main =============================================== */    


/*======================================== Main =============================================== */    
public static void main(String[] args) {
    table = new Paquet();
    joeur=new Paquet();
    ordi = new Paquet();
    paquet = new Paquet();

    chargerPaquet();
    distribuer(paquet, table, joeur, ordi);

    //ronda = new JeuRonda("Jeu Ronda","Choisissez votre jeu ", "cliquer simple ou complet","");
    ronda = new JeuRonda(joeur,ordi,table);
    ronda.clique();
}
}
