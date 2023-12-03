(def input (clojure.string/split-lines "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
  Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
  Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
  Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
  Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"))


(def input (clojure.string/split-lines (slurp "input-part-1.txt")))

(comment
  input
  ;; => ["Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
  ;;     "  Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"
  ;;     "  Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"
  ;;     "  Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"
  ;;     "  Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"]
  )

(def bag {:red 12 :green 13 :blue 14})


(defn impossible? [game]
  (let [red (:red game 0)
        green (:green game 0)
        blue (:blue game 0)
        bag-red (:red bag)
        bag-green (:green bag)
        bag-blue (:blue bag)
        red? (> red bag-red)
        green? (> green bag-green)
        blue? (> blue bag-blue)]
 (or red? green? blue?)))

(defn line->game [line]
  (->> line
   (map #(clojure.string/split % #","))
   (map (fn [x] (map #(clojure.string/trim %) x)))
   (map (fn [x] (map #(clojure.string/split % #" ") x)))
   (map (fn [x] (map reverse x)))
   (map (fn [x] (map #(hash-map (keyword (first %)) (Integer. (second %))) x)))
   (map #(apply merge %))
   (map impossible?)
   (some true?)
   ))


; part 1
(->> input
     (map #(clojure.string/split % #":"))
     (map second)
     (map #(clojure.string/split % #";"))
     (map line->game)
     (zipmap (drop 1 (range)))
     (filter (fn [[_ v]] (nil? v)))
     (map first)
     (reduce + 0)
     )

(defn line->maps [line]
  (->> line
       (map #(clojure.string/split % #","))
       (map (fn [x] (map #(clojure.string/trim %) x)))
       (map (fn [x] (map #(clojure.string/split % #" ") x)))
       (map (fn [x] (map reverse x)))
       (map (fn [x] (map #(hash-map (keyword (first %)) (Integer. (second %))) x)))
       (map #(apply merge %))
       (apply merge-with max)))

; part 2
(->> input
 (map #(clojure.string/split % #":"))
 (map second)
 (map #(clojure.string/split % #";"))
 (map line->maps)
 (map #(reduce * 1 (vals %)))
 (reduce + 0)
 )


