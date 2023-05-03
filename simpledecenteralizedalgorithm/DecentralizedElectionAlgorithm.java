import java.util.ArrayList; 
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author FAYAW
 */
public class DecentralizedElectionAlgorithm {
    
 public static void main(String args[]) { 
      // Input list of candidates and voters 
      ArrayList<String> candidates = new ArrayList<>(); 
      candidates.add("Candidate A"); 
      candidates.add("Candidate B"); 
      candidates.add("Candidate C"); 

      ArrayList<String> voters = new ArrayList<>(); 
      voters.add("Voter 1"); 
      voters.add("Voter 2"); 
      voters.add("Voter 3"); 
      voters.add("Voter 4"); 
    
      // Shuffle the list of voters 
      Collections.shuffle(voters); 

      // Assign candidates to voters 
      Map<String, Integer> voteCount = new HashMap<>();
      for (int i = 0; i < voters.size(); i++) { 
         String voter = voters.get(i); 
         String candidate = candidates.get(i % candidates.size()); 
         System.out.println(voter + " voted for " + candidate);
         int count = voteCount.getOrDefault(candidate, 0);
         voteCount.put(candidate, count + 1);
      } 
      
      // Print the vote count for each candidate
      for (String candidate : voteCount.keySet()) {
          System.out.println(candidate + ": " + voteCount.get(candidate) + " votes");
      }
      
      // Determine the most voted candidate
      String mostVotedCandidate = Collections.max(voteCount.entrySet(), Map.Entry.comparingByValue()).getKey();
      System.out.println("The most voted candidate is: " + mostVotedCandidate);
   } 
} 
//This algorithm inputs a list of candidates and voters, then randomly assigns candidates to voters. 
//The code uses the Array List class in Java to manage the lists of candidates and voters, and 
//......the shuffle method from the Collections class to randomize the order of voters.
// The code then prints the vote count for each candidate 
//.....and determines the candidate with the most votes using the Collections.max() method.