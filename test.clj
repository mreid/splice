;A simple test to try to read in one of the smaller data files in
;FASTA format The file to read in is in `data/dm4p1/dm4p1.pEID`. Uses
;BioJava to parse the protein sequence and print out some of the
;symbols
(ns my-test)
(import '(java.io FileReader BufferedReader))
(import '(org.biojava.bio.search MaxMismatchPattern))
(import '(org.biojava.bio.seq DNATools))
(import '(org.biojavax.bio.seq RichSequence$IOTools))
(import '(java.io FileWriter BufferedWriter))

; Helper for showing BioJava sequences
(defn seq-str [seq] (.seqString seq))

(defn reader [fname] (BufferedReader. (FileReader. fname)))

; Read in the DNA file using a Fasta parser
(defn dna-seq-iter [fname] 
  (RichSequence$IOTools/readFastaDNA (reader fname) nil))

(defn repeatedly-while
  "Constructs a lazy sequence by calling f until pred is logical false"
  [pred f] 
  (lazy-seq (when (pred) (cons (f) (repeatedly-while pred f)))))

; Creates a lazy sequence for iterating through the FASTA data
(defn lazy-dna-seqs [dna-iter]
  (repeatedly-while #(.hasNext dna-iter) #(.nextSequence dna-iter)))

(defn dna-seqs 
  "Creates a lazy sequence from the file named fname"
  [fname]
  (lazy-dna-seqs (dna-seq-iter fname)))

(defn properties [seq]
  (let [description (.getDescription seq)
        terms       (re-seq #"(.+?)=(.+?);\s*" description)]
    (apply hash-map (mapcat rest terms))))
    
(defn println-hash-map [m]
  (doseq [[key val] m] (println (str ">     " key ": " val))))  
  
(defn interval-from-str [interval-str-pair]
  (map #(Integer/parseInt %) interval-str-pair))

(defn interval-length [i] (+ 1 (- (second i) (first i))))

(defn chromosome
  "Gets the chromosome string from the given properties map"
  [properties]
  (re-find #".+(?=:)" (properties "loc")))

(defn intervals
  "Gets a sequence of intervals from the given properties map"
  [properties]
  (let [all       (re-find #"\(.*\)" (properties "loc"))
        str-pairs (map rest (re-seq #"(\d+)\.\.(\d+)" all))]
    (map interval-from-str str-pairs)))

; A functional-style approach using recursion rather than for loops
(defn interval-strs
  "Generates subsequences of the given sequence according to the 
  pairs of integers representing intervals"
  [sequence intervals]
  ; While there are still intervals, compute the first one's length,
  ; pull off that many symbols from the sequence and then recurse.
  (when (seq intervals)
    (let [ i-first          (first intervals)
           i-rest           (rest intervals)
           [s-start s-end]  (split-at (interval-length i-first) 
                                       sequence)]
      (cons (apply str s-start)
            (interval-strs s-end i-rest)))))

(defn check-seqs [seqs]
  (doseq [seq seqs]
    (println "--------")
    (def prop (properties seq))
    (println-hash-map prop)
    (let [chromosome     (chromosome prop)
          intervals      (intervals prop)
          prop-length    (Integer/parseInt (prop "length"))
          sum-length     (reduce + (map interval-length intervals))
          seq-str        (seq-str seq) 
          interval-strs  (interval-strs seq-str intervals)
          re-spliced-str (apply str interval-strs)]
       (println ">     chromosome:" chromosome)
       ;(println ">     intervals:" intervals)
       (println ">     intervals add up:" (= sum-length 
                                             prop-length))
       (println ">     reconstructed string ok:" (= seq-str 
                                                    re-spliced-str))))
  (println "-------\n# of fasta sequences" (count seqs)))
        
;(defn not_intersecting_intervals [i j]
;  (let [[i1 i2] i [j1 j2] j]
;     (or (> j1 i2) (< j2 i1))))

; De Morgan's Law says: 
; (not (or (> j1 i2) (< j2 i1))) == (and (<= j1 i2) (>= j2 i1))
(defn intersecting_intervals [i j] 
;  (not (not_intersecting_intervals i j)))
  (let [[i1 i2] i [j1 j2] j]
     (and (<= j1 i1) (>= j2 i1))))
  
(defn all-intervals [file] 
  (map (comp intervals properties) (dna-seqs file)))

(def cds-intervals (all-intervals "data/sxl.cds.fasta"))
(def exons-intervals (all-intervals "data/sxl.exon.fasta"))
(def introns-intervals (all-intervals "data/sxl.intron.fasta"))
(def gene-intervals (all-intervals "data/sxl.fasta"))

(def intron-ints (map first introns-intervals))
(def exon-ints (map first exons-intervals))

(doseq [i (first cds-intervals)]
  (prn "search" i)
  (doseq [k intron-ints]
    (if (intersecting_intervals i k)
      (println "intersects intron" k)))
  (doseq [k exon-ints]
    (if (intersecting_intervals i k)
      (println "intersects exons" k)))
)

    
;;(def seq-iter (dna-seq-iter "data/dm4p1/dm4p1.dEID"))
; ;; Set up a pattern "agnct" ("n" = "ambigous"match anything") to find
; (def pattern (MaxMismatchPattern. (DNATools/createDNA "agnct") 0))
; 
; ;; Extracts the value of the gene field from the seqence description
; (defn gene [dna-seq] 
;   (second (re-find #"gene=\"(\w+)\"" (.getDescription dna-seq))))
; 
; (def sxl-id "CG18350")
; ; Apply the function to the FASTA data
; (prn 'Pattern (seq-str (.getPattern pattern)))
