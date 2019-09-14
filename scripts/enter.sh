#!/usr/bin/env bash


SQL_STR="postgres://postgres@localhost:5432"
DB=${1:-daidrop}
docker run --rm -it --net=host postgres psql "$SQL_STR/$DB"
