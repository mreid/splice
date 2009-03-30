DNA Analysis
============
This code is used to analyse DNA sequences using BioJava and Clojure. 

Setup
-----
There are several data and library requirements used by this project. The following needs to be installed to successfully run the code. 

We assume that you are running Mac OS X Leopard, that downloads go to `~/Downloads`, and that commands are run from the directory containing this file. The following directory need to be created manually:

    $ mkdir -p data/dm4p1 vendor

_NOTE_: In the interests of space, the files installed here are not (and should not) be checked into the source control repository. If you need to set up a new local copy of this project you will have to manually download and set up these files.

### [Clojure][] ###

1. Follow [these instructions][setup] for setting up Clojure for Mac OS X Leopard.

2. Use the following command to add the extra jars to Clojure's classpath:    
`$ echo "vendor/biojava.jar:vendor/bytecode.jar" > .clojure`

[setup]: http://mark.reid.name/sap/setting-up-clojure.html

### [_D. melanogaster_ (4.1) EID data][eid] ###

1. Download [dm4p1.EID.tar.gz][dm4p1].

2. Unpack to the data directory:   
`$ tar xf ~/Downloads/dm4p1.EID.tar -C data/dm4p1`

[dm4p1]: http://mco321125.meduohio.edu/EID/dm4p1.EID.tar.gz
[eid]: http://hsc.utoledo.edu/bioinfo/eid/

### [BioJava][] ###

1. Download the jar file for [BioJava 1.6][].

2. Download the [bytecode][] jar file (required for BioJava).

2. Copy both jars to the `vendor` directory:    
`$ cp ~/Downloads/biojava.jar vendor ; cp ~/Downloads/bytecode.jar vendor`

[biojava]: http://biojava.org/
[biojava 1.6]: http://www.biojava.org/download/bj16/bin/biojava.jar
[bytecode]: http://www.biojava.org/download/bj16/support-jars/bytecode.jar

Simple BioJava Test
-------------------
There is a simple test file `test.clj`, written in Clojure, that reads in the start of the `dm4p1.dEID` file and matches a pattern against it. 

Once everything is set up correctly, the test can be run from the project directory (the one containing this file) by:   
    
    $ clj test.clj

The following output should be observed:    
    
    Sequence "gattggggcaaagtttatccaaatatgtctggagatggtgctcttggtatgcttattaatcgtaaagcagatatatgcattggagctatgtactcgtggtacgaagattacacatacttagacctttctatgtatcttgtacgttctggaataacgtgtcttgtaccagcgcctttgcggttgactagttggtaccttcccttagagcctttcaaagaaactttatgggctgcaattctattatgtctatgtgcagaagccacaggattggttttagcatataaaagtgagcaggcgctgtatgtactgcctggctaccgagagggctggtggacttgtacaagctttggagtatgtaccacctttaaacttttcatatcgcaatcaggaaacagcaaggcatattcactgacagttcgtgtactactctttgcctgcttccttaatgatttaataataaccagcatatatggtggcggccttgctagtatattgacaattcctagcatggacgaggcagccgacactgtcacccgcttgcgatttcaccgattacagtgggcggccaactcagaggcatgggtctcggccatacgcgcttctgatgaggtaagtgttttaatgaagatcaatatcatcttagaagcatacgtttctttctatgaaaggcattagtgaaggatatattgtacaattttcacatctatagcgacgatgagttgctacgcttagcacaggaccagcatatgcgcattggatttactgtggagcgtctgccattcggtaacaacaaaaattaatgacgggaaccaatattatatttttcttgttctgtaggtcactttgctatcggaaactatttggggcctcaagcgattgaccagttagttataatgaaggacgatatttattttcaatatacggtggcttttgttcccagactttggcccctcctcgataaattaaatactctgatatatagctggcattcgtctggtttcgataaatactgggaatatcgagttgttgccgataacttaaatctgaagatacaacaacaagttcaagaaacaatgacaggaactaaagatattggtccagtcccgcttggaatgtcaaactttgcgggatttataattgtctggatattaggatctgctatagctacattaacttttttgttagaactatcactgacatatattttaaaacagagcaatctgaaataa"
	Pattern "agnct"
	Match "agcct"
