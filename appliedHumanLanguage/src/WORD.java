public class WORD {
    private String word;
    private String pos;
    private String root;
    private String plural;

    public WORD(String word, String pos, String root, String plural){
        this.word = word;
        this.pos = pos;
        this.root = root;
        this.plural = plural;
    }

    public String getWord(){
        return word;
    }

    public String  getPos(){
        return pos;
    }

    public String getRoot(){
        return root;
    }
    public String getPlural(){
        return plural;
    }
}
