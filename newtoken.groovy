#! /usr/local/bin/groovy

// script parameters
def shuffleToken() {
  def userName = 'jenkins'
  
  /*def user = User.get(userName, false)
  def apiTokenProperty = user.getProperty(ApiTokenProperty.class)
  def result = apiTokenProperty.tokenStore.generateNewToken(tokenName)
  user.save()*/
  def tokenSeed = giveRandom() + userName + giveRandom()
  def result = tokenSeed.bytes.encodeBase64().toString()
  println result
}
def giveRandom() {
  return Math.abs(new Random().nextInt() % ([1000000] - [500000])) + [500000]
}
return this
