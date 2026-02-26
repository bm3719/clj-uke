(ns clj-uke.core
  (:require [malli.generator :as mg]))

;; TODO: Add more notes here later
(def notes
  (map vector
       [:c :d :e :f :g :a :b :c :d]
       (reverse (range 11))))

(def staff-template
  "ASCII staff rows (top to bottom)."
  (->> (cycle    ["-----------"
                  "           "])
       (take 10)
       vec
       (#(conj %1 "     -     "))))

(def seed (atom 1807))

(defn get-next-seed []
  (swap! seed inc))

(defn rand-note []
  (mg/generate (vec (cons :enum notes)) {:seed (get-next-seed)}))

(defn replace-char [s index new-char]
  (str (subs s 0 index) new-char (subs s (inc index))))

(defn insert-note [note]
  (for [line (range (count staff-template))]
    (if (= line (second note))
      (replace-char (get staff-template line) 5 "O")
      (get staff-template line))))

(defn gen-note []
  (let [note (rand-note)
        msur (insert-note note)]
    (doseq [line msur]
      (println line))))

(defn -main [& args]
  (future
    (while true
      (println "")
      (gen-note) ; Generate note.
      (Thread/sleep 8000))))
