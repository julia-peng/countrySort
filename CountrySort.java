/*
 * Julia Peng, Jonathan Ye, Lily Liu
 * 05/04/2024
 * Ms. Krasteva
 * This program reads a list of countries from a file and prints the countries ordered by population and name into two files. It uses parallel ArrayLists as well as the insertion sort to achieve this.
 */

//importing necessary elements
import java.io.*;
import java.util.*;

public class CountrySort {
    // String with list of all countries with exceptions (capital cities with more
    // than one word). the index of each is placed before it, with letters being
    // used to represent numbers greater than 9
    private String exceptions = "0Andorra Andorra la 1Antigua and Barbuda St. 2Argentina Buenos 3Belarus Mensk 4Bolivia La 5Brunei Bandar Seri 6Cambodia Phnom 7Costa Rica San 8Dominican Republic Santo 9El Salvador San AEthiopia Addis BGrenada St. CGuatemala Guatemala DIndia New EKuwait Kuwait FMalaysia Kuala GMauritius Port HMexico Mexico IMongolia Ulan JPanama Panama KPapua New Guinea Port LSan Marino San MSão Tomé and Príncipe São NUkraine Kyiv OUnited Arab Emirates Abu PUnited States Washington, ";

    // array to hold all countries proper form for countries with exceptions
    private String[] countryCap = { "Andorra", "Antigua and Barbuda", "Argentina", "Belarus", "Bolivia", "Brunei",
            "Cambodia", "Costa Rica", "Dominican Republic", "El Salvador", "Ethiopia", "Grenada", "Guatemala", "India",
            "Kuwait", "Malaysia", "Mauritius", "Mexico", "Mongolia", "Panama", "Papua New Guinea", "San Marino",
            "São Tomé and Príncipe", "Ukraine", "United Arab Emirates", "United States" };
    // initialize parallel ArrayLists
    private ArrayList<String> countryName = new ArrayList<String>();
    private ArrayList<String> capitalName = new ArrayList<String>();
    private ArrayList<String> area = new ArrayList<String>();
    private ArrayList<String> pop = new ArrayList<String>();

    // verifys that file exists and has content
    public void verifyFile() {
        try {
            BufferedReader read = new BufferedReader(new FileReader("Countries-Population.txt"));
            read.readLine();
            read.close();
        } catch (IOException e) {
            System.out.println("There was an issue with your file!");
            System.out.println("Please verify your file and try again.");
        }
    }

    // reads data and stores into parallel arrays
    public void readData() {
        int count = 0; // count var to set vaue of array
        BufferedReader read = null; // buffered reader with utf-8 encoding to be able to detect special
                                    // characters
        try {
            read = new BufferedReader(
                    new InputStreamReader(new FileInputStream("Countries-Population.txt"), "UTF-8"));

            // set value of count and initialize input array
            while (true) {
                String testInput = read.readLine();
                if (testInput == null)
                    break;
                count++;
            }
            read.close();

            read = new BufferedReader(
                    new InputStreamReader(new FileInputStream("Countries-Population.txt"), "UTF-8")); // redeclare
                                                                                                      // to start
                                                                                                      // from
                                                                                                      // beginning
            for (int i = 0; i < count; i++) { // fill arraylists
                String[] splitInput = (read.readLine()).split(" "); // split input into word-by-word format
                int index = splitInput.length - 3; // index of last word of country (in most cases)

                countryName.add(convertCountryName(splitInput, index)); // fill arrayLists
                capitalName.add(splitInput[index]);
                area.add(splitInput[index + 1]);
                pop.add(splitInput[index + 2]);
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // method to sort and display based on name
    public void sortDisplayName() {
        BufferedWriter write = null; // Buffered writer to be used in this method
        ArrayList<Double> areaCon = new ArrayList<Double>(); // Double ArrayList to hold the converted values of area
                                                             // (number vs. String comparisons)
        try {
            write = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(new File("sortedByCountry.txt")), "UTF-8"));

            for (int i = 0; i < area.size(); i++) {
                areaCon.add(Double.parseDouble(area.get(i).replace(",", "")));
            }

            for (int i = 1; i < countryName.size(); i++) { // sort by country name
                String cval = countryName.get(i); // temporary values for later assignment
                String caval = capitalName.get(i);
                String pval = pop.get(i);
                String aval = area.get(i);

                int j;
                for (j = i - 1; j >= 0 && countryName.get(j).compareTo(cval) > 0; j--) {
                    countryName.set(j + 1, countryName.get(j));
                    capitalName.set(j + 1, capitalName.get(j));
                    pop.set(j + 1, pop.get(j));
                    area.set(j + 1, area.get(j));
                }

                countryName.set(j + 1, cval); // move other values as well
                capitalName.set(j + 1, caval);
                pop.set(j + 1, pval);
                area.set(j + 1, aval);
            }
            for (int i = 0; i < countryName.size(); i++) { // output
                displayer(countryName.get(i), pop.get(i), write);
            }
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // method to sort and display population
    public void sortDisplayPop() {
        BufferedWriter write = null;
        ArrayList<Double> popCon = new ArrayList<Double>();
        try {
            write = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(new File("sortedByPopulation.txt")), "UTF-8"));

            for (int i = 0; i < pop.size(); i++) { // fill converted array
                popCon.add(Double.parseDouble(pop.get(i).replace(",", "")));
            }

            for (int i = 1; i < popCon.size(); i++) { // sort by population
                double cval = popCon.get(i); // variables to hold temporary value
                String cName = countryName.get(i);
                String caName = capitalName.get(i);
                String p = pop.get(i);
                String a = area.get(i);
                int j; // j to

                for (j = i - 1; j >= 0 && popCon.get(j) <= cval; j--) {
                    popCon.set(j + 1, popCon.get(j));
                    countryName.set(j + 1, countryName.get(j));
                    capitalName.set(j + 1, capitalName.get(j));
                    pop.set(j + 1, pop.get(j));
                    area.set(j + 1, area.get(j));
                }

                popCon.set(j + 1, cval);
                countryName.set(j + 1, cName);
                capitalName.set(j + 1, caName);
                pop.set(j + 1, p);
                area.set(j + 1, a);
            }

            for (int i = 0; i < pop.size(); i++) {
                displayer(countryName.get(i), pop.get(i), write);
            }

            write.close();
        } catch (IOException e) {
        }
    }

    // special print method that checks for exceptions
    public void displayer(String one, String two, BufferedWriter w) {
        String output = ""; // String for output, will be added to depending on pathway
        try {
            if (exceptions.indexOf(one) > -1 && !one.equals("Guinea ")) {
                int index = 0;
                if ((int) exceptions.charAt(exceptions.indexOf(one) - 1) < 65) {
                    index = Integer.parseInt("" + exceptions.charAt(exceptions.indexOf(one) - 1));
                } else {
                    index = (int) (exceptions.charAt(exceptions.indexOf(one) - 1) - 55);
                }
                output = countryCap[index];
            } else {
                output = one;
            }

            w.write(output + "\t\t\t" + two);
            w.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // method that returns country name as one String
    public String convertCountryName(String[] arr, int num) {
        String output = "";
        for (int i = 0; i < num; i++) {
            output += (arr[i] + " ");
        }
        return output;
    }

    // main method
    public static void main(String[] args) {
        CountrySort a = new CountrySort();
        a.verifyFile();
        a.readData();
        a.sortDisplayName();
        a.sortDisplayPop();
    }
}