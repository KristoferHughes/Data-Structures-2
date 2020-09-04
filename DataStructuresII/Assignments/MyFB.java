package algs34;

import java.util.HashMap;
import java.util.HashSet;

import java.util.Set;
import java.util.*;
import stdlib.*;

//Kristofer Hughes
//CSC 301
//Professor Zaretsky
/*
 * Complete the methods of MyFB so that it works.
 * Each method must work in the worst case time given below (assuming uniform hashing and ignoring string lengths):
 *
 *   addPerson must take constant time
 *
 *   addFriendship must take constant time
 *
 *   queryFriendship must take constant time
 *
 *   removeFriendship should take constant time
 *
 *   lookupFriends should take time proportional to the number of
 *   friends that the named person has (or less)
 *
 * Your solution should use a single field of type
 *
 *   HashMap<Person,HashSet<Person>>
 *
 * You will need to add hashCode and equals methods to the Person class.
 * To increase the avalance effect, consider a hash function that utilizes 
 * primes, for example: 
 * 
 * public int hashCode() {
 * 		int result = 17;
 * 		result = 37*result + var1;
 * 		result = 37*result + var2;
 * 		result = 37*result + var3;
 * 		return result;
 * 	}
 *
 * Don't change the first line of any method given below.
 * The names and types of the methods should not change.
 * I will run you MyFB class using my own main method.
 *
 * You can add more classes too.  Just put them all in this file, inside MyFB.
 * Any classes you add should be "static" and not "public".
 * You can import/use any file in the java APIs.
 * You can import/use any file in the algs packages.
 *
 * You do not need to implement a hash table for this assignment.
 * You can use java.util.HashMap, java.util.HashSet, or the implementations in algs34.
 *
 * You do not need to implement an iterable object.
 * You can use algs13.Queue.
 * Also note that all of the Map implementations we have looked at create an iterable
 *   object via the keys() method.
 *
 *
 * Here is an example session:
 *
 *   INPUT           		OUTPUT
 *
 *   p john 8
 *   p mike 7
 *   p sara 9
 *   p jane 5
 *   f mike 7 jane 5
 *   f mike 7 sara 9
 *   f jane 5 john 8
 *   l jane 5          		mike 7 john 8
 *   l john 8          		jane 5
 *   u mike 7 jane 5
 *   l jane 5          		john 8
 *   q mike 7 sara 9     	yes
 *   q sara 9 mike 7     	yes
 *   q sara 9 mike 23    	no
 *   q sara 9 sara 9     	no
 *
 * Note that friendship is symmetric and irreflexive.
 * So if a/b are friends, then so are b/a.
 * And no one is their own friend.
 *
 * Put the following in a file "input.txt" to run this test:
p john 8
p mike 7
p sara 9
p jane 5
f mike 7 jane 5
f mike 7 sara 9
f jane 5 john 8
l jane 5
l john 8
u mike 7 jane 5
l jane 5
q mike 7 sara 9
q sara 9 mike 7
q sara 9 mike 23
q sara 9 sara 9
 */
public class MyFB {
	public static boolean initiator = true;
	Scanner in=new Scanner(System.in);
	
    public static Person makePerson(In in) {
            try {
                    String name = in.readString();
                    int age = in.readInt();
                    return makePerson(name, age);
            } catch (java.util.InputMismatchException e) {
                    StdOut.println("Input format error");
                    return null;
            }
    }

    public static Person makePerson(String name, int age) {
            return new Person(name, age);
    }


    static class Person {
            String name;
            int age;

            public Person(String name, int age) {
                    this.name = name;
                    this.age = age;
            }

            public String toString() {
                    return name + " " + age;
            }

            @Override
            public boolean equals(Object obj) {
                    if (this == obj)
                            return true;
                    if (obj == null)
                            return false;
                    if (getClass() != obj.getClass())
                            return false;
                    Person other = (Person) obj;
                    if (age != other.age)
                            return false;
                    if (name == null) {
                            if (other.name != null)
                                    return false;
                    } else if (!name.equals(other.name))
                            return false;
                    return true;
            }

            @Override
            public int hashCode() {
                    final int prime = 31;
                    int result = 1;
                    result = prime * result + age;
                    result = prime * result + ((name == null) ? 0 : name.hashCode());
                    return result;
            }

    }

    HashMap<Person, HashSet<Person>> map = new HashMap<>();

    // a person "exists" after they are added using addPerson
    // addPerson does nothing if p already exists
    public void addPerson(Person p) {
            if (initiator) {
                    StdOut.format("P %s: %s\n", p, map);
            }
            if (!map.containsKey(p)) {
                    map.put(p, new HashSet<Person>());
            }
    }

    // addFriendship does nothing if p1 and p2 are already friends or if one does
    // not exist
    public void addFriendship(Person p1, Person p2) {
            if (initiator) {
                    StdOut.format("F %s %s: %s\n", p1, p2, map);
            }
            if (!p1.equals(p2) && map.containsKey(p1) && map.containsKey(p2)) {
                    map.get(p1).add(p2);
                    map.get(p2).add(p1);
            }
    }

