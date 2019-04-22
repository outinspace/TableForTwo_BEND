#!/bin/bash
set -f
echo "$SSH_PRIVATE_KEY" > aws.pem
chmod 600 aws.pem
mkdir ~/.ssh
ssh-keyscan $DEPLOY_SERVER >> ~/.ssh/known_hosts

mvn -Dmaven.test.skip=true package

ssh -i aws.pem ubuntu@$DEPLOY_SERVER "sudo systemctl stop reservation-application"

chmod 777 target/reservation-application.jar

scp -i aws.pem target/reservation-application.jar ubuntu@$DEPLOY_SERVER:/var/www/java
ssh -i aws.pem ubuntu@$DEPLOY_SERVER "sudo systemctl start reservation-application"