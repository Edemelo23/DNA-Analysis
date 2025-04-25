package DNAanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class AminoAcid {
    private String name;
    private String abbreviation;
    private List<String> codons;

    public AminoAcid(String name, String abbreviation, List<String> codons) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.codons = codons;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<String> getCodons() {
        return codons;
    }

    public void setCodons(List<String> codons) {
        this.codons = codons;
    }
}

public class AminoAcids {
	public static List<AminoAcid> loadAminoAcids(String filename) {
        List<AminoAcid> aminoAcids = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("aminoAcidTable.csv"))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    // Skip the header line since it's not an amino acid
                    headerSkipped = true;
                    continue;
                }
                String[] parts = line.split(","); // delimeter
                String name = parts[0];
                String abbreviation = parts[2]; // at token 2 because we aren't using the 3 letter abbr.
                List<String> codons = new ArrayList<>();
                for (int i = 3; i < parts.length; i++) {
                    codons.add(parts[i]);
                }
                AminoAcid aminoAcid = new AminoAcid(name, abbreviation, codons);
                aminoAcids.add(aminoAcid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        return aminoAcids;
		
    }


}

