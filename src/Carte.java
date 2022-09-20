public class Carte implements Comparable<Carte>{
    private String suit;
    private int rank;
    private char label;
    public Carte(){};
    
    public Carte(String suit, int rank, char label){
        this.setLabel(label);
        this.setRank(rank);
        this.setSuit(suit);
    }
    public Carte(String suit, int rank){
        this(suit,rank,' ');
         
    }
    public void setLabel(char label) {
        this.label = label;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public void setSuit(String suit) {
        this.suit = suit;
    }

    public char getLabel() {
        return label;
    }
    public int getRank() {
        return rank;
    }
    public String getSuit() {
        return suit;
    }
    public String toString() {
        return  this.suit+this.rank+"_"+this.label;
        
    }
    public boolean toSmall(Carte card) {
        boolean retour = false;
        if(this.getRank() < card.getRank()){
            retour = true;
        } 
        return retour; 
    }
    public boolean toEgal(Carte card) {
        boolean retour = false;
        if(this.getRank() == card.getRank()){
            retour = true;
        } 
        return retour; 
    }
    public int compareTo(Carte autre) {
              return (this.rank - autre.rank);
       }

}

