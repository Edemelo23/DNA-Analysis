package DNAanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
    private static List<AminoAcid> aminoAcidList;

    public Main() {
    	aminoAcidList = AminoAcids.loadAminoAcids("aminoAcidTable.csv");
    }

    public static void main(String[] args) {
        new Main(); // Create an instance of Main to load amino acids
        Genes gene = new Genes();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("covidSequenceRF1.csv"));
            gene.getGenes(reader);
            reader.close(); // Close the reader after use
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            printAminoAcidList(aminoAcidList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printAminoAcidList(List<AminoAcid> aminoAcidList2) {
        for (AminoAcid aminoAcid : aminoAcidList2) {
            System.out.println("Name: " + aminoAcid.getName());
            System.out.println("Abbreviation: " + aminoAcid.getAbbreviation());
            System.out.println("Codons: " + aminoAcid.getCodons());
            System.out.println();
        }
    }
}