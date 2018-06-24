(ns filter.core
  (:require [clojure.java.io :as io])
  (:gen-class))

(defn filter-lines
  [pred lines]
  (filter pred lines))

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
    (run! println (filter filter? (line-seq (io/reader *in*))))))

(defn -main
  [& args]
  (run-filter args))
