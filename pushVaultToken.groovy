#!/usr/local/bin/groovy
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.*
import com.datapipe.jenkins.vault.credentials.*
import hudson.util.Secret

def updateVaultToken(String authToken, String vaultToken, String vaultAddress) {
    if (authToken != null && authToken.length() > 0 &&
       vaultToken != null && vaultToken.length() > 0 &&
       vaultAddress != null && vaultAddress.length() > 0) { 
        sh "curl --header \'X-Vault-Token: ${vaultToken}' --header \'Content-Type: application/json\' -X POST -d \'{\"authtoken\":\"${authToken}\"}\' ${vaultAddress}/v1/kv/vaulttoken"
        sh "curl --header \'X-Vault-Token: ${vaultToken}' --request GET ${vaultAddress}/v1/kv/vaulttoken | jq -r \".data.authtoken\""
    }
  }
return this
