#  A software to extract networks based on co-occurrence from unstructured text

This is just a desktop application I have done to extract networks from unstructured text written in Spanish. However, as I am using [FreeLing](http://nlp.lsi.upc.edu/freeling/) to implement language analysis capabilities, this tool could also be used to extract networks from unstructured text written on other languages supported by [FreeLing](http://nlp.lsi.upc.edu/freeling/).

The motivation behind the development of this application was the analysis of criminal networks that are described by unstructured text so I believe this SW could be useful to visualize and to understand a criminal organization. Importantly, this kind of approach based on co-occurrence has been used before. However, I realized that there is a lack of free tools to apply this analysis on languages such as Spanish so I did this software.

Today the relevance of applying Social Network Analysis to understand complex phenomena as criminal organizations is not under discussion. However, the main problem with criminal networks is they do their best to conceal data about their activities and this is why getting tools to gather data about them could be useful. Importantly, mostly criminal organizations are described by unstructured data such as conversations, chats, journalistic articles, etc.

## What kind of problems do we face to build a network based on unstructured text?

 1. The first sub problem this work faces is extracting entities from unstructured text written in Spanish. The extraction of entities is implemented through the usage of Named Entity Recognition (NER) and Named Entity Classification (NEC). NER and NEC are implemented through [FreeLing](http://nlp.lsi.upc.edu/freeling/) 
 2. The second sub problem this work faces is normalizing entities, which means recognizing different entities i.e. John, John Doe and Doe as the same. This problem is solved through the usage of a clustering algorithm called Density-based spatial clustering of applications with noise (DBSCAN) and this is implemented using the library [SMILE](https://haifengl.github.io/smile/) 
 3. The third sub problem, generating relationships between entities, is solved using co-occurrence at the sentence level or at the paragraph level to minimize errors because generating links through co-occurrence at the document level could be too broad.

## How could I run this SW?

If you want to test this software, follow these steps.

 1. First of all, these are the components needed to run this.

	 - [ ] Ubuntu 16.04.5 LTS, the OS where FreeLing is installed
	 - [ ] FreeLing 4.0, the engine used to solve problems such as NER and NEC
	 - [ ] MySQL, the database where data is persisted
	 - [ ] MySQL Workbeanch, software used to design the database
	 - [ ] MySQL connector, library used to connect this SW with MySQL
	 - [ ] Java Development Kit 1.8, our beloved programming language
	 - [ ] Netbeans 8.2, IDE used to develop this software
	 - [ ] Statistical Machine Intelligence and Learning Engine (Smile), this library is used to implement the DBSCAN algorithm
	 - [ ] EclipeLink (JPA), the persistence layer used through Java
	 - [ ] Gephi, the tool used to visualize the network gathered using the SW developed here
	 - [ ] ORA-Lite, the tool used to analyse the network applying Social Network Analysis
 2. Install FreeLing using this guide 

  

UNDER CONSTRUCTION :)

Written with [StackEdit](https://stackedit.io/).
