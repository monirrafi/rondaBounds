import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Paquet {
    private ArrayList<Carte> listPaquet= new ArrayList<>();
    private int points=0;
    private ArrayList<Carte> gainCartes = new ArrayList<Carte>();  

    public Paquet(){

    }
    public Paquet(ArrayList<Carte> listPaquet){
        this.setPaquet(listPaquet);
    }
    public ArrayList<Carte> getGainCartes() {
            return gainCartes;
    }
    public int sizeGain() {
        int size =0;
        if(this.gainCartes != null){
            size = this.getGainCartes().size();
        }
        return size;
    }
    public int getPoints() {
        return points;
    }
    public void setCarteInGainCartes(Carte card) {
        this.gainCartes.add(card);
        
    }
    public void setGainCartes(ArrayList<Carte> gainCartes) {
        this.gainCartes = gainCartes;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public ArrayList<Carte> getPaquet() {
        return listPaquet;
    }
    public void setPaquet(ArrayList<Carte> listPaquet) {
        this.listPaquet = listPaquet;
    }
    public String toString() {
        int i=1;
        String strPaquet="";
        if(this.listPaquet == null){
            strPaquet="vide";
        }else{    
            for(Carte card :this.listPaquet){
                if(i%10 != 0){
                    strPaquet += "\t"+ card.toString(); 
                }else{
                    strPaquet += "\t"+ card.toString() + "\n";
                }
                i++;
            }
        }
        return strPaquet;
    }

    public void moveOneCard(Carte card, Paquet autre){
        if(this.trouveIndexCarte(card) !=-1){
            autre.getGainCartes().add(card);
            this.getGainCartes().remove(card);
        }else{
            JOptionPane.showConfirmDialog(null, "carte n'apparient pas au paquet");
        }
    }
    public void moveCards(Paquet autre,char label,int n) {
        int i=0;
        Carte carte = new Carte();
        ArrayList<Carte> list = new ArrayList<Carte>();
        while(i<n){
            carte = this.listPaquet.get(0);
            carte.setLabel(label);
            list.add(carte);
            this.getPaquet().remove(carte);
            i++;
        }
        autre.setPaquet(list);
        
    }
    public void moveCardsNoDouble(Paquet autre,char lable,int n) {
        int i=0;
        ArrayList<Carte> list = new ArrayList<Carte>();
        Carte current;
        while(i<n){
            Boolean trouve=false;
            current = this.getPaquet().remove(i);
            for(Carte c:list){
                if(c.toEgal(current)){
                trouve = true;
                this.getPaquet().add(current);
                break;
                }
            }
            if(trouve == false){
                list.add(current);
                i++;
            }
        }
        autre.setPaquet(list);
    }
    public int trouveIndexCarte(Carte card) {
        int index=-1;
        for(Carte c:this.getPaquet()){
            if (c.toString().equals(card.toString())) {
                index = this.getPaquet().indexOf(c);
                
            }
        }
        return index;
        
    }
    public int trouveIndexRank(Carte card) {
        int index=-1;
        for(Carte c:this.getPaquet()){
            if (c.getRank() == card.getRank()) {
                index = this.getPaquet().indexOf(c);
                
            }
        }
        return index;
    }    

    // fonction pour verifier le ronda et tringa
    public int[] verification() {
        int[] tab = new int[2];
        Carte c0, c1, c2;
        c0=this.getPaquet().get(0);
        c1=this.getPaquet().get(1);
        c2=this.getPaquet().get(2);
        
        if (c0.toEgal(c1) && (c0.toEgal(c2))) {
            tab[0]=3;
            tab[1]= this.getPaquet().get(0).getRank();
        }
        else if (c0.toEgal(c1) || (c0.toEgal(c2))) {
            tab[0]=2;
            tab[1]= this.getPaquet().get(0).getRank();
        }
        else if (c1.toEgal(c2)) {
            tab[0]=2;
            tab[1]= this.getPaquet().get(1).getRank();
        }
        return tab;
    }
    public void ajoutPoints(int points) {
        this.points +=points;
    }
    public void pointageRandaTringa(Paquet autre){
        int[] paq1 = this.verification();
        int[] paq2 = autre.verification();
        int c1 = paq1[0], n1 = paq1[1],c2 = paq2[0], n2 = paq2[1];

        if(c1==2 && c2==0){
            this.points += 1;
        }else if( c1==0 && c2==2){
            autre.points += 1;
        }else if(c1== 2 && c2==2){
            if(n1 > n2){
                this.points += 2;
            }else if(n2 > n1){
                autre.points += 2;
            }else{
                this.points += 1;
                autre.points += 1;
            }
        }
        if(c1==3 && c2==0){
            this.points += 5;
        }else if( c1==3 && c2==2){
            this.points +=6;
        }else if( c1==0 && c2==3){
            autre.points += 5;
        }else if( c1==2 && c2==3){
            autre.points += 6;
        }else if(c1== 3 && c2==3){
            if(n1 > n2){
                this.points += 10;
            }else if(n2 > n1){
                autre.points += 10;
            }else{
                this.points += 5;
                autre.points += 5;
            }
        }
    }
        

}
