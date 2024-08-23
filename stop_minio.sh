#!/bin/bash

set -e

source ./.minio_env

docker stop "$CONTAINER_NAME"
docker rm "$CONTAINER_NAME"
echo "Stopped and removed container[name=$CONTAINER_NAME]."
