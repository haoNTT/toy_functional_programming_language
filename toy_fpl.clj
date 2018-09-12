;; CSCI2041 Project2
;; Haonan Tian 
;; Student ID:5229178


;; Test functions for Perjure level3
(declare evaluate) 
(def scope
	(list (assoc 
		(hash-map)
		'+ +
		'- -
		'* *
		'/ /
		'= =
		'< <
		'> >
		'true true
		'false false)))

(def quote?
	(fn [form]
		(and
			(not (empty? form))
			(= (first form) 'quote))))

(def constant?
	(fn [form]
		(or
			(number? form)
			(string? form)
			(nil? form)
			(= form true)
			(= form false))))

(def let?
	(fn [form]
		(and 
			(not (empty? form))
			(= (first form) 'let))))

(def if?
	(fn [form]
		(and 
			(not (empty? form))
			(= (first form) 'if))))

(def fn?
	(fn [form]
		(and 
			(not (empty? form))
			(= (first form) 'fn))))

(def letfn-call?
	(fn [form]
		(and 
			(not (empty? form))
			(= (first form) 'letfn))))

(def closure?
	(fn [form]
		(and 
			(vector? form)
			(= (get form 0) 'closure))))

;; Test functions for closure
(def closure-parameter
	(fn [form]
		(get form 1)))

(def closure-body
	(fn [form]
		(get form 2)))

(def closure-scope
	(fn [form]
		(deref (get form 3))))

;; Functional evaluate functions for Perjure level3
(def evaluate-symbol
	(fn 
		([form]
			(evaluate-symbol form scope))
		([form scope]
			(if
				(empty? scope)
				(throw (Exception. (str "Invalid Symbol: " form)))
				(if
					(contains? (first scope) form)
					(get (first scope) form)
					(recur form (rest scope)))))))

(def evaluate-if
	(fn [form scope]
		(if
			(evaluate (second form) scope)
			(evaluate (first (rest (rest form))) scope)
			(evaluate (second (rest (rest form))) scope))))

(def evaluate-let
	(fn [form scope]
		(loop [parameters (second form)
			map {}]
			(if
				(empty? parameters)
				(evaluate (first (rest (rest form))) (cons map scope))
				(recur (rest (rest parameters))
					(assoc map (first parameters) 
						(evaluate (second parameters) (cons map scope))))))))

(def evaluate-call 
	(fn [closure arguments scope]
		(loop [parameters (closure-parameter closure)
			arg arguments
			map {}]
			(if
				(empty? parameters)
				(evaluate (closure-body closure) (cons map (closure-scope closure)))
				(recur (rest parameters) (rest arg)
					(assoc map (first parameters) (evaluate (first arg) scope)))))))

(def evaluate-fn
	(fn [form scope]			
		(vector 'closure
			(second form)
			(first (rest (rest form)))
			(atom scope))))

(def evaluate-fn2
	(fn [form scope]
		(vector 'closure
			(second form)
			(first (rest (rest form)))
			scope)))

(def evaluate-letfn
	(fn [form scope]
		(let [a (atom scope)]
			(loop [map {}
				func (second form)]
				(if
					(empty? func)
					(do
						(reset! a (cons map scope))
						(evaluate (first (rest (rest form))) (cons map scope)))
					(recur 
						(assoc map (first func) (evaluate-fn2 (second func) a)) 
						(rest (rest func))))))))

;; Mian function 
(def evaluate
	(fn ([form]
		(evaluate form scope))
	([form scope]
		(cond 
			(constant? form) form
			(symbol? form) (evaluate-symbol form scope)
			(if? form) (evaluate-if form scope)
			(let? form) (evaluate-let form scope)
			(fn? form) (evaluate-fn form scope)
			(letfn-call? form) (evaluate-letfn form scope)
			(quote? form) (second form)
			true
			(let 
				[func (evaluate (first form) scope)]
				(if 
					(closure? func)
					(evaluate-call func (rest form) scope)
					(apply func
						(map
							(fn [argument]
								(evaluate argument scope))
								(rest form)))))))))

