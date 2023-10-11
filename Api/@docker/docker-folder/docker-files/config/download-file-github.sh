##!/usr/bin/env bash
#set -e

GITHUB_TOKEN=$1
REPO="maxxsoft-tecnologia/rural-cloud"
FILE=$2
VERSION=$3

echo "começando a executar o script"
echo "token do github => "$GITHUB_TOKEN
echo "file => "$FILE
echo "VERSION => "$VERSION

wget --version

curl --version

ls

echo "#vamos imprimir o jq"
./jq-linux64 --version
echo "#vamos imprimir o jq"


wget --show-progress -q --auth-no-challenge --header='Accept:application/octet-stream' \
  https://$GITHUB_TOKEN:@api.github.com/repos/$REPO/releases/assets/`curl -H "Authorization: token $GITHUB_TOKEN" -H "Accept: application/vnd.github.v3.raw"  -s https://api.github.com/repos/$REPO/releases | ./jq-linux64 ". | map(select(.tag_name == \"$VERSION\"))[0].assets | map(select(.name == \"$FILE\"))[0].id"` \
  -O ./$FILE
