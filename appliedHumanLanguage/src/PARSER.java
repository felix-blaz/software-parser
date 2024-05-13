import java.util.Vector;

class Parser {
    private final Vector<WORD> lexicon;
    private final Vector<RULE> rules;
    private final Vector<POS> pos;

    public Parser(Vector<WORD> lexicon, Vector<RULE> rules, Vector<POS> pos) {
        this.lexicon = lexicon;
        this.rules = rules;
        this.pos = pos;
    }

    public void parseUserSentence(String userInput) {
        //spliting the user inpit into individual words

        String[] words = userInput.split("\\s");

        // checking if all the words an in the lexicon and the sentence length is okay
        if (areAllWordsInLexicon(words) && isValidSentence(words)) {
            System.out.println(rules.get(0).getRule()); //print the first rule S NP VP
            String firstWord = words[0].trim();  //getting the first word passed in
            //if the first word is "A" use the rules for article
            if (firstWord.equals(lexicon.get(1).getWord())) { //if the first word equals  "A" in the lexicon use the rules for article
                applyRulesA(words);
            } else if (firstWord.equals(lexicon.get(0).getWord())) {       //if the fist word is The use "The" rules for determiner
                applyRulesD(words);
            }
        } else System.out.println("Error: invalid sentence, one or more words are not in the lexicon");
    }


    // This method checks if all words in an array are in the lexicon
    private boolean areAllWordsInLexicon(String[] words) {
        for (String word : words) {
            if (!isWordInLexicon(word.trim())) {
                return false;
            }
        }
        return true;
    }

    // This method checks if a word is in the lexicon
    private boolean isWordInLexicon(String word) {
        for (WORD lexiconWord : lexicon) { // loops through each word in the lexicon and checks if it is equal to a word in the lexicon
            if (word.equalsIgnoreCase(lexiconWord.getWord())) {
                return true;
            }
            // since like and dislike are in the lexicon but as a root i put this check to see if the word is == to a root
            else if (word.equalsIgnoreCase(lexiconWord.getRoot()) || (word.equalsIgnoreCase(lexiconWord.getRoot()))) {
                return true;
            }
        }
        return false;
    }

    private void applyRulesA(String[] words) {

        for (int i = 0; i < words.length; i++) {
            String currentWord = words[i].trim();

            switch (i) {
                case 1: // Noun
                    if (currentWord.equalsIgnoreCase("person")) {
                        System.out.println(rules.get(3).getRule()); // NP ART NN
                        System.out.println(rules.get(5).getRule()); // ART “A”
                        System.out.println(rules.get(6).getRule()); // NN “person”
                        // String par = "[S[NP[NP[ART[A]][NN[person]]";
                    } else {
                        System.out.println("Invalid sentence");
                        return;
                    }
                    break;
                case 2: // Verb
                    if (currentWord.equalsIgnoreCase("likes")) {
                        System.out.println(rules.get(8).getRule()); //RULE: VP VBZ NP
                        System.out.println(rules.get(13).getRule()); //RULE: VBZ "likes"

                    } else if (currentWord.equalsIgnoreCase("dislikes")) {
                        System.out.println(rules.get(8).getRule()); //RULE: VP VBZ NP
                        System.out.println(rules.get(11).getRule()); //RULE: VBZ ”dislikes”
                    } else {
                        System.out.println("Invalid sentence");
                        return;
                    }
                    break;
                case 3: // det
                    if (currentWord.equalsIgnoreCase("the")) {
                        System.out.println(rules.get(14).getRule()); //RULE: NP DT JJ NN
                        System.out.println(rules.get(15).getRule()); //RULE: DT "the"
                    } else {
                        System.out.println("Invalid sentence");
                        return;
                    }
                    break;
                case 4: // Adjective
                    System.out.println(rules.get(16).getRule()); //RULE: JJ “green”
                    break;
                case 5: // Noun
                    if (currentWord.equalsIgnoreCase("dog")) {
                        System.out.println(rules.get(17).getRule()); //RULE: NN “dog”
                    } else {
                        System.out.println("Invalid sentence");
                        return;
                    }
                    break;
            }
        }
        String posTree = genBracketStructure("S", words);
        System.out.println(posTree);
        String theTree = getTreeStructure(words);
        System.out.println(theTree);

    }

