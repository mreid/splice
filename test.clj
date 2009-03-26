;; A simple test to try to read in one of the smaller data files in FASTA format
;; The file to read in is in `data/dm4p1/dm4p1.pEID`.
;; Uses BioJava to parse the protein sequence and print out some of the symbols
(ns my-test)
(import '(java.io FileReader BufferedReader))
(import '(org.biojava.bio.search MaxMismatchPattern))
(import '(org.biojava.bio.seq DNATools))
(import '(org.biojavax.bio.seq RichSequence$IOTools))

;; Helper for showing BioJava sequences
(defn show [sequence] (.seqString sequence))

;; Read in the DNA file using a Fasta parser
(def input (BufferedReader. (FileReader. "data/dm4p1/dm4p1.dEID")))
(def data (RichSequence$IOTools/readFastaDNA input nil))

;; Creates a lazy sequence for iterating through the FASTA data
(defn bio-iterator-seq [iterator]
	(lazy-seq
		(when (.hasNext iterator) 
			(cons (.nextSequence iterator) (bio-iterator-seq iterator)))))

;; Set up a pattern "agnct" ("n" = "ambigous"match anything") to find
(def pattern (MaxMismatchPattern. (DNATools/createDNA "agnct") 0))

;; Extracts the value of the gene field from the seqence description
(defn gene [dseq] 
	(second (re-find #"gene=\"(\w+)\"" (.getDescription dseq))))

;; For a single sequence, show the match against the pattern and its gene
(defn domatch [dseq]
	(let [matcher (.matcher pattern dseq)]
		(do
			(.find matcher)
			(println "Match" (show (.group matcher)) "on Gene" (gene dseq))
		)
	)
)

; Apply the function to the FASTA data
(prn 'Pattern (show (.getPattern pattern)))
(doseq [d (bio-iterator-seq data)] (domatch d))
