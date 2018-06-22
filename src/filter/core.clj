(ns filter.core
  (:gen-class)
  (:require [clojure.java.io :as io]))

(defn filter-line
  "Prints `line` to stdout, if (filter? line)"
  [filter? line]
  (when (filter? line)
    (println line)))

(defn start-filter
  "Filters stdin line by line with the given filter expression and prints the filtered result. Stops when an end-of-file character is read."
  [filter?]
  (doseq [line (line-seq (io/reader *in*))]
    (filter-line filter? line)))

(defn make-filter
  [filter-expr-str]
  (let [raw-expr (read-string filter-expr-str)
        filter-expr `(fn [~(symbol "it")]
                       ~raw-expr)]
    (fn [x] (eval (list filter-expr x)))))

(defn run-filter
  "Runs the filter program by transforming the args into a filter expression and applying the filter on stdin until an end-of-file character is read."
  [args]
  (assert (not (empty? args)))
  (let [filter? (make-filter (first args))]
    (start-filter filter?)))

(defn -main
  [& args]
  (run-filter args))
