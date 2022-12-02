# sauce-raw-selenium

## Overview

5 Different cases:
1. **Case 1 - Automate Purchase Process**
1. **Case 2 - Verify error messages for mandatory fields** 
1. **Case 3 - Rich Text Editor**
1. **Case 4 - iFrame and tab handling**
1. **Case 5 - REST API testing**

There's a 1 to 1 mapping between the cases and Test Classes.

The classes are intentionally separated from each other.
Each case was coded with different kind of abstractions (patterns, extensions, JUnit storage etc.).
Each kind of abstraction has its own pros and cons.

## How to run

Before you run anything please read this readme.

You can use surefire:

```shell
mvn clean test
```

Or you can 'manually' run test classes/methods in IntelliJ IDEA etc.

## Caveats

There must be **ONE TEST ERROR.** (for screenshot on failure demonstration)

Artifacts will be (intentionally) **written to** `./artifact` not under `./target/artifact`.

There are **MORE TESTS THAN CASES (5)**!
*(Some tests are intentionally repeated, to see whether a test can handle different non-deterministic popups.
While some tests are not part of the cases, but were made to cause errors and trigger screenshot taking code.)*

No requirement for webdriver option configurability was specified,
so all tests run with a basic chrome (interactive, no fullscreen, no sandbox, no magic options).

No requirement for resource filtering or maven profiles was specified,
so there are no profiles.

## Cases using different patterns

### Case 1 – Bot Pattern

Bot is the driver owner, who accepts Functor like objects and executes them.

There are different subjects:
- element
- page
- storage