    // removeFriendship does nothing if p1 and p2 are not friends or if one does not
    // exist
    public void removeFriendship(Person p1, Person p2) {
            if (initiator) {
                    StdOut.format("U %s %s: %s\n", p1, p2, map);
            }
            if (!p1.equals(p2) && map.containsKey(p1) && map.containsKey(p2)) {
                    map.get(p1).remove(p2);
                    map.get(p2).remove(p1);
            }
    }

    // queryFriendship returns false if p1 and p2 are not friends or if one does not
    // exist
    public boolean queryFriendship(Person p1, Person p2) {
            if (initiator) {
                    StdOut.format("Q %s %s: ", p1, p2);
            }
            final Set<Person> s1 = map.get(p1);
            final Set<Person> s2 = map.get(p2);
            return null != s1 && null != s2 && s1.contains(p2) && s2.contains(p1);
    }

    // lookupFriends returns null or empty iterable if p does not exists
    public Iterable<Person> lookupFriends(Person p) {
            if (initiator) {
                    StdOut.format("L %s:", p);
            }
            return map.get(p);
    }


	public static void hashTest () {
		Trace.showBuiltInObjects (true);
		//Trace.run ();
		Person x = makePerson ("bob", 27);
		Person y = makePerson ("bob", 27);
		Boolean eq = x.equals(y);
		Boolean hash = x.hashCode() == y.hashCode();
		StdOut.println(x.toString() + " ("+x.hashCode()+") == " + y.toString() + " ("+y.hashCode()+")");
		StdOut.println( "  Equal Test: " + (eq ? "equal" : "not equal") );
		StdOut.println( "  Hash Test: " + (hash ? "equal" : "not equal") );

		x = makePerson ("mike", 27);
		y = makePerson ("john", 29);
		eq = x.equals(y);
		hash = x.hashCode() == y.hashCode();
		StdOut.println(x.toString() + " ("+x.hashCode()+") == " + y.toString() + " ("+y.hashCode()+")");
		StdOut.println( "  Equal Test: " + (eq ? "equal" : "not equal") );
		StdOut.println( "  Hash Test: " + (hash ? "equal" : "not equal") );
	}
	
	public static void main (String[] args) {
		Trace.showBuiltInObjects (true);
		//Trace.run ();
		
		hashTest();
		
		/*
		 * The first line below causes "in" to read from the console. You can
		 * change this to read from a file, by using something like the line
		 * below, where "input.txt" is a file you create in your eclipse
		 * workspace. The file should be in the same folder that contains "src"
		 * "bin" and "lib":
		 */

		//In[] inputFiles = new In[] { new In () }; // from console
		In[] inputFiles = new In[] { new In ("input.txt") }; // from file
		//In[] inputFiles = new In[] { new In ("input.txt"), new In () }; // from file, then console
		
		MyFB fb = new MyFB ();
		StdOut.println ("\n");
		StdOut.println ("Enter one of the following:");
		StdOut.println ("  P name age -- add person");
		StdOut.println ("  F name1 age1 name2 age2 -- add friendship");
		StdOut.println ("  U name1 age1 name2 age2 -- remove friendship");
		StdOut.println ("  Q name1 age1 name2 age2 -- query friendship");
		StdOut.println ("  L name age -- lookup friends");
		StdOut.println ("  R -- restart");
		StdOut.println ("  X -- exit\n\n");
		
		for (In in : inputFiles) {
			while (in.hasNextLine ()) {
				String action;
				try {
					action = in.readString ();
				} catch (NoSuchElementException e) { continue; }

				//StdOut.println (action); // while debugging, print debugging info here!  You can print maps, sets, lists.
				
				switch (action) {
				case "P":
				case "p": {
					Person p = makePerson (in);
					fb.addPerson (p);
					break;
				}
				case "F":
				case "f": {
					Person p1 = makePerson (in);
					Person p2 = makePerson (in);
					fb.addFriendship (p1, p2);
					break;
				}
				case "U":
				case "u": {
					Person p1 = makePerson (in);
					Person p2 = makePerson (in);
					fb.removeFriendship (p1, p2);
					//Trace.draw ();
					break;
				}
				case "Q":
				case "q": {
					Person p1 = makePerson (in);
					Person p2 = makePerson (in);
					boolean result = fb.queryFriendship (p1, p2);
					StdOut.println (result ? "Yes" : "No");
					break;
				}
				case "L":
				case "l": {
					Person p = makePerson (in);
					Iterable<Person> result = fb.lookupFriends (p);
					if (result != null) {
						for (Person friend : result) {
							StdOut.print (friend);
							StdOut.print (" ");
						}
					}
					StdOut.println ();
					break;
				}
				case "R":
				case "r":
					fb = new MyFB ();
					break;
				case "X":
				case "x":
					System.exit (0);
				}
			}
		}
	}
}
