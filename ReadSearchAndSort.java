
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ReadSearchAndSort {

    /*
    * ========================================================================
    * START TODO #2: "readWords"
    */
    public static String[]  readWords(String fileName)  throws FileNotFoundException{
   	 File file = new File(fileName);
   	 Scanner read = new Scanner(file, "UTF-8");
   	 read.useDelimiter("\\s+|\\—");
   	 String[] words = new String[0];
   	 String word;

   	 while(read.hasNext()) {
   		 word = read.next();
   	    word = word.toLowerCase();
   		 word = word.replaceAll("[^a-zA-Z]", "");
   		 words = Arrays.copyOf(words, words.length+1);
   		 words[words.length-1] = word;
   	 }
   	 read.close();
        return words;
    }
    /*
    * END TODO #2: "readWords"
    * ========================================================================
    */

    /*
    * ========================================================================
    * START TODO 3: "countWordsInUnsorted"
    */
    public static int  countWordsInUnsorted(String[] wordsToCount,String countedWord){
   	 int counter = 0;
   	 for(int i=0; i<wordsToCount.length; i++) {
   		 if (wordsToCount[i].equals(countedWord))
   			 counter ++;
   	 }
        return counter;
    }
    /*
    * END TODO #3: "countWordsInUnsorted"
    * ========================================================================
    */



    /*
    * ========================================================================
    * START TODO #4: "mergeSort"
    */
    //don't create new array. only use tempArray
    public static void mergeSort(String[] arrayToSort, String[] tempArray, int first, int last)
    {
   	 if( first < last )
 		 {
   		 int center = (first + last) / 2;
   		 mergeSort(arrayToSort, tempArray, first, center);
   		 mergeSort(arrayToSort, tempArray, center + 1, last);
   		 merge(arrayToSort, tempArray, first, center + 1, last);
 	 	 }	
    }
    
    public static void merge(String[] arrayToSort, String[] tempArray, int first, int mid, int last)
    {
   	 int leftEnd = mid - 1;
   	 int k = first;
       int num = last - first + 1;

       while(first <= leftEnd && mid <= last) {
           if(arrayToSort[first].compareTo(arrayToSort[mid]) <= 0)
               tempArray[k++] = arrayToSort[first++];
           else
               tempArray[k++] = arrayToSort[mid++];
       }

       while(first <= leftEnd) {
           tempArray[k++] = arrayToSort[first++];
       }

       while(mid <= last) {
           tempArray[k++] = arrayToSort[mid++];
       }

       for(int i = 0; i < num; i++, last--) {
           arrayToSort[last] = tempArray[last];
       }
    }
    /*
    * END TODO #4: "mergeSort"
    * ========================================================================
    */



    /*
    * ========================================================================
    * START TODO #5: binary search
    */
    public static int binarySearch(String[] sortedWords, String query, int startIndex, int endIndex){
   	 int mid = (startIndex+endIndex)/2;
   	 if(startIndex>endIndex) return -1;
   	 if(sortedWords[mid].compareTo(query)==0) {
   		 return mid;
   	 }
   	 if(sortedWords[mid].compareTo(query)>0) {
   		 return binarySearch(sortedWords, query, startIndex, mid-1);
   	 }
   	 else {
   		 return binarySearch(sortedWords, query, mid+1, endIndex);
   	 }
    }

    public static int getSmallestIndex(String[] words, String query, int startIndex, int endIndex){
   	 int findSmall = binarySearch(words,query,startIndex, endIndex);
   	 if(findSmall == -1) {
   		 return endIndex;
   	 }
   	 if(words[findSmall-1].compareTo(query)!=0) {
   		 return findSmall;
   	 }
   	 else
   		 return getSmallestIndex(words, query, startIndex, endIndex-1);
    }
    public static int getLargestIndex(String[] words, String query, int startIndex, int endIndex){
       int findLarge = binarySearch(words, query, startIndex, endIndex);
       if(findLarge == -1) {
   		 return startIndex;
   	 }
   	 if(words[findLarge+1].compareTo(query)!=0) {
   		 return findLarge;
   	 }
   	 else
   		 return getLargestIndex(words, query, startIndex+1, endIndex);
    }

    public static int  countWordsInSorted(String[] wordsToCount,String countedWord){
   	 int location = binarySearch(wordsToCount, countedWord, 0, wordsToCount.length-1);
   	 if(location == -1) {
   		 return 0;
   	 }
   	 int small = getSmallestIndex(wordsToCount,countedWord, 0, location);
   	 int large= getLargestIndex(wordsToCount,countedWord,location,wordsToCount.length-1);
   	 if(large-small ==0) {
   		 return 1;
   	 }
   	 else if(large-small>0){
   		 return large-small+1;
   	 }
   	 else
   		 return -1;
    }
    /*
    * END TODO #5: binary search
    * ========================================================================
    */



    public static void main(String[] args) throws FileNotFoundException{


        /*
        * TODO 1
        * ========================================================================
        */
   	 
        String filename=null;
        String[] queryWords;
        if(args.length==1) {
      	  queryWords = new String[]{"doctor", "frankenstein", "the", "monster", "igor", 
					  "student", "college", "lightning", "electricity", "blood", 
					  "soul"};
        }
        else{
      	  queryWords = new String[args.length-1];
        }
        int index =0;
        for(int i=1; i<args.length; i++) {
          			  queryWords[index] = args[i];
          			  index++;
        }

        filename = args[0];
        

        /*
        * TODO 1
        * ========================================================================
        */
       

        /*
        * TODO 2
        * ========================================================================
        */

        String[] allWords = readWords(filename);

        /*
        * TODO 2
        * ========================================================================
        */

        int timingCount = 100;
        System.out.println("\nArguments: use '" +  String.join(",",queryWords) + "' words, time " + timingCount + " iterations, search for words: " + 
String.join(",", queryWords) + "\n");




        System.out.println("NAIVE SEARCH:");


        // Record the current time
        long t0 = (new Date()).getTime();

        // Time how long it takes to run timingCount loops
        //   for countWordsInUnsorted
        for (int j = 0; j < timingCount; j++) {
            for (int i = 0; i < queryWords.length; i++) {

                /*
                * ========================================================================
                *   START: TODO #3
                */
            	 
                int count = countWordsInUnsorted(allWords,queryWords[i]); //returns counter from method
                
                /*
                *   END: TODO #3
                * ========================================================================
                */

                // For the first one, print out the value
                if (j == 0)
                    System.out.println(queryWords[i] + ":" + count);

            }
        }

        // Record the current time
        long t1 = (new Date()).getTime();

        long timeToSeachNaive = t1 - t0;
        int searchCount = timingCount*queryWords.length;

        // Output how long the searches took, for how many searches
        // (remember: searches = timingcount * the number of words searched)
        System.out.printf("%d ms for %d searches, %f ms per search\n", timeToSeachNaive, searchCount, timeToSeachNaive*1.0f/searchCount);

        // Sort the list of words
        System.out.println("\nSORTING: ");

        /*
        * ========================================================================
        *   START: TODO #4
        */

        //call mergesort here
  	      String[] temp = new String[allWords.length];
  	      mergeSort(allWords, temp, 0, allWords.length-1);

        /*
        *   END: TODO #4
        * ========================================================================
        */

        // Record the current time
        long t2 = (new Date()).getTime();

        // Output how long the sorting took
        long timeToSort = t2 - t1;
        System.out.printf("%d ms to sort %d words\n", timeToSort, allWords.length);

        // Output every 1000th word of your sorted wordlist
        int step = (int)(allWords.length*.00663 + 1);
        System.out.print("\nSORTED (every " + step + " word): ");
        for (int i = 0; i < allWords.length; i++) {
            if (i%step == 0)
                System.out.print(allWords[i] + " ");
        }
        System.out.println("\n");


        System.out.println("BINARY SEARCH:");

        // Run timingCount loops for countWordsInSorted
        // for the first loop, output the count for each word

        for (int j = 0; j < timingCount; j++) {
            for (int i = 0; i < queryWords.length; i++) {

                /*
                * ========================================================================
                *   START: TODO #5
                */
                binarySearch(allWords,queryWords[i],0,queryWords.length-1);
                int count = countWordsInSorted(allWords,queryWords[i]);
                /*
                *   END: TODO #5
                * ========================================================================
                */

                // For the first one, print out the value
                if (j == 0)
                    System.out.println(queryWords[i] + ":" + count);
                }
        }

        // Output how long the searches took, for how many searches
        // (remember: searches = timingcount * the number of words searched)

        // Record the current time
        long t3 = (new Date()).getTime();

        long timeToSeachBinary = t3 - t2;
        System.out.printf("%d ms for %d searches, %f ms per search\n", timeToSeachBinary, searchCount, timeToSeachBinary*1.0f/searchCount);
    }


}
