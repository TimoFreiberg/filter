(ns filter.core-test
  (:require [clojure.test :as t :refer [deftest testing is]]
            [filter.core :refer [make-filter
                                 filter-lines]]))

(deftest make-filter-test
  (testing "make-filter"
    (let [f (make-filter "(constantly true)")]
      (is (f "whatever")))))
