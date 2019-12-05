import hudson.FilePath
final GROOVY_SCRIPT = "../script.groovy"

evaluate(new FilePath(build.workspace, GROOVY_SCRIPT).read().text)
