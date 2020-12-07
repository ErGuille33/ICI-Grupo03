(deftemplate BLINKY
	(slot edible (type SYMBOL)))
	
(deftemplate INKY
	(slot edible (type SYMBOL)))
	
(deftemplate PINKY
	(slot edible (type SYMBOL)))

(deftemplate SUE
	(slot edible (type SYMBOL)))
    
(deftemplate ACTION
	(slot id))   
    
(defrule INKYmiddlePath
	(INKY (edible false)) => (assert (ACTION (id INKYmiddlePath))))

(defrule INKYrunsAway
	(INKY (edible true)) =>  (assert (ACTION (id INKYrunsAway))))
	
	