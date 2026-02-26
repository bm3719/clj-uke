(ns clj-uke.core
  (:require [malli.generator :as mg]))

(def treble-clef
  ["     /\\  "
   "+----|-|-"
   "|    |/  "
   "|----|---"
   "|   /|   "
   "|--/-|---"
   "| |  , - "
   "|-|-(_)-)"
   "|  \\ | / "
   "+----|---"
   "     |   "
   "   (_|   "])

;; TODO: Add more notes here later
(def notes
  (map vector
       [:c :d :e :f :g :a :b :c :d]
       (reverse (range 11))))

;; ASCII staff rows (top to bottom), 11 rows total.
(def staff-template
  (->> (cycle    ["-----------"
                  "           "])
       (take 10)
       vec
       (#(conj %1 "     -     "))))

(def seed (atom 1808))
(defn get-next-seed [] (swap! seed inc))

(defn rand-note
  "Generate random note from seed." []
  (mg/generate (vec (cons :enum notes)) {:seed (get-next-seed)}))

(defn replace-char
  "Util fn to insert char at index." [s index new-char]
  (str (subs s 0 index) new-char (subs s (inc index))))

(defn insert-note
  "Insert given note into staff." [note]
  (for [line (range (count staff-template))]
    (if (= line (second note))
      (replace-char (get staff-template line) 5 "O")
      (get staff-template line))))

(defn gen-note
  "Generate staff with populated note." []
  (let [note (rand-note)
        msur (insert-note note)]
    (println (first treble-clef))
    (doseq [linum (range (count msur))]
      (println (str (nth (rest treble-clef) linum) (nth msur linum))))))

(defn -main [& args]
  (future
    (while true
      (println)
      (gen-note)
      (Thread/sleep 5000))))