    private void applyRulesD(String[] words) {

        for (int i = 0; i < words.length; i++) {
            String currentWord = words[i].trim();

            switch (i) {
                case 1: // Noun
                    if (currentWord.equalsIgnoreCase("person")) {
                        System.out.println(rules.get(1).getRule()); //NP DT NN
                        System.out.println(rules.get(4).getRule()); // DT “The”
                        System.out.println(rules.get(6).getRule()); // NN “person”
                    } else if (currentWord.equalsIgnoreCase("people")) {
                        System.out.println(rules.get(2).getRule()); // NP DT NNS
                        System.out.println(rules.get(4).getRule()); // DT “The”
                        System.out.println(rules.get(7).getRule()); // NNS "people"
                    } else {
                        System.out.println("Invalid sentence");
                        return;
                    }
                    break;
                case 2: // Verb
                    String previousWord = words[1].trim();
                    // taking care of people likes and person like
                    if ((previousWord.equalsIgnoreCase("people") && (currentWord.equalsIgnoreCase("like")))) {

                        System.out.println(rules.get(9).getRule()); //VP VBP NP
                        System.out.println(rules.get(12).getRule()); //RULE: VBZ "like”


                    } else if ((previousWord.equalsIgnoreCase("people") && (currentWord.equalsIgnoreCase("dislike")))) {

                        System.out.println(rules.get(9).getRule()); //VP VBP NP
                        System.out.println(rules.get(10).getRule()); //RULE: VBZ "dislike"


                    } else if ((previousWord.equalsIgnoreCase("person") && (currentWord.equalsIgnoreCase("likes")))) {
                        System.out.println(rules.get(8).getRule()); //RULE: VP VBZ NP
                        System.out.println(rules.get(13).getRule()); //RULE: VBZ ”likes”

                    } else if ((previousWord.equalsIgnoreCase("person") && (currentWord.equalsIgnoreCase("dislikes")))) {
                        System.out.println(rules.get(8).getRule()); //RULE: VP VBZ NP
                        System.out.println(rules.get(11).getRule()); //RULE: VBZ ”dislikes”

                    } else {
                        System.out.println("Invalid sentence");
                        return;
                    }
                    break;
                case 3: // Noun
                    if (currentWord.equalsIgnoreCase("the")) {
                        System.out.println(rules.get(14).getRule()); //RULE: NP DT JJ NN
                        System.out.println(rules.get(15).getRule()); //RULE: DT "the"
                    } else {
                        System.out.println("Invalid sentence");
                        return;
                    }
                    break;
                case 4: // Adjective
                    System.out.println(rules.get(16).getRule()); //RULE: JJ “green”
                    break;
                case 5: // Noun
                    if (currentWord.equalsIgnoreCase("dog")) {
                        System.out.println(rules.get(17).getRule()); //RULE: NN “dog”
                    } else {
                        System.out.println("Invalid sentence");
                        return;
                    }
                    break;
            }
        }
        String posTree = genBracketStructure("S", words);
        System.out.println(posTree);
        String theTree = getTreeStructure(words);
        System.out.println(theTree);

    }

    //To generate the bracketed structure  it will take the array of words inputed by the user

    private String genBracketStructure(String firstWord, String[] words) {
        StringBuilder braStr = new StringBuilder("[" + firstWord);

        // it will seperate the array and look at ech word at a time saved in a variable currrentWord
        for (int i = 0; i < words.length; i++) {
            String currentWord = words[i].trim();

            switch (i) {
                //it will always presume the first word is an article or determiner and return NP from the pos
                // then get the pos tag from the current word
                case 0: // Article or Determiner
                    braStr.append("[" + getPp(1));
                    braStr.append("[" + getPp(1));// getting NP from parts of speech
                    braStr.append(getPosTag(currentWord));

                    break;


                case 1: // Noun
                    braStr.append(getPosTag(currentWord)).append("]");
                    braStr.append("]");
                    break;
                case 2: // Verb
                    braStr.append("[" + getPp(2));// getting VP from parts of speech
                    braStr.append("[" + getPp(2));
                    braStr.append(getPosTag(currentWord));


                    break;
                case 3: // Noun
                    braStr.append("[" + getPp(1));// getting NP from parts of speech
                    braStr.append("[" + getPp(1));// getting NP from parts of speech
                    braStr.append(getPosTag(currentWord));

                    break;
                case 4: // Adjective
                    braStr.append(getPosTag(currentWord));
                    break;
                case 5: // Noun
                    braStr.append(getPosTag(currentWord));
                    braStr.append("]");
                    break;
            }
        }

        braStr.append("]");
        braStr.append("]");
        braStr.append("]");
        braStr.append("]");
        return braStr.toString();
    }

