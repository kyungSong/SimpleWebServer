import psycopg2
import json
from collections import Counter
import sys

def connect_to_db():
    try:
        conn = psycopg2.connect("dbname='' user='' host='localhost' password=''")
    except:
        print("cannot connect to database")
    return conn

def get_data(gameName, startDate, endDate):
    conn = connect_to_db()
    cur = conn.cursor()
    try:
        select_statement = "SELECT dict FROM game_dic WHERE query = " + "'" + gameName + "' AND date BETWEEN " + "'" + startDate + "' AND '" + endDate + "'"
        cur.execute(select_statement)
    except:
        print("could not get data")

    rows = cur.fetchall()
    combined = Counter()
    for i in range(len(rows)):
        combined += Counter(json.loads(rows[i][0]))

    conn.close()
    return json.dumps(combined)

def main():
    # 리니지m
    # 2017-08-10
    # 2017-08-21

    combined = get_data(sys.argv[1], sys.argv[2], sys.argv[3])
    print(combined)

main()
