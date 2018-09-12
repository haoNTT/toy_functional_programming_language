# toy_functional_programming_langauge
This is a functional programming project which is designed to emulate the behavior of a standard computer programming language. The course code serves as a demo of the realization of establishing a programming language by coding in functional programming language. Specifically, the toy functional can achieve the following functionalities:

1.Constants

A very basic function for any programming language. Almost any programming language needs constants to represents data in order to operate computations.

2.Names

Just like variables in any programming languages, we need certain names to represents subject that we defined as meaningful in a programming language such that certain computations can be carried out.

3.Quotations

To check is any statement if a quote statement. Note: quote is a specific instruction in this language that assign variable types to variables.

4.Predefined Functions

Function names are just like variable names, they are symbols point to a function.

5.Special Form 'let'

Simulation of programming logic expression if. Since the program is programmed in Clojure, the Details can be found at: https://clojuredocs.org/clojure.core/if

6.Speical Form 'fn'

Similar to 'def' in python which defined a variable as a function name, 'fn' in programming language Clojure serves very similar function. Here in this project, 'fn' is a type checker, much like type in ppython which returns the type name of a variable.

7.Special Form 'closure'

This is a self-defined special form in this programming language. Actually, it is just the function type in ordinary Clojure. Previously, we defined special form ‘fn’ to verify if a statement is a function or not but here in this programming language, the user will input ‘closure’ instead of ‘fn’ to start a new function. The special form ‘fn’ is only used to check once the statement type closure is confirmed.

8.Recursive Calls 

one essential function for any programming language is the capability to deal with loops. There are multiple typee of loops which include while loops and recursive calls. For functional languages like Clojure, the implementation of recursive calls will be much more easier than the implementation of recursions in other types of programming languages.
