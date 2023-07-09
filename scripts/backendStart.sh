#!/bin/bash

export $(cat .env | xargs)

docker build -t platform-service:latest --build-arg CONF_MYSQL_URL=$CONF_MYSQL_URL --build-arg CONF_MYSQL_USERNAME=$CONF_MYSQL_USERNAME --build-arg CONF_MYSQL_PASSWORD=$CONF_MYSQL_PASSWORD --build-arg CONF_REDIS_HOST=$CONF_REDIS_HOST --build-arg CONF_REDIS_PORT=$CONF_REDIS_PORT --build-arg CONF_REDIS_PASSWORD=$CONF_REDIS_PASSWORD --build-arg CONF_REDIS_DATABASE=$CONF_REDIS_DATABASE .

docker run -p 10000:10000 platform-service:latest