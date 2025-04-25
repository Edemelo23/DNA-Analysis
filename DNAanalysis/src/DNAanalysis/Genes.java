package DNAanalysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Genes {
   private List<AminoAcid> aminoAcidList;
   private List<Integer> start_nucleotides = new ArrayList<>();
   private List<Integer> end_nucleotides = new ArrayList<>();
   private Map<Integer, String> aminoAcidsMap = new HashMap<>();
   private int mapindex = 1;
   
   public Genes() {
       aminoAcidList = AminoAcids.loadAminoAcids("aminoAcidTable.csv");
   }
  
   public void getGenes(BufferedReader reader) {
       String nextCodon;
       try {
           String[] codons = new String[1000];
           String line;
           while ((line = reader.readLine()) != null) {
               codons = line.split(",");
           }
           List<String> indCodons = List.of(codons);
           int startIndex = -1;
           for (int i = 0; i < indCodons.size(); i++) {
               String currentCodon = indCodons.get(i);
               if (currentCodon.equals("ATG")) {
                   List<String> possibleGene = new ArrayList<>();
                   possibleGene.add(currentCodon);
                   startIndex = i;
                   nextCodon = indCodons.get(++i);
                   while (!(nextCodon.equals("TAA") || nextCodon.equals("TAG") || nextCodon.equals("TGA"))) {
                       possibleGene.add(nextCodon);

                       nextCodon = indCodons.get(++i);
                   }
                  
                   possibleGene.add(nextCodon);
                  
                   if (possibleGene.size() > 50) {
                       String aminoAcidString = "";
                       for (String codon : possibleGene) {
                           // Find the amino acid abbr. for the current codon
                           String abbreviation = findAbbreviation(codon);
                           aminoAcidString += abbreviation;
                       }
                       aminoAcidsMap.put(mapindex++, aminoAcidString);
                      
                       int endIndex = startIndex + possibleGene.size() - 1;
                       start_nucleotides.add(startIndex);
                       end_nucleotides.add(endIndex);
                   }
               }
           }
           System.out.print("** Gene analysis for file: measlesSequenceRF1.csv **");
           aminoAcidsMap.forEach((i, n) -> {
               System.out.print("\nGene " + i + " (" + (n.length()) + "):");
               System.out.print("\n" + (start_nucleotides.get(i-1)*3) + ".." + (end_nucleotides.get(i-1)*3));
               System.out.print("\nSequence: " + n + "\n");
           });
           reader.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   // Helper method to find the amino acid abbr. for a given codon
   private String findAbbreviation(String codon) {
       for (AminoAcid aminoAcid : aminoAcidList) {
           if (aminoAcid.getCodons().contains(codon)) {
               return String.valueOf(aminoAcid.getAbbreviation());
           }
       }
       return ""; // Return empty string if no abbr. found
   }
}

