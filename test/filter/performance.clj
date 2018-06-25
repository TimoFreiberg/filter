(ns filter.performance
  (:require  [clojure.test :as t :refer [deftest testing is]]
             [filter.core :refer [make-filter
                                  filter-lines]]
             [taoensso.tufte :as tufte :refer [profile p]]
             [clojure.java.io :as io]))

(tufte/add-basic-println-handler! {})

#_(deftest
  filter-nothing
  (testing "performance of filtering nothing in the words file"
    (tufte/profile
     {}
     (let [const-true-filter (make-filter "(constantly true)")
           words (slurp "/usr/share/dict/words")
           result (p :filtering
                     (filter-lines const-true-filter (slurp "/usr/share/dict/words")))]
       nil))))
