#!/usr/bin/python
# -*- coding:utf-8 -*-
import requests
import json
from time import sleep

import jieba_fast as jieba


def get_briefs():
    news_url = 'https://covid-dashboard.aminer.cn/api/dist/events.json'
    response = requests.get(news_url)
    data = json.loads(response.content)['datas']
    selected_data = []
    for single_data in data:
        if single_data['type'] == 'event':
            selected_data.append(single_data)
    return selected_data


def get_id(brief):
    return brief['_id']


def get_content_by_id(_id):
    content_url = 'https://covid-dashboard-api.aminer.cn/event/' + str(_id)
    cert = '/home/kxz/miniconda3/envs/thumt/lib/python3.7/site-packages/certifi/cacert.pem'
    headers = {
            'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
            'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36'
            }
    response = requests.get(content_url, headers=headers)
    data = json.loads(response.content)['data']
    return data

def get_processed_content(brief):
    """first get content by _id, then concatenate title and content,
    then use jieba to cut the sentences, return list of words"""
    event_dict = get_content_by_id(get_id(brief))
    cat_content = event_dict['title'] +\
                  '.' if event_dict['lang'] == 'en' else '。' +\
                  event_dict['content']
    return [word for word in jieba.cut(cat_content)]


def get_contents_from_new_api():
    url = 'https://covid-dashboard.aminer.cn/api/events/list?type=event&page=1&size=10000'
    response = requests.get(url)
    contents = json.loads(response.content)['data']
    return contents

def get_processed_content_from_content(event_dict):
    cat_content = event_dict['title'] +\
                  ('.' if event_dict['lang'] == 'en' else '。') +\
                  event_dict['content']
    stop_words = ['。', ' ', '，', '.', ',', '的', '-', '了', '新冠', '病毒', '、',
                  '研究', '和', '在', '发现', '中', '患者', '冠状病毒', '与', '肺炎',
                  '团队', '人员', '（', '）', '是', '该', '对', '为']
    return [word for word in jieba.cut(cat_content) if word not in stop_words]


def get_data():
    raw_contents = get_contents_from_new_api()
    processed_contents = []
    for content in raw_contents:
        processed_contents.append(
                get_processed_content_from_content(content))
    return raw_contents, processed_contents


def main():
    _, data = get_data()
    print(data[0])


if __name__ == '__main__':
    main()
