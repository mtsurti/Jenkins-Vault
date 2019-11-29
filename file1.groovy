#! /usr/local/bin/groovy

def sayHello {
  println 'Hello'
}

def getAllEnv() {
  env = System.getenv()
  env.each
    println it
}
