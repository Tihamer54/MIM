# MIM
Most Interesting Machine --hopefully an improvement on Doug Lenat's AM (Automated Mathematician) 
See https://www.researchgate.net/publication/221605660_Why_AM_and_Eurisko_Appear_to_Work

Many years ago, Doug Lenat wrote a LISP program that replicated and evolved, and tried to find truth about the world of Mathematics using random mutations.
It discovered prime numbers and Goldbach's conjecture. But it depended on the random intersections of LISP functions with mathematical ideas that Lenat pruned and renamed.
IMHO, AM relied too much on human evolutinary selection *after* replication and mutation.  
What MIM is trying to explore is whether early intervention by a Software Engineer can make an exhaustive searcher smarter, 
perhaps by unfolding an algorithm in a way analogous to the way L-Systems generate Fibbonacci words.
(TODO: Possibly map functions/variables to nodes and branches of an L-system graph; see LSystemApplet.java)

The toy problem it explores is: Why is a number on a digital clock is interesting?
More generally, what kind of abstract representation of numbers and expressions can be used in an exhaustive search of a rule characterization n-space 
for a program to find things like the taxicab number 1729 = Ta(2) = 1^3 + 12^3 = 9^3 + 10^3?

At this point, the getMetaRules() function can exhaustively generate three different types of rules:
1. singles (e.g the four-digit number isPrime(), or isPerfectCube(), etc.)
2. both (e.g. both the hours and the minutes are true for the same function such as isPrime(), or isPerfectCube(), etc.)
3. hoursminutesrelations (e.g. hoursOrMinutes <operation> [1-12] == minutesOrHours; e.g. (hours * 3 == minutes).)

TODO: Number of rules fired per number is ok for first cut, but some rules are cooler than others.
      Give weights to more elegant rules. What makes something elegant? Simple, beautiful (symetric), patterns, maximally unfolding?
      Don't be boring.  +1 is ok, but not too often. +7 is boring pretty quick. 
      Cubes and cube roots are cooler than squares, square are cooler than unlike multiplication, multiplication is cooler than addition.
TODO: Find a representation that handles all three of the above types (and more).
      I.e. look at n-fold splits from 0 to length n, exhaustively do functions and relations between each group (n X n).

There is lots of exploratory code in other areas in this project (which can be safely ignored, as this is a very crufty research project for other random ideas), but the important classes are MainRunner and TimeLord.

MainRunner uses JEL to evaluate the generated rules for evaluating possibly interesting numbers.
Unfortunately, JEL has not been mavenized yet (on my TODO list), and so you may need to manually import it
from https://mvnrepository.com/artifact/org.gnu/jel and then add it to your .m2 directory 
(e.g. for Windows: C:\Users\yourusername\.m2\repository\org\gnu\jel\2.0.1).
Alternatively, you might want to get the latest and greatest (2.1.1 as of this writing; from https://www.gnu.org/software/jel/), 
compile it into your own jar file, and then manually add it to your .m2 directory.
