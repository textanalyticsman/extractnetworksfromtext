#  A software to extract networks based on co-occurrence from unstructured text
First of all, I am not a developer. Thus, be prepared to find bugs and bad practices that hopefully are going to be fixed over the time.

That said, this is just a desktop application I have done to extract networks from unstructured text written in Spanish. However, as I am using [FreeLing](http://nlp.lsi.upc.edu/freeling/) to implement language analysis capabilities, the software presented here can also be used to extract networks from unstructured text written on other languages supported by [FreeLing](http://nlp.lsi.upc.edu/freeling/).

The motivation behind the development of this application is the analysis of criminal networks that are described by unstructured text so I believe this SW could be useful to visualize and to understand a criminal organization. Importantly, this kind of approach based on co-occurrence has been used before. However, I realized that there is a lack of free tools to apply this analysis on languages such as Spanish so I did this software.

Today the relevance of applying Social Network Analysis to understand complex phenomena as criminal organizations is not under discussion. However, the main problem with criminal networks is they do their best to conceal data about their activities and this is why getting tools to gather data about them could be useful. Importantly, mostly criminal organizations are described by unstructured data such as conversations, chats, journalistic articles, etc.

## What kind of problems do we face to build a network based on unstructured text?

 1. The first sub problem this work faces is extracting entities from unstructured text written in Spanish. The extraction of entities is implemented through the usage of Named Entity Recognition (NER) and Named Entity Classification (NEC). NER and NEC are implemented through [FreeLing](http://nlp.lsi.upc.edu/freeling/) 
 2. The second sub problem this work faces is normalizing entities, which means recognizing different entities i.e. John, John Doe and Doe as the same. This problem is solved through the usage of a clustering algorithm called Density-based spatial clustering of applications with noise (DBSCAN) and this is implemented using the library [SMILE](https://haifengl.github.io/smile/) 
 3. The third sub problem, generating relationships between entities, is solved using co-occurrence at the sentence level or at the paragraph level to minimize errors because generating links through co-occurrence at the document level could be too broad.

## How could I run this SW?

If you want to test this software, follow these steps.

 1. First of all, these are the components needed to run this.

	 - [ ] Ubuntu 16.04.5 LTS, the OS where FreeLing is installed
	 - [ ] SWIG, used to connect Java with FreeLing as this is an engine writen in C++
	 - [ ] FreeLing 4.0, the engine used to solve problems such as NER and NEC
	 - [ ] MySQL, the database where data is persisted
	 - [ ] MySQL Workbeanch, software used to design the database
	 - [ ] MySQL connector, library used to connect this SW with MySQL
	 - [ ] Java Development Kit 1.8, our programming language
	 - [ ] Netbeans 8.2, IDE used to develop this software
	 - [ ] Statistical Machine Intelligence and Learning Engine (Smile), this library is used to implement the DBSCAN algorithm
	 - [ ] EclipeLink (JPA), the persistence layer used through Java
	 - [ ] Gephi, the tool used to visualize the network gathered using the SW developed here
	 - [ ] ORA-Lite, the tool used to analyse the network applying Social Network Analysis
 2. Install SWIG in case you are missing it.
 3. Install JDK 1.8
 4. Install FreeLing on Ubuntu using this [guide](https://talp-upc.gitbook.io/freeling-4-0-user-manual/installation#install-from-tar-gz-source-packages). I have used the section called " Install from .tar.gz source packages"
 5. Generate the FreeLing APIs for Java. For example, I have installed FreeLing on this path /home/osboxes/FreeLing-4.0 where you will find the folder APIs. To generate the Java API, follow these steps.

	 - [ ] Edit /FreeLing-4.0/APIs/java/Makefile* to adjust the right values of
	          FREELINGDIR = /usr/local
	          SWIGDIR = /usr/share/swig2.0
	          JAVADIR = /usr/lib/jvm/java-8-openjdk-amd64
	          
		Previous values are just an example according to my installation
	 - [ ] Run 'make' to build the java API.
          
 6. To call FreeLing from a Java program

	 - [ ] Make sure that the directory contanining libfreeling.so
        ($FREELINGDIR/lib) is in your LD_LIBRARY_PATH      
	 - [ ] Make sure that the directory contanining libfreeling_javaAPI.so (created
        by 'make' above) is in your LD_LIBRARY_PATH.       
	 - [ ] Make sure that the package "freeling.jar" created by make is in your 	CLASSPATH

	As this SW is a Maven project I have included the "freeling.jar" as a dependency. What is more, the LD_LIBRARY_PATH on NetBeans has been configured with these values -Djava.library.path=/usr/local/lib:/home/osboxes/FreeLing-4.0/APIs/java
 7. Install MySQL Community Server using this [link](https://dev.mysql.com/downloads/mysql/). I developed this SW with MySQL Server 5.7. Using the latest version is up to you. Once, you have MySQL installed, you can run the script located on [database](https://github.com/textanalyticsman/databasesna)  to create the database used by this software
 8. Install MySQL Workbench using this [link](https://dev.mysql.com/downloads/workbench/) if you want to redesing the database
 9.  Install NetBeans 8.2 on Ubuntu; the internet is plenty of guides to do this. Once you have NetBeans on your machine you can clone or download the source code from [extractNetworksFromText](https://github.com/textanalyticsman/extractnetworksfromtext)
 10. To install Gephi and ORA-Lite you can use these links [Gephi](https://gephi.org/users/download/) [ORA-Lite](http://www.casos.cs.cmu.edu/projects/ora/download.php ). Importantly, I have used both of them on Windows.

If you follow all these steps, you will manage to run this software in some days I will write a little guide about how to use this to get your first network based on entities that co-occur on the same sentence or paragraph.

Work in progress!

Written with [StackEdit](https://stackedit.io/).
