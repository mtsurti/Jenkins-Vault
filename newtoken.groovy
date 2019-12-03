#! /usr/local/bin/groovy

// script parameters
def shuffleToken() {
  def userName = 'jenkins'
  def tokenName = 'VAULT_TOKEN'
  
  def user = User.get(userName, false)
  def apiTokenProperty = user.getProperty(ApiTokenProperty.class)
  def result = apiTokenProperty.tokenStore.generateNewToken(tokenName)
  user.save()

  println result.plainValue
}
