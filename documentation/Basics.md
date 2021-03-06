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

    private void increaseScore() { // camelCase member functionCodeingfects
```

Use specifications for functions if (at least one):

- The name does not explain himself well enough
- It has side effects
- It throws error
- etc.

If you can name a code segment it is a function.

## Documentation

There are two "main" folders for the documentation:

- UML
- Markdown

The UML folder holds the diagrams of the class hierarchy, as well as some sequence diagrams, which simulate the flow of the information.

The Markdown folder is for the written documentation using the markdown syntax.

The structure of that is the following. The entry is the [Documentation](Documentation.md) file.
Here are the basic stuffs about the project, the structure of the source files, a list of modules, etc.

Every module will have it's separate file for their own documentation respecting the source's structure.
