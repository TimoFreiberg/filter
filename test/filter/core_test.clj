(ns filter.core-test
  (:require [clojure.test :as t :refer [deftest testing is]]
            [filter.core :refer [make-filter
                                 filter-lines]]))

(deftest make-filter-test
  (testing "make-filter"
    (let [f (make-filter "(constantly true)")]
      (is (f "whatever")))
    (let [f (make-filter "(= it \"asd\")")]
      (is (f "asd"))
      (is (not (f "x")))))
  (testing "`eval` is only evaluated once"
    (let [eval-counter (atom 0)
          tmp-eval eval
          iters 10]
      (with-redefs [eval #(do (swap! eval-counter inc)
                              (tmp-eval %))]
        (let [f (make-filter "(= it \"s\")")]
          (doseq [_ (range 10)] (f "s"))))
      (is (= @eval-counter 1)
          (str "eval was called "
               @eval-counter
               " times out of "
               iters
               " iterations")))))
