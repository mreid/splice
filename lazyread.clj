(import '(java.io FileReader BufferedReader PrintWriter))

(def filename "test.data")

; Write out a small test file. Numbers 0 to 99, one per line.
(with-open [data (PrintWriter. filename)]
  (dotimes [i 10000] (.println data i)))

(defn repeatedly-while
  "Constructs a lazy sequence by calling f until pred is logical false"
  [pred f] 
  (lazy-seq (when (pred) (cons (f) (repeatedly-while pred f)))))

; A specific implementation for the problem at hand
(defn lazy-read [reader]
  (lazy-seq
    (when (.ready reader) 
      (cons (.readLine reader) (lazy-read reader)))))

(print "File access: ")
(time (with-open [data (BufferedReader. (FileReader. filename))]
  (println "opened and closed file" )))

(print "repeatedly-while: ")
(time (with-open [data (BufferedReader. (FileReader. filename))]
  (prn (take 10 (repeatedly-while #(.ready data) #(.readLine data))))))

(print "lazy-read: ")
(time (with-open [data (BufferedReader. (FileReader. filename))]
  (prn (take 10 (lazy-read data)))))


