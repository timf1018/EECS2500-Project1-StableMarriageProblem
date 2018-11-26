import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Project1 {

    static ArrayStack stackA = new ArrayStack(4);
    static ArrayStack stackB = new ArrayStack(4);

    public static void main(String[] args) {
        ArrayList<Person> groupA = new ArrayList<Person>();
        ArrayList<Person> groupB = new ArrayList<Person>();

        try {
            BufferedReader objReader = null;
            String currentLine;
            objReader = new BufferedReader(new FileReader("C:\\Project1TestData.txt"));
            while ((currentLine = objReader.readLine()) != null) {
                int numberOfPairs = Integer.parseInt(currentLine);

                for (int i = 0; i < numberOfPairs; i++) {
                    int[] prefArray = new int[4];
                    String name = objReader.readLine();
                    String numbers = objReader.readLine();
                    String[] tokens = numbers.split("\t");
                    int j = 0;
                    for(String token : tokens){
                        prefArray[j++] = Integer.parseInt(token);
                    }
                    Person person = new Person(name, i, prefArray);
                    groupA.add(person);
                }

                for (int i = 0; i < numberOfPairs; i++) {
                    int[] prefArray = new int[4];
                    String name = objReader.readLine();
                    String numbers = objReader.readLine();
                    String[] tokens = numbers.split("\t");
                    int j = 0;
                    for(String token : tokens) {
                        prefArray[j++] = Integer.parseInt(token);
                    }
                    Person person = new Person(name, i, prefArray);
                    groupB.add(person);
                }
            }
        }
        catch (IOException e) {
            System.out.println("IOExcepteion occurred.");
        }

        calculateMatches(groupA, groupB);
        printTeams(groupA);
    }

    private static void calculateMatches(ArrayList groupA, ArrayList groupB) {

        try {
            System.out.println("***calcMatches");
            int i = 0;
            while (!stackA.isFull() && i < 4) {
                stackA.push(groupA.get(i));
                i++;
            }

            while (!stackA.isEmpty()) {
                Person groupAPerson = (Person) stackA.top();
                stackA.pop();
                int j=0;
                while (groupAPerson.getPartner() == null) {
                    // start with most preferred partner
                    int preferredPartnerIndex = groupAPerson.getPartnerIndex(j++);
                    Person proposedPartner = (Person) groupB.get(preferredPartnerIndex);

                    if (proposedPartner.getPartner() == null) {
                        System.out.println("immediate marriage");
                        group(groupAPerson, proposedPartner);
                    } else {
                        System.out.println("made it here 0");
                        int groupBPersonPartnerPositionVal = proposedPartner.getPartner().getPosition();
                        int groupAPersonsPositionVal = groupAPerson.getPosition();
                        int currentPartnerPriority = proposedPartner.getPriority(groupBPersonPartnerPositionVal);
                        int groupAPersonPriority = proposedPartner.getPriority(groupAPersonsPositionVal);
                        System.out.println("Person A - " + groupAPerson.getName());
                        System.out.println(groupAPerson.getPosition());
                        System.out.println("Person B - " + proposedPartner.getName());
                        System.out.println(proposedPartner.getPartner().getPosition());
                        System.out.println("Person B Current Partner - " + proposedPartner.getPartner().getName());
                        System.out.println(currentPartnerPriority);
                        System.out.println(groupAPersonPriority);
                        if (currentPartnerPriority > groupAPersonPriority) {
                            System.out.println("Made it here 1");
                            ungroup(proposedPartner.getPartner(), proposedPartner);
                            group(groupAPerson, proposedPartner);
                        }
                    }
                }
            }
        } catch (StackUnderflowException ex) {
            System.out.println("StackUnderflowException occurred.");
        } catch (StackOverflowException ex) {
            System.out.println("StackOverflowException occurred.");
        }
    }

    private static void group(Person personA, Person personB) {
        System.out.println("Making group");
        personA.setPartner(personB);
        personB.setPartner(personA);
    }

    private static void ungroup(Person personA, Person personB) {
        try {
            System.out.println("Breaking group");
            personA.setPartner(null);
            personB.setPartner(null);
            stackA.push(personA);
        }
        catch (StackOverflowException ex) {
            System.out.println("StackOverflowException occurred.");
        }
    }

    private static void printTeams(ArrayList groupA) {
        for (int i = 0; i < groupA.size(); i++) {
            Person personA = (Person) groupA.get(i);
            if (personA.getPartner() != null) {
                System.out.println("Team " + i + ": " + personA.getName() + " and " + personA.getPartner().getName());
            }
            else {
                System.out.println("No Stable pair exists");
            }
        }
    }


}
