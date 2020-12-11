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
    
(defrule PINKYmiddlePath
	(PINKY (edible false)) => (assert (ACTION (id PINKYmiddlePath))))

(defrule BLINKYrunsAway
	(PINKY (edible true)) =>  (assert (ACTION (id PINKYrunsAway))))