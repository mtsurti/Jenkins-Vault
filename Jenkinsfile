#!/usr/local/bin/groovy
import java.io.InputStream;
import java.io.FileInputStream
import java.io.File;
import javax.xml.transform.stream.StreamSource
import groovy.xml.MarkupBuilder
import hudson.model.*
import jenkins.model.Jenkins
import groovy.xml.XmlUtil
import static javax.xml.xpath.XPathConstants.*
import groovy.util.XmlParser

      node {   
            environment {
                  def newToken
                  def tokenGenerator
                  def configParser
                  def pushToVault
            }
        try {
          stage('Checkout') {
              echo 'Checking out scm...'
                checkout([
                      $class: 'GitSCM', 
                      branches: [[name: '*/master']], 
                      doGenerateSubmoduleConfigurations: false,
                      extensions: [],
                      userRemoteConfigs: [[
                            credentialsId: 'd1ea6eb0-c66a-4926-817d-597635de0af7',
                            url: 'https://github.com/mtsurti/Jenkins-Vault.git']]])              
              workspace = pwd() 
          }
          stage('Generate') {
              echo 'Generating new token...'
              tokenGenerator = load pwd() + '/refreshToken.groovy'  
              newToken = tokenGenerator.shuffleToken()
              println newToken
          }
          stage('Update config.xml...'){
              echo 'Updating config.xml file with new token...'
              configParser = load pwd() + '/parseConfig.groovy'  
              configParser.updateAllConfigs(newToken)
          }      
          stage('Deploy to Vault server...') {
              echo 'Deploying to Vault Server...'
              pushToVault = load pwd() + '/pushVaultToken.groovy'  
              //pushToVault.updateVaultToken(vaultRole, newToken)  
          }
        } 
        catch (e) {
            throw e
        }
      }    
  
