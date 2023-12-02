(ns day1.day1)

(def puzzle-input (slurp "input-part-1.txt"))

(defn part-1 []
  (->> puzzle-input
    (clojure.string/split-lines)
    (map #(re-seq #"\d" %))
    (map #(str (first %) (last %)))
    (map #(Integer. %))
    (reduce +)))

(println (part-1))


(def digit-map 
  {"1" "1" "2" "2" "3" "3" "4" "4" "5" "5" "6" "6" "7" "7" "8" "8" "9" "9"
   "one" "1" "two" "2" "three" "3" "four" "4" "five" "5" "six" "6" "seven" "7" "eight" "8" "nine" "9"})

(def pattern (re-pattern (clojure.string/join "|" (keys digit-map))))
(def pattern-reverse (re-pattern (clojure.string/join "|" (map clojure.string/reverse (keys digit-map)))))

(def puzzle-input (slurp "input-part-2.txt"))

(defn s->digit [s]
  (let [a (digit-map (re-find pattern s))
        b (digit-map (clojure.string/reverse (re-find pattern-reverse (clojure.string/reverse s))))]
    (Integer/parseInt (str a b))))

(defn part-2 []
  (let [lines (clojure.string/split-lines puzzle-input)]
    (->> lines
      (map s->digit)
      (reduce + 0))))

(println (part-2))
