import csv
import requests
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('--file', type=str)
parser.add_argument('--url', type=str)
parser.add_argument('--cookie', type=str)
args = parser.parse_args()

with open(args.file, encoding='utf-8') as f:
    for item in csv.reader(f):
        requests.put(url=args.url, json={
                "title": item[0],
                "authors": item[1],
                "publisher": item[2]
            }, headers= {
                "Content-Type": "application/json",
                "Cookie": args.cookie
            })
