#! /usr/local/bin/groovy

// script parameters
def shuffleToken() {
  def userName = 'jenkins'
  
  /*def user = User.get(userName, false)
  def apiTokenProperty = user.getProperty(ApiTokenProperty.class)
  def result = apiTokenProperty.tokenStore.generateNewToken(tokenName)
  user.save()*/
  def result = giveRandom() + userName + giveRandom()
  println result
}
def giveRandom() {
  return Math.abs(new Random().nextInt() % ([1000000] - [500000])) + [500000]
}
return this