    private String getTreeStructure(String[] words) {

        String firstWord = words[0].trim();
        String secondWord = words[1].trim();
        String thirdWord = words[2].trim();
        String forthWord = words[3].trim();
        String fifthtWord = words[4].trim();
        String sixthWord = words[5].trim();

        TreeNode tree = new TreeNode(getPp(0),  //S
                new TreeNode(getPp(1),  //NP
                        new TreeNode(getTreeTag(firstWord), //printing pos of first word
                                new TreeNode(firstWord) //printing first word
                        ),
                        new TreeNode(getTreeTag(secondWord), //printing pos of second word
                                new TreeNode(secondWord)    //printing second word
                        )
                ),
                new TreeNode(getPp(2),
                        new TreeNode(getPp(2),
                                new TreeNode(getTreeTag(thirdWord), //printing pos of third word
                                        new TreeNode(thirdWord) //printing third word
                                ),
                                new TreeNode(getPp(1),  //NP
                                        new TreeNode(getTreeTag(forthWord), //printing pos of forth word
                                                new TreeNode(forthWord)     //printing forth word
                                        ),
                                        new TreeNode(getTreeTag(fifthtWord),    //printing pos of fifth word
                                                new TreeNode(fifthtWord)        //printing fifth word
                                        ),
                                        new TreeNode(getTreeTag(sixthWord), //printing pos of sixth word
                                                new TreeNode(sixthWord)     //printing sixth word
                                        )
                                )
                        )
                )
        );


        return tree.printTree("", true);
    }

    // getting parts of speach from the lecicon by checking the pos in the lexicon that belongs to the word
    private String getPosTag(String word) {
        for (int i = 0; i <= 8; i++) {
            if (word.equalsIgnoreCase(lexicon.get(i).getWord())) { //if the word is = to a word in the lexicon return the words corresponding pos
                return "[" + lexicon.get(i).getPos() + "[" + word + "]]";
                // if the word in the lexicon is = to a root and is not person assign it VBP and return the word
            } else if (word.equalsIgnoreCase(lexicon.get(i).getRoot()) && !word.equalsIgnoreCase("person")) {
                return "[" + "VBP" + "[" + word + "]]";
            }
        }
        return "[INVALID_POS[" + word + "]]"; // Return an invalid POS tag if not found in the pos vector
    }

    private String getTreeTag(String word) {
        for (int i = 0; i <= 8; i++) {
            if (word.equalsIgnoreCase(lexicon.get(i).getWord())) { //if the word is = to a word in the lexicon return the words corresponding pos
                return lexicon.get(i).getPos();
                // if the word in the lexicon is = to a root and is not person assign it VBP and return the word
            } else if (word.equalsIgnoreCase(lexicon.get(i).getRoot()) && !word.equalsIgnoreCase("person")) {
                return "VBP";
            }
        }
        return "[INVALID_POS[" + word + "]]"; // Return an invalid POS tag if not found in the pos vector
    }

    // retreving pasts of speach from the pos vector
    private String getPp(int i) {
        String myPos = null;
        if (i >= 0 && i < pos.size()) {
            myPos = pos.get(i).getPos();
        }
        return myPos;
    }

    private boolean isValidSentence(String[] words) { // checks that the sentence is equal to 6 words and the first word is either "A" or "The"
        return words.length == 6 && (words[0].equalsIgnoreCase("A") || words[0].equalsIgnoreCase("The"));
    }
}
