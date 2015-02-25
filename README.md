# Stack-2D
An esoteric programming language based on a stack based structure and a 2D map of commands

# Documentation

note a function with arity 3 will pop values in this order: c, b, a.

Character |  Arity | Description
:--------:|:------:|------------
+         |  2     | returns a + b
-         |  2     | returns a - b
/         |  2     | returns a / b
*         |  2     | returns a * b
%         |  2     | returns a % b
^         |  2     | returns a bitwise-xor ^ b
&         |  2     | returns a bitwise-and b
\\        |  2     | returns a bitwise-or b
=         |  2     | returns 1 if a == b else 0
G         |  2     | returns 1 if a > b else 0
g         |  2     | returns 1 if a >= b else 0
L         |  2     | returns 1 if a < b else 0
l         |  2     | returns 1 if a <= b else 0
~         |  1     | returns the complement of a
\|        |  1     | switches the direction to up if a is a non-empty string, a true boolean, or a non-zero integer
$         |  0     | duplicates the top element of the stack
p         |  1     | prints a to output
P         | all    | pops all of the stack and prints it
s         |  0     | starting point of all programs. starts going right
t         |  2     | teleports pointer to (a, b)
>         |  0     | changes pointer direction to right
<         |  0     | changes pointer direction to left
^         |  0     | changes pointer direction to up
v         |  0     | changes pointer direction to down
.         |  2     | method call (will be changed soon)
,         |  0     | method return
"         |  0     | enters string input mode. all of the following characters will be pushed to stack in a single string
#         |  0     | trampoline: jumps over the next cell