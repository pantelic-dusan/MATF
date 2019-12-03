module Lib where

    
    data Ring t = MkRing [t] [t]

    fromList :: [t] -> Ring t
    fromList xs = MkRing xs []

    toList :: Ring t -> [t]
    toList (MkRing ls rs) = ls ++ reverse rs

    switchActive :: Ring t -> Ring t
    switchActive (MkRing [] []) = MkRing [] []
    switchActive (MkRing [l] []) = MkRing [l] []
    switchActive (MkRing [l] rs) = 
        switchActive $ MkRing (l : reverse rs) []
    switchActive (MkRing (active:next:others) rs) = 
        MkRing (next:active:others) rs

    focusNext :: Ring t -> Ring t 
    focusNext (MkRing [] []) = MkRing [] []
    focusNext (MkRing [l] rs) = 
        focusNext $ MkRing (l : reverse rs) []
    focusNext (MkRing (l:ls) rs) = MkRing ls (l:rs)

    focusPrev :: Ring t -> Ring t
    focusPrev (MkRing [] []) = MkRing [] []
    focusPrev (MkRing ls []) = 
        focusPrev $ MkRing [] (reverse ls)
    focusPrev (MkRing ls (r:rs)) = MkRing (r:ls) rs

    closeWindow :: Ring t -> Ring t 
    closeWindow (MkRing [] []) = MkRing [] []
    closeWindow (MkRing [l] rs) = MkRing (reverse rs) []
    closeWindow (MkRing (l:ls) rs) = MkRing ls rs 

    openWindow :: t -> Ring t -> Ring t 
    openWindow w (MkRing ls rs) = MkRing (w:ls) rs