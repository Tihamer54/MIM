# MIM
Most Interesting Machine (hopefully an improvement on Doug Lenat's AM)

Many years ago, Doug Lenat wrote a LISP program that replicated and evolved, and tried to find truth about the world of Mathematics using random mutations.
It discovered prime numbers and Goldbach's conjecture. But it depended on the random intersections of LISP functions with mathematical ideas that Lenat pruned and renamed.
IMHO, AM relied too much on human evolutinary selection *after* replication and mutation.  
What MIM is trying to explore is whether early intervention by a Software Engineer can make the mutation smarter.
The toy problem it explores is: Why is a number interesting?
More generally, what kind of abstract representation of numbers and exhaustive search of a rule characterization n-space 
is needed in order for a program to find things like the taxicab number 1729 = Ta(2) = 1^3 + 12^3 = 9^3 + 10^3.

There are lots of exploratory code in other areas in this project, but the important classes are MainRunner and TimeLord.

MainRunner uses JEL to evaluate the generated rules with possibly interesting numbers.
Unfortunately, JEL has not been mavenized yet (on my TODO list), and so you may need to manually import it
from https://mvnrepository.com/artifact/org.gnu/jel and then add it to your .m2 directory 
(e.g. for Windows: C:\Users\yourusername\.m2\repository\org\gnu\jel\2.0.1).
Alternatively, you might want to get the latest and greatest (2.1.1 as of this writing; from https://www.gnu.org/software/jel/), 
compile it into your own jar file, and then manually add it to your .m2 directory.

