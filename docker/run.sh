	#!/bin/bash

cd "$(dirname "$0")" # dirname $0 : 쉘 스크립트 파일의 실행 경로

export SERVICES=s3
export TMPDIR=/private$TMPDIR
export DEBUG=0
docker-compose up -d
