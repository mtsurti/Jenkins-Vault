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
                  def newAuthToken
                  def user
                  def tokenGenerator
                  def configParser
                  def pushToVault
                  def vaultHost
                  def vaultRole
                  def loginToken 
            }
        try {
          vaultHost = "localhost"
          vaultRole = "auth-token-role"
          loginToken = "s.duaFyWBXizlsIA0TFSrcLQKH"
          wrap([$class: 'BuildUser']) {
            user = "${BUILD_USER}"
          }
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
              println "User is " + user
              newAuthToken = tokenGenerator.shuffleToken(user)
              println newAuthToken
          }
          stage('Update config.xml...'){
              echo 'Updating config.xml file with new token...'
              configParser = load pwd() + '/parseConfig.groovy'  
              configParser.updateAllConfigs(newAuthToken)
          }      
          stage('Deploy to Vault server...') {
              echo 'Deploying to Vault Server...'
              pushToVault = load pwd() + '/pushVaultToken.groovy'  
              pushToVault.updateVaultToken(vaultHost, vaultRole, loginToken, newAuthToken)  
          }
        } 
        catch (e) {
            throw e
        }
      }    
