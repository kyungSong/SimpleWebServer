import crawler
from konlpy.tag import Komoran
from collections import defaultdict
import json
import psycopg2

from selenium import webdriver
import os

def create_dict(post_list):
    dict_list = []
    for query in post_list:
        text = ""
        post_num = 0
        for post in query:
            if(len(post) > 2):
                post_num += 1
                text += post[2]
        nouns = Komoran().nouns(text)
        count_dict = defaultdict(int)

        for noun in nouns:
            count_dict[noun] += 1
        dict_list.append(count_dict)

    for i in range(len(dict_list)):
        for item in list(dict_list[i]):
            if dict_list[i][item] < post_num/10:
                del dict_list[i][item]
    return dict_list

def cycle():
    conn = connect_to_db()
    store_data(conn)

def connect_to_db():
    try:
        conn = psycopg2.connect("dbname='' user='' host='localhost' password=''")
    except:
        print("cannot connect to database")
    return conn

def get_data():
    path = os.getcwd() + "\\phantomjs\\bin\\phantomjs"
    driver = webdriver.PhantomJS(path)

    buzz_per_query, post_list = crawler.run_scraper(["리니지m", "소녀전선"], "2", driver)
    dict_list = create_dict(post_list)

    driver.quit()

    return [buzz_per_query, dict_list]

def store_data(conn):
    data = get_data()
    buzz_per_query = data[0]
    dict_list = data[1]

    cur = conn.cursor()
    try:
        for i in range(len(buzz_per_query)):
            insert_statement = "INSERT INTO game_dic VALUES (" + "'" + buzz_per_query[i][0] + "'" + ",'" + json.dumps(dict_list[i]) +  "')"
            cur.execute(insert_statement)
    except:
        print("could not insert")

    conn.commit()
    conn.close()


def main():

    cycle()

main()
