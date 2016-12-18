# Amazing Battleship!

## How to run

To run a simple test:

```
battleship
```

To run your own script:

```
battleship input.txt
```

To run your own script and save it in a file:

```
battleship input.txt output.txt
```

## Script

Inline comments are supported in script. The application also skips blank lines and useless spaces.

Input:
```
  10                      // Size of the board is 10x10
  (0, 0, N) (9, 2, E)     // 2 ships in different locations
  (0, 0) MRMLMM           // move/rotate the ship located at (0, 0)
  (9, 2)                  // shoot at (9, 2) and sink the ship if there is one
```

Output:
```
  (1, 3, N)
  (9, 2, E) SUNK
```
