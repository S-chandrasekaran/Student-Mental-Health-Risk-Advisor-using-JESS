(deftemplate student
  (slot name)
  (slot attendance)
  (slot grades)
  (slot mood)
  (slot sleep)
  (slot social))

(defrule high-risk
  (student (name ?n) (attendance low) (grades poor) (mood sad) (sleep irregular) (social withdrawn))
  =>
  (printout t ?n " is at HIGH mental health risk. Immediate counseling recommended." crlf))

(defrule moderate-risk
  (student (name ?n) (attendance medium) (grades average) (mood anxious))
  =>
  (printout t ?n " is at MODERATE mental health risk. Monitor and suggest wellness activities." crlf))

(defrule low-risk
  (student (name ?n) (attendance high) (grades good) (mood happy) (sleep regular) (social active))
  =>
  (printout t ?n " is at LOW mental health risk. No immediate concern." crlf))
