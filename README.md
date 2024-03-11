# SpringBoot Web Postgres Docker 
The project Expose environment variable 


# Install Vault 

## Install vault 

````sh
 helm install vault hashicorp/vault \
    --set "server.dev.enabled=true"
````
## Enable k8s auth method authentication
### connect to vault
````sh
kubectl exec -ti vault-0 -- sh 
````
````sh
vault auth enable kubernetes
````
### Configure kubernetes Login
```sh
vault write auth/kubernetes/config \
token_reviewer_jwt="$(cat /var/run/secrets/kubernetes.io/serviceaccount/token)" \
kubernetes_host="https://$KUBERNETES_PORT_443_TCP_ADDR:443" \
kubernetes_ca_cert=@/var/run/secrets/kubernetes.io/serviceaccount/ca.crt
```
### Create Role **app** and assign to policy **app**
````sh
vault write auth/kubernetes/role/app \
bound_service_account_names=springboot-web-postgres-sa \
bound_service_account_namespaces=default \
policies=app \
ttl=24h
````
### Create Policies
```sh 
vault policy write app - <<EOF 
path "secrets/database/credentials" {
    capabilities = ["read"]
}
EOF
```
### Enable path secrets/database
````sh 
vault secrets enable -path=secrets/database kv
````
### Create credentials
````sh
vault kv put secrets/database/credentials user=<user> password=<password> \
url=<url>
````