;;This is an example interests file.
;;In the next release, the file extension
;;will be .edn, so M4 will look for a
;;co-located interests.edn file.
;;Interests should define  a clojure
;;map, denoted by {...} pairs of 
;;keys to values.
;;The keys can be anything, but we
;;typically follow the clojure idiom
;;of using keywords, or symbolic constants,
;;defined by :blah .  This provides a 
;;simple way to mess with interests
;;programatically, and to derive 
;;excursions (we can select or remove
;;keys from the map of interests).

;;The values associated with each key
;;are represented by a vector, denoted
;;by [...] in clojure.
;;Specifically, these are vectors with
;;2 entries..
;;[label srcs]
;;Where label is a string that defines
;;the visual representation and filename
;;of the interest.
;;srcs is another vector of one or more
;;strings, which correspond to SRCs to
;;include from the data, associated with
;;the interest.

;;So, en toto, if we have:
;;{:some-interest ["some-interst" ["SRC1" "SRC2" SRC3"]]
;; :another-interest ["another-interest" ["SRC4" "SRC5"]]}
;;in a file named interests.edn (formerly interests.clj),
;;co-located in the directory of our MARATHON project workbook,
;;M4 will pick up on that interest as the user-specified default.
;;This will impact post-processing, namely in how output data is
;;filtered (non-intersting data is ignored, significantly speeding
;;up post-processing), and the default visualizations will 
;;use the interest information to aggregate and present the data (e.g.
;;Dwell/Fill plots).  Similarly, any intermediate outputs may be populated,
;;such as the ./fills/*.txt files.  Each file should correspond to an 
;;interest (as defined by the interest map).


;;Final note: users can make quick changes to the interests file
;;and re-compute results.  Alternately, if no file is defined,
;;M4 will ask if the user wants to specify one prior to proceeding.
;;This allows users to point M4 at arbitrary files, which can be named
;;anything.  The only constraint is that the files should be formatted
;;according to the interest.edn clojure map literal (as defined above).

;;Additionally, users can comment out regions of the file if they're
;;note needed, or for experimentation.  The #_ syntax allows specific
;;clojure forms to be commented (basically telling Cojure "don't read
;;the next item"), which is a handy mechanism for eliding pieces of
;;nested data structures.

{:BCTS    ["BCTS"
           [;"47112K000"
            ;"77202K000"
            ;"77202K100"
            "87312K000"
            ]]
 
 #_:GSAB    #_["GSAB"
               ["01225K000"]]
 
 #_:CAB     #_["CAB"
                ["01302K000"]]
 
 #_:CSSB    #_["CSSB"
                ["63426K000"]]
 
 #_:QMSUPP  #_["QM Supply Co"
                ["10473R100"]]
 
 #_:QMPOL   #_["QM Pol"
                ["10527RA00"
                 "10527RC00"
                 "10527RF00"
                 "10527RG00"]]
 
 #_:PATRIOT #_["ADA Patriot"
               ["44635K000"]]}