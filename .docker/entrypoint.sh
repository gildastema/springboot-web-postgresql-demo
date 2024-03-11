#!/bin/sh
set -e
mkdir -p $VAULT_SECRET_DIRECTORY
touch  $VAULT_SECRET_PATH
source /vault/secrets/database
java  -Djava.security.egd=file:/dev/./urandom  -jar ./app/app.jar