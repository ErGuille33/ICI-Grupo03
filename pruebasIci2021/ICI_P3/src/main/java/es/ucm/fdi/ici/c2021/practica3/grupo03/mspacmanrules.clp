;FACTS ASSERTED BY GAME INPUT
	
(deftemplate MSPACMAN 
    (slot edibleGhost (type SYMBOL)) )
    
;DEFINITION OF THE ACTION FACT
(deftemplate ACTION
	(slot id))
   
;RULES 

(defrule PacManChaseGhost
	(MSPACMAN (edibleGhost true)) 
	=>  
	(assert (ACTION (id MsPacManChaseGhost) (info "Comestible --> Perseguir") )))
	
(defrule PacManChasePill
	(MSPACMAN (edibleGhost false)) 
	=> 
	(assert (ACTION (id MsPacManChasePill) (info "No comestible --> comer pill") )))	
	
	