Each capable of:
- acting: non-idempotent procedures
- seeing: idempotent procedures (well... idempotent in the sense that it won't fire events)
- getting: functions (they are really dangerous because they return possibly stale data from the dom etc.)

```java
bot.act(PageActs.open("some url"));
bot.act(By.cssSelector(".foo"), ElementActs.click());
```

Pros:
- actions, and subjects are decoupled
- small 'technical' interfaces (aka you don't have interfaces with 100 methods like 'click', 'getText')
- you can pick and choose the implementation (lik click via js, click via actions, click via WebElement)

Cons:
- less help from autocomplete (interfaces don't have regular methods like 'click')
- ton of classes and static factory methods
- no hand holding (you can attempt to click with anything, just like with `WebElement`)

### Case 2 - Widgets/Wrappers

Actual wrapper classes which directly model something on the UI.

Like Inputs, Buttons etc.

```java
new Input().setValue("some value")
```

I did something questionable to handle composite wrappers.

Instead of getting fields and assigning them to local variables:
```java
final var dataTable = new DataTable();
final var topPaginator = dataTable.getTopPaginator();
topPaginator.next();
```

The composite accepts `Consumer`s, like this:
```java
new DataTable()
        .paginatorTop(paginator -> {
            paginator.mustBeAt(1);
            paginator.goNext();
        })
        .body(body -> {
            dataTable.mustHaveRows(2);
            dataTable.mustHaveColumns(4);
        })
```

(Spring Security configuration works a bit like this, where it accepts `Consumer`s for its `Builder`s.)

Pros:
- help from autocomplete
- 1 to 1 matching between test wrappers and React/Angular/Whatever components is possible
- lot of hand holding (each wrapper has its own methods)

Cons:
- rigid
- huge interfaces
- you cannot pick and choose how to do things (like js or vanilla)

### Case 3 - Factory

Rich Text Editor (CKEditor 4).

Basically I wanted to create a JS and a Vanilla CKEditor implementation.

Both of these conform to the same contract/interface but one of them uses the questionable WebDriver protocol,
while the other the even more questionable vanilla JS spaghetti of mine.

Aka, basically:
```java
final Doodad doodadJs = DoodadFactory.doodadWithJsGlitches();
final Doodad doodadWebDriverOnly = DoodadFactory.doodadWithSeleniumBugs();
```

Pros:
- instantiation code is moved to factory/factory methods
- you are depending on interfaces, not the direct classes

Cons:
- doesn't solve anything beside creation/instantiation

The only down-side of this is that...it does not solve anything.
This just moves the instantiation code to another place.

### Case 4 - Step/Task (aka elementary, and composite)

Basically you have an actor. It can do two things:
- Steps: some effect
- Task: some effect or calling Steps

I mean basically Task is 'Composition of Steps'.

This way you can organically refactor your code.

Example: imagine, you start out by creating Steps.
You start to see that a certain sequence of Steps occurs frequently.

You jam that sequence into a Task, and use the Task instead of the elementary Steps.

```java
new Actor()
        .chain(Url.visit("http://what.ever"))
        .chain(Url.mustBe("http://what.ever")); 

new Actor()
        .chain(WhatEverTasks.openThenArrive());
```

Pros:
- it's non-rigid
- lends itself well to refactoring
- less initial coding overhead

Cons:
- less help from autocomplete (interfaces don't have regular methods like 'click')
- ton of classes and static factory methods
- no hand holding (you can attempt to click with anything, just like with `WebElement`)

### Case 5 - Not Selenium

Case 5 has nothing to do with Selenium or UI testing.
It is simply a GET + json parsing with a bit of hamcrest.

## Details

### Screenshot, page source

You can wire in this feature in a number of ways.

One way is to treat it as part of the test.
This is highlighted in `AutomatePurchaseProcessTest.java`, 
where the screenshot and page source acquisition is done via the `Observer` pattern.

On other way is `FormValidationTest.java`, where an JUnit extension was used for screenshot and page source acquisition.

Be careful with JUnit's `TestWatcher` though.
[It goes off AFTER the ExtensionContext is killed](https://junit.org/junit5/docs/5.5.1/api/org/junit/jupiter/api/extension/TestWatcher.html).


### Extension context and json data

In `AutomatePurchaseProcessTest.java`, the json IO and parsing is done via extensions:
- `CredentialStore.jva`
- `ProductStore.java`

I simply added to the root ExtensionContext the data (parsed from json) on demand.
This is not safe, and probably context (pico, spring) would be better, but things are already complicated enough.

I mean if you are a 'thread per class' person,
then putting the immutable json data into the class level context with a before all callback might be good.

On the other hand if you are 'thread per method' person, then it might be better to derp aroing with testplan listener.

I personally don't know what's the right answer, so I just shove things into the root context like a madman.

### Driver lifecycle management

Well... some keep the drivers alive and use a [threadId-keyed synchonized map of VM shutdown hooks](https://selenide.org/) to close them.

Others make them closeable (JUnit store closeable) and put it into the context.


I tried to keep it simple and manage it simply via callbacks on the test class itself.

Just for quick demonstration there's an example of AutoCloseable Resource + JUnit Store, in `FormValidationTest.java`.
In that case we use the lowest ExtensionContext (the actual test instance), and put the driver into it, 
with an AutoCloseable wrapper.

Although... be aware that messing with Extension Context hierarchies and Parameter Resolvers can break things.
I mean we are talking about untyped shared state and arbitrary call order 
(end-user can reorder the extensions' call order by mistake).

### Logging

Logback was used (but hidden under slf4j).
To mess with appenders, or format, please use `./test/resources/logback.xml`.

You can spray-n-pray log in the test code, like in `CKEditorInstanceVanilla.java`.

Or, you can overcomplicate things like in `IframeAndTabHandlingTest.java`, 
where we made logging part of the test (intentionally in a simplistic way).

In this case though, I'd much rather use a 3rd party library instead of self-coding the whole thing.
Creating logs which are verbose enough, but still small is hard.

There's one tiny thing though. In my experience logs are sometimes used to track state and action sequences.
Speaking of sequences... you have to admit that Cypress' chains are kind of cool, because they provide you with a new kind of 'stack trace'.
I mean most of the time you are not concerned with the stack, but with sequences of already popped invocations.
Aka a 'been there done that stack'.
The stack trace cannot capture this information,
but in case of Cypress' chains, the semantic connection is expressed in code.
Aka this connection is not just an ordering of method calls,
but the semantical link between these objects is code-ified too!

The most naive approach would be something along the lines of `Chain of Responsibility`.
So you iterate forwards for execution, BUT you still have the opportunity at any point, 
to go backwards for failure propagation (and even retry forwards).

Or make the chain into an immutable structure, 
and move the iteration and execution responsibilities to somewhere else, 
and keep the history with data structures instead of the stack.

Or messing around with `Memento`.

I personally don't have any cool solutions for this problem.

### Partial function application order

Most of my problems come from partial function application order.

I mean most of the time we have functions like this:
```text
(TheChannel X TheTargetIdentifier X SomeArbitraryArg) -> WhateverResult
```

Aint nobody has time to carry all of this, and every time manually pass it to the functions.

Selenium basically solves this by giving you an instantiatable `WebDriver`,
which let's you call things like this:
```text
(SomeTargetIdentifier X SomeArbitraryArg) -> WhateverResult
```

and even `WebElement` which let's you call things like this:
```text
(SomeArbitraryArg) -> WhateverResult
```

But... you can also choose to invert the order.

I mean what if you create things like the thing below, and you form sequences from them:
```text
(TheTargetIdentifier X SomeArbitraryArg) -> TheChannel -> WhateverResult
```

This is like Selenium's `Action` (like `build` or `perform`).
Cypress also kind of follows this.
Aka you are not directly causing effects, but you are creating 'unfinished functions'.
Someone then comes, and 'finishes the function' then passes it to someone who will actually apply it.

One messed up thing with this, is that when these partial function things start to get thrown around,
you might need to tell whether two of these things are the same or not.
Basically that's the point where I get lost (These Selenium functions are not mathematical functions,
aka if you call the same thing twice with the same arguments you might end up with different results.
Also the arguments are totally stateful, so you cannot compare the args and save the result of the comparation.).

This application order is kind of messy, and not so straightforward in real life.
And when you add statefulness to this you end up with code like mine and your eyes start bleeding.

I don't know the solution.
But the inverted order seems to be better in the long run,
although it is hard to pull off correctly, so I didn't bother with it.