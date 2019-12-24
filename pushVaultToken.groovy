#!/usr/local/bin/groovy
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.*
import com.datapipe.jenkins.vault.credentials.*
import hudson.util.Secret

def updateVaultToken(String authToken, String vaultToken, String vaultAddress) {
    if (authToken != null && authToken.length() > 0 &&
       vaultToken != null && vaultToken.length() > 0 &&
       vaultAddress != null && vaultAddress.length() > 0) { 
        //sh "curl --header \'X-Vault-Token: \"${vaultToken}\"\' --header \'X-Vault-Namespace: authtoken/\' --header \'Content-Type: application/json\' -X POST -d \'{\"auth-token\":\"${authToken}\"}\' ${vaultAddress}/v1/kv/my-secret"
        //sh "curl --header \'X-Vault-Token: \"${vaultToken}\"\' --header \'Content-Type: application/json\' -X POST -d \'{\"auth-token\":\"${authToken}\"}\' ${vaultAddress}/v1/kv/vaulttoken"
        sh "curl --header \'X-Vault-Token: \"${vaultToken}\"\' --request GET ${vaultAddress}/v1/kv/vaulttoken | jq"    

  /*      def secrets = [
//        [path: 'kv/vaulttoken', secretValues: [
//            [envVar: 'authtoken', vaultKey: "${authToken}"]]],
        [path: 'kv/vaulttoken', secretValues: [
            [vaultKey: 'authtoken']]]
        ]    
 
    // optional configuration, if you do not provide this the next higher configuration
    // (e.g. folder or global) will be used
        def configuration = [vaultUrl: "${vaultAddress}",
                         vaultCredentialId: 'auth-writer']
    // inside this block your credentials will be available as env variables
//    withVault([configuration: configuration, vaultSecrets: secrets]) {
//        sh 'echo $authtoken'
//    }
        
      withCredentials([string(credentialsId: 'auth-writer', variable: 'ROLE_ID'),string(credentialsId: 'VAULTTOKEN', variable: 'VAULT_TOKEN')]) {
    }*/
    }
  }
return this
