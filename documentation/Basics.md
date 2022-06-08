# Basics knowledge for developing

## Git Usage

There are two "main" branches for the project:

- __master__
- __unstable__

The __master__ represents the released stable version.
The other one, namely __unstable__, represents the version which is under production.

When you are developing new features, doing bugfixes, god forbid *refactoring*,
then you create a new branch from the last __unstable__ commit, you push to cloud if you want, not necessary,
and in the end merge to the __unstable__ branch.

It looks like this using the terminal:

``` bash
git checkout unstable # Goes to the unstable branch 
git checkout -b bugFixes # Creates and checks out a branch named bugFixes
git checkout unstable # Back to unstable
git merge bugFixes # Merges bugFixes to unstable
```

### Important notes

- Commit your stuff, especially before merge
- Commit only necessary files
- Be carefull when resolving merge clashes
- Check if unstable got updated merge with your current branch
- If you do not want to use the terminal commands learn vscode git gui or github desktop gui
- Always check which branch are you on before starting to work

## Coding

Always use __UpperCase__ for class names, camelCase for everything else.

Example:

```Java
public class Counter extends BukkitRunnable { // Uppercase for class names

    int scoreCounter = 0; // camelCase member variable

    private void increaseScore() { // camelCase member function
        this.scoreCounter++;
    }
}
```

Use specifications for functions if (at least one):

- The name does not explain himself well enough
- It has side effects
- It throws error
- etc.

If you can name a code segment it is a function.
