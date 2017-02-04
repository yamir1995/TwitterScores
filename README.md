# TwitterScores

## NETS 150 Final Project

We did an empirical analysis on the spread of football articles on social networks.
A more detailed description is included in the project report.

Categories:

1. Social Networks

2. Physical Networks (Internet)

3. Information Retrievel

To run the program, the entire eclipse workspace has been included in files.zip.
It includes code used for retrieving articles from the Guardian, tweets from Twitter, storage of all the information into text files and analysis and comparison using the Vector Space model taught in class.
> Main - This class actually puts everythign together and prints out the average similarities

> ArticleWriter - This class must be run first to ensure that the right articles are stores in the text file called articles.txt. It retirieves the articles from the guardian API and then writes it to the file.

> Twitter - It provides the function to get tweets using a url parameter. It is called from Main

> MyDocument - It is a modification of the Document class provided by Professor Sheth. It is tailored to Articles and tweets.

> Country - This class reads from a file called geocode.txt and then returns a Country object which stores all the data related to countries and the geocodes of the cities located in the country.

[Analysis of results](https://github.com/yamir1995/TwitterScores/blob/master/Analysis%20Writeup.pdf)
