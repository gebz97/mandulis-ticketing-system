#!/bin/bash

set -e

source ./.minio_env

# todo: check if container already exists. Meanwhile pipe stop with start :v

docker run -d --name "$CONTAINER_NAME" -p 9000:9000 -p 9001:9001  quay.io/minio/minio server /data --console-address ":9001"
echo "Started container $CONTAINER_NAME"

docker exec -it "$CONTAINER_NAME" mc mb --ignore-existing storage
echo "Storage bucket[name=$BUCKET_NAME] has been setup."



