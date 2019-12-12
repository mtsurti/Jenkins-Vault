#! /usr/local/bin/groovy

def shuffleToken(String userName) {
  def tokenSeed = nextRandomNum() + userName + nextRandomNum()
  def result = tokenSeed.bytes.encodeBase64().toString()
  return result
}
def nextRandomNum() {
  return Math.abs(new Random().nextInt() % ([1000000] - [500000])) + [500000]
}
return this
