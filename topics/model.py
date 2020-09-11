#!/usr/bin/python
# -*- coding:utf-8 -*-
import json
from gensim import corpora, models

from data import get_data


def get_model(num_topics):
    raw, processed = get_data()
    # construct vocab
    vocab = corpora.Dictionary(processed)
    # construct corpus using vocab (doc2bow model)
    corpus = [vocab.doc2bow(text) for text in processed]
    # lda model
    lda = models.ldamodel.LdaModel(corpus=corpus, id2word=vocab, num_topics=num_topics)
    perplexity = lda.log_perplexity(corpus)
    for pos, values in enumerate(lda.inference(corpus)[0]):
        # print(''.join(processed[pos]))
        all_values = []
        for theme, value in enumerate(values):
            all_values.append(value)
            # print(f'theme {theme} value {value}')
        theme_belong = all_values.index(max(all_values))
        raw[pos]['theme'] = theme_belong
    with open('classes.json', 'w') as fout:
        json.dump(raw, fout, ensure_ascii=False)
    return lda, perplexity


def main():
    nums = [2, 3, 4, 5, 6, 7, 8, 9, 10]
    for num in nums:
        if num != 5:
            continue
        print(f'theme num = {num}')
        model, perplexity = get_model(num)
        print(f'perplexity = {perplexity}')
        fout = open('keywords.txt', 'w')
        for topic in model.print_topics(num_words=10):
            print(topic)
            fout.write(str(topic) + '\n')
        fout.close()


if __name__ == '__main__':
    main()
