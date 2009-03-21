DNA Analysis
============
This code is used to analyse DNA sequences using BioJava and Clojure. 

Setup
-----
There are several data and library requirements used by this project. The following needs to be installed to successfully run the code. 

We assume that you are running Mac OS X Leopard, that downloads go to `~/Downloads`, and that commands are run from the directory containing this file. The following directory need to be created manually:

    $ mkdir -p data/dm4p1 vendor

_NOTE_: In the interests of space, the files installed here are not (and should not) be checked into the source control repository. If you need to set up a new local copy of this project you will have to manually download and set up these files.

### [_D. melanogaster_ (4.1) EID data][eid] ###

1. Download [dm4p1.EID.tar.gz][dm4p1].

2. Unpack to the data directory:   
`$ tar xf ~/Downloads/dm4p1.EID.tar -C data/dm4p1`

[dm4p1]: http://mco321125.meduohio.edu/EID/dm4p1.EID.tar.gz
[eid]: http://hsc.utoledo.edu/bioinfo/eid/

### [Clojure][] ###

1. Download [clojure 20081217][].

2. Copy `clojure.jar` to the `vendor` directory:    
`$ cp ~/Downloads/clojure/clojure.jar vendor`

[clojure]: http://clojure.org/
[clojure 20081217]: http://clojure.googlecode.com/files/clojure_20081217.zip

### [JLine][] ###

1. Download [jline-0.9.94.zip][].

2. Copy to the `vendor` directory:    
`$ cp ~/Downloads/jline-0.9.94/jline-0.9.94.jar vendor/jline.jar`

[jline]: http://jline.sourceforge.net/
[jline-0.9.94.zip]: http://downloads.sourceforge.net/jline/jline-0.9.94.zip

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
    
    $ bin/clj test.clj

The following output should be observed:    
    
    Sequence "gattggggcaaagtttatccaaatatgtctggagatggtgctcttggtatgcttattaatcgtaaagcagatatatgcattggagctatgtactcgtggtacgaagattacacatacttagacctttctatgtatcttgtacgttctggaataacgtgtcttgtaccagcgcctttgcggttgactagttggtaccttcccttagagcctttcaaagaaactttatgggctgcaattctattatgtctatgtgcagaagccacaggattggttttagcatataaaagtgagcaggcgctgtatgtactgcctggctaccgagagggctggtggacttgtacaagctttggagtatgtaccacctttaaacttttcatatcgcaatcaggaaacagcaaggcatattcactgacagttcgtgtactactctttgcctgcttccttaatgatttaataataaccagcatatatggtggcggccttgctagtatattgacaattcctagcatggacgaggcagccgacactgtcacccgcttgcgatttcaccgattacagtgggcggccaactcagaggcatgggtctcggccatacgcgcttctgatgaggtaagtgttttaatgaagatcaatatcatcttagaagcatacgtttctttctatgaaaggcattagtgaaggatatattgtacaattttcacatctatagcgacgatgagttgctacgcttagcacaggaccagcatatgcgcattggatttactgtggagcgtctgccattcggtaacaacaaaaattaatgacgggaaccaatattatatttttcttgttctgtaggtcactttgctatcggaaactatttggggcctcaagcgattgaccagttagttataatgaaggacgatatttattttcaatatacggtggcttttgttcccagactttggcccctcctcgataaattaaatactctgatatatagctggcattcgtctggtttcgataaatactgggaatatcgagttgttgccgataacttaaatctgaagatacaacaacaagttcaagaaacaatgacaggaactaaagatattggtccagtcccgcttggaatgtcaaactttgcgggatttataattgtctggatattaggatctgctatagctacattaacttttttgttagaactatcactgacatatattttaaaacagagcaatctgaaataa"
	Pattern "agnct"
	Match "agcct"
