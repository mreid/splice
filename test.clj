;; A simple test to try to read in one of the smaller data files in FASTA format
;; The file to read in is in `data/dm4p1/dm4p1.pEID`.
;; Uses BioJava to parse the protein sequence and print out some of the symbols
(ns my-test)
(import '(java.io FileReader BufferedReader))
(import '(org.biojava.bio.search MaxMismatchPattern))
(import '(org.biojava.bio.seq DNATools))
(import '(org.biojavax.bio.seq RichSequence$IOTools))

;; Helper for showing BioJava sequences
(defn show [sequence] (. sequence seqString))

;; Read in the DNA file using a Fasta parser
(def input (BufferedReader. (FileReader. "data/dm4p1/dm4p1.dEID")))
(def data (RichSequence$IOTools/readFastaDNA input nil))

;; Grab the first sequence and print it out
(def dseq (. data nextRichSequence))
(prn 'Sequence (show dseq))

;; Set up a pattern "agnct" ("n" = "ambigous"match anything") to find
(def pattern (MaxMismatchPattern. (DNATools/createDNA "agnct") 0))
(def matcher (. pattern matcher dseq))
(prn 'Pattern (show (. pattern getPattern)))

;; Get the first match and print it out
(. matcher find)
(def match (. matcher group))
(prn 'Match (show match))
