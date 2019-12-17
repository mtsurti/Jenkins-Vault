#!/usr/local/bin/groovy
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.*
import com.datapipe.jenkins.vault.credentials.*
import hudson.util.Secret

/*def rotateToken() {
        // define the secrets and the env variables
        // engine version can be defined on secret, job, folder or global.
        // the default is engine version 2 unless otherwise specified globally.
        def secrets = [
            [path: 'secret/testing', engineVersion: 2, secretValues: [
                [envVar: 'testing', vaultKey: 'value_one'],
                [envVar: 'testing_again', vaultKey: 'value_two']]],
            [path: 'secret/another_test', engineVersion: 2, secretValues: [
                [vaultKey: 'another_test']]]
        ]

        // optional configuration, if you do not provide this the next higher configuration
        // (e.g. folder or global) will be used
        def configuration = [vaultUrl: 'http://localhost:8200',
                                vaultCredentialId: 'VAULT_TOKEN',
                                engineVersion: 2,
                                timeout: 60]
        // inside this block your credentials will be available as env variables
        withVault([configuration: configuration, vaultSecrets: secrets]) {
            sh 'echo $testing'
            sh 'echo $testing_again'
            sh 'echo $another_test'
            sh 'echo TOKEN=$VAULT_TOKEN'
            sh 'echo ADDR=$VAULT_ADDR'
        }
    //withCredentials([[$class: 'VaultTokenCredentialBinding', addrVariable: 'VAULT_ADDR', credentialsId: 'VAULT_TOKEN', vaultAddr: 'http://localhost:8200']]) {
            // values will be masked
        //   sh 'echo TOKEN=$VAULT_TOKEN'
        //   sh 'echo ADDR=$VAULT_ADDR'
    // }
    }*/

 def updateVaultToken(String authToken, String vaultToken, String vaultAddress) {
    if (authToken != null && authToken.length() > 0 &&
       vaultToken != null && vaultToken.length() > 0 &&
       vaultAddress != null && vaultAddress.length() > 0) {
        println "curl --header \"X-Vault-Token: " + vaultToken + "\" --request GET " + vaultAddress + "/v1/kv/my-secret | jq"
        
        sh "curl --header \"X-Vault-Token: " + vaultToken + "\" --request GET " + vaultAddress + "/v1/kv/my-secret | jq"
    /*def secrets = [
        [path: 'secret/vault-token-id', secretValues: [
            [envVar: 'vault-token-id', vaultKey: authToken]]]
    ]
 
    // optional configuration, if you do not provide this the next higher configuration
    // (e.g. folder or global) will be used
    def configuration = [vaultUrl: "https://" + hostname + ":8200",
                         vaultCredentialId: 'vault-token-id']
    // inside this block your credentials will be available as env variables
    withVault([configuration: configuration, vaultSecrets: secrets]) {
        sh 'echo $vault-token-id'        
        sh '''
          set +x
          export VAULT_ADDR=https://${hostname}:8200
          export VAULT_SKIP_VERIFY=true
          export SECRET_ID=$(./vault write -field=secret_id -f auth/approle/role/vault-token-id/${authToken})
          export VAULT_TOKEN=$(./vault write -field=token auth/approle/login role_id=${roleId} secret_id=${authToken})
        ''' 
    }*/
            
   /*withCredentials([string(credentialsId: 'vault-token-id', variable: 'ROLE_ID'),string(credentialsId: 'vault-token-id', variable: 'VAULT_TOKEN')]) {
        sh '''
          set +x
          export VAULT_ADDR=https://$(hostname):8200
          export VAULT_SKIP_VERIFY=true
          export SECRET_ID=$(./vault write -field=secret_id -f auth/approle/role/vault-token-id/$(authToken))
          export VAULT_TOKEN=$(./vault write -field=token auth/approle/login role_id=${ROLE_ID} secret_id=${authToken})
        '''   
    }*/
//          export SECRET_ID=$(./vault write -field=secret_id -f auth/approle/role/vault-token/secret-id)
        
        /*//sh "export VAULT_ADDR = hostname + ":8200"
        def credentialsStore = jenkins.model.Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()
        def credentials = credentialsStore.getCredentials(Domain.global())
        credentials.each {
           println "Role is " + it.getRoleId()
           if (it.getRoleId() == roleId){
               if ( credentialsStore.updateCredentials(
                 com.cloudbees.plugins.credentials.domains.Domain.global(),
                 it, new VaultAppRoleCredential(it.scope, it.id, it.description, it.roleId, new Secret(token) ) ) ) {
               println roleId + " updated" 
               } 
               else {
                println "ERROR: unable to update " + roleId 
               }
            }
        } 
        //sh "vault login token =" + loginToken  */
    }
  }
return this
