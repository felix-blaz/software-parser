import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Vector<WORD> wordVector = new Vector<>();
        Vector<POS> posVector = new Vector<>();
        Vector<RULE> ruleVector = new Vector<>();
        boolean firstLine = true;
        //you may need to change this to run code
        try (BufferedReader wordReader = new BufferedReader(new FileReader("C:\\Users\\narut\\IdeaProjects\\appliedHumanLanguageAssesemnt\\src\\lexicon.txt"));
             BufferedReader posReader = new BufferedReader(new FileReader("C:\\Users\\narut\\IdeaProjects\\appliedHumanLanguageAssesemnt\\src\\rules.txt"));
             BufferedReader ruleReader = new BufferedReader(new FileReader("C:\\Users\\narut\\IdeaProjects\\appliedHumanLanguageAssesemnt\\src\\rules.txt"))) {

            // Skip the first line in wordReader
            wordReader.readLine();

            // Read and store word information
            String wordLine;
            while ((wordLine = wordReader.readLine()) != null) {
                String[] parts = wordLine.split("\\s");
                if (parts.length == 4) {
                    WORD word = new WORD(parts[0], parts[1], parts[2], parts[3]);
                    wordVector.add(word);
                }
            }

            // Read and store parts of speech
            String posLine;
            if ((posLine = posReader.readLine()) != null) {
                // Split up the POS line using space as a delimiter
                String[] posParts = posLine.split("\\s");
                for (String pos : posParts) {
                    posVector.add(new POS(pos));
                }
            }

            // Read and store rules
            String ruleLine;
            while ((ruleLine = ruleReader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the first line
                }
                ruleVector.add(new RULE(ruleLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        // Instantiating Parser and passing the user's input
        Parser parser = new Parser(wordVector, ruleVector, posVector);

        Scanner scanner = new Scanner(System.in);
        //The person likes the green dog
        System.out.print("Enter a sentence: ");
        String userInput = scanner.nextLine();
        // Parse the user's input
        parser.parseUserSentence(userInput);
    }
}
