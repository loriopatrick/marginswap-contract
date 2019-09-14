DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

PSQL="docker run --rm -i --net=host postgres psql"

DB_NAME="daidrop"
SQL_STR="postgres://postgres@localhost:5432"
echo "drop database $DB_NAME;" | $PSQL "$SQL_STR/postgres"
echo "create database $DB_NAME;" | $PSQL "$SQL_STR/postgres"

cat "$DIR/../src/main/resources/database/tables.sql" | $PSQL "$SQL_STR/$DB_NAME"
