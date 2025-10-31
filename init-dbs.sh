#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    DO \$\$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'cartDb') THEN
            CREATE DATABASE "cartDb";
        END IF;
        IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'userDb') THEN
            CREATE DATABASE "userDb";
        END IF;
        IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'productDb') THEN
            CREATE DATABASE "productDb";
        END IF;
    END
    \$\$;
EOSQL
