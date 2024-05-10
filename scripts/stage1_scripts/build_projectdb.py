"""
Script to build the team5_projectdb database.
Creates tables and imports data from a CSV file into PostgreSQL.
"""

import os
import psycopg2 as psql


# Read password from secrets file
file = os.path.join("secrets", ".psql.pass")
with open(file, "r", encoding="UTF-8") as file:
    password = file.read().rstrip()

conn_string = f"host=hadoop-04.uni.innopolis.ru port=5432 user=team5 \
dbname=team5_projectdb password={password}"

data_path = os.path.join("data", "balanced_bitcoin_heist.csv")

# Connect to the remote dbms
with psql.connect(conn_string) as conn:
    # Create a cursor for executing psql commands
    cur = conn.cursor()
    # Read the commands from the file and execute them.
    with open(os.path.join("sql", "create_table.sql"),
              encoding="utf-8") as file:
        content = file.read()
        cur.execute(content)
    conn.commit()

    # Read the commands from the file and execute them.
    with open(os.path.join("sql", "import_data.sql"),
              encoding="utf-8") as file:
        # We assume that the COPY commands in the file are ordered (1.depts, 2.emps)
        commands = file.readlines()
        with open(data_path, "r", encoding="utf-8") as data:
            cur.copy_expert(commands[0], data)

    # If the sql statements are CRUD then you need to commit the change
    conn.commit()
