# Todo Tree VSCode Extension

I use Todo Tree, a vscode extension that allows you to see all the TODOs in your code. It is very useful to keep track of what remains to be done. You will find the Tags that I use, and my configuration herewith. You can copy this in the settings.json file of the .vscode folder of your project or via the command palette menu.

```json
   // TODO TREE TAGS CONFIG
    "todo-tree.general.tags": [
        "BUG",
        "HACK",
        "FIXME",
        "TODO_LOW",
        "TODO",
        "TODO_HIGH",
        "TODO_END",
        "XXX",
        "[ ]",
        "[x]",
        "DEBUG",
        "REVIEW",
        "TOTEST",
        "TO_UPDATE",
        "TOCONFIG",
        "FIXME",
        "TOASK",
        "TODISCUSS",
        "NOTE",
        "TONOTE",
        "TOCHECK",
        "IMPORTANT",
        "WIP",
        "TOFINISH",
        "REFACTOR"
    ],
    // https://microsoft.github.io/vscode-codicons/dist/codicon.html
    // https://en.wikipedia.org/wiki/Web_colors
    "todo-tree.highlights.customHighlight": {
        "TOTEST": {
            "icon": "beaker",
            "iconColour": "lime",
        },
        "TODO": {
            "icon": "code",
            "iconColour": "RoyalBlue",
            "type": "line"
        },
        "TODO_HIGH": {
            "foreground": "white",
            "background": "red",
            "icon": "flame",
            "iconColour": "red",
            "gutterIcon": true
        },
        "TODO_LOW": {
            "icon": "fold-down",
            "iconColour": "LightSkyBlue"
        },
        "TODO_END": {
            "icon": "fold-down",
            "iconColour": "DodgerBlue"
        },
        "FIXME": {
            "foreground": "white",
            "background": "red",
            "icon": "flame",
            "iconColour": "red",
            "gutterIcon": true
        },
        "IMPORTANT": {
            "foreground": "white",
            "background": "red",
            "icon": "alert",
            "iconColour": "white",
            "gutterIcon": true
        },
        "TONOTE": {
            "icon": "note"
        },
        "TOASK": {
            "icon": "comment",
        },
        "TODISCUSS": {
            "icon": "comment-discussion",
            "iconColour": "HotPink",
            "foreground": "black",
            "background": "LightPink",
        },
        "TOCHECK": {
            "icon": "unverified",
            "iconColour": "Goldenrod",
            "foreground": "black",
            "background": "Goldenrod",
            "gutterIcon": true
        }
    },
    "todo-tree.highlights.useColourScheme": true,
    "todo-tree.tree.buttons.groupBySubTag": true,
    // END OF TODO TREE TAGS CONFIG
```
