# What is this?

This is an example of using an annotation processor
to add a custom static check to a Java codebase.

This README has two parts

1. An overview of what the code in this repo does.
2. A challenge to the users and authors of other build tools.

## Overview

To run the code in this repo, first run

```
java "@bootstrap"
```

(the quotes are only required on Windows)

Followed by

```
java "@build"
```

You should see a failing build with errors like this:

```
dev.mccue.debtbomb.example\src\Example.java:4: error: Debt Bomb! expired=2023-10-05, reason=Example code is silly
public class Example {
       ^
dev.mccue.debtbomb.example\src\Example.java:9: error: Debt Bomb! expired=1000-10-10, reason=Bad method name
    public void f() {
                ^
dev.mccue.debtbomb.example\src\Example.java:9: error: Debt Bomb! expired=2000-01-01, reason=Unix epoch, owner=Nobody, ticket=JIRA-99999
    public void f() {
                ^
3 errors
```

If you go into the code and change the dates on those annotations to dates in the future, the errors should go away.

This is an implementation of the "debt bomb" comment system
from [this reddit post](https://www.reddit.com/r/programming/comments/1q96nar/comment/nystx96/?context=1)
and [repo](https://github.com/jobin-404/debtbomb) by [Jobin Jose](https://github.com/jobin-404).
Instead of using comments, this hooks into the annotation processor system to emit errors at compile time.

You can find the code used to build this in `scripts/Build.java`.

## Challenge

It took me around 2 hours from having read the announcement post
for `debtbomb` to implementing it as an annotation processor.

I _could_ always publish this as a library, but doing so would be a little uncomfortable because:

1. If you read the comments on the reddit post, you will see that people seem to disagree about when such an error should be emitted, whether it should be an error or a warning, and what exact information should be mandatory as opposed to optional.
2. It took around 70 lines of code, total, to implement this static check as an annotation processor. Why bother releasing it as a library when it would be "easy" for people to reimplement and tweak to have the exact semantics they want.

And I think it is a perfect case study of something that
is easy when using the JDK tools directly but which build tools make
"[virtually inaccessible (or at least not easily discoverable) to Java users.](https://www.reddit.com/r/java/comments/1q5lqz7/comment/nyar2vo/?context=3)"

So the challenge is this:

Using your build tool of choice, add both the runtime annotations
and processor to an example build.

You lose points if:

- You need to add a `META-INF/services/javax.annotation.processing.Processor` file (meaning you couldn't figure out how to put the procesor on the `--processor-module-path`)
- You need to add a `module-info.java` to the code with the `Example` class. (Dependencies should be placeable on the `--module-path` even if your code is not.)
- `Example.class` ends up in the same jar as the processor or annotations
- The features you use are undocumented
- You have to make a custom plugin

You can submit by making a PR and putting links below. There are no prizes.