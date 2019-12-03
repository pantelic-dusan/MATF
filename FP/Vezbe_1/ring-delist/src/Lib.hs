module Lib where

    type Ring t = ([t], [t])

    fromList:: [t] -> Ring t
    fromList xs = (xs, [])

    toList:: Ring t -> [t]
    toList (ls, rs) = ls ++ reverse rs

    switchActive:: Ring t -> Ring t
    switchActive ([], []) = ([], [])
    switchActive ([l], []) = ([l], [])
    switchActive ([l], rs) = switchActive (l:reverse rs, [])
    switchActive (active:next:others, rs) = (next:active:others, rs)

    focusNext:: Ring t -> Ring t
    focusNext ([], []) = ([], [])
    focusNext ([l], rs) = focusNext (l:reverse rs, [])
    focusNext (l:ls, rs) = (ls, l:rs)

    focusPrev:: Ring t -> Ring t
    focusPrev ([], []) = ([], [])
    focusPrev (ls, []) = focusPrev ([], reverse ls)
    focusPrev (ls, r:rs) = (r:ls, rs)

    closeWindow:: Ring t -> Ring t
    closeWindow ([], []) = ([], [])
    closeWindow ([l], rs) = (reverse rs, [])
    closeWindow (l:ls, rs) = (ls, rs)

    openWindow:: t -> Ring t -> Ring t
    openWindow w (ls, rs) = (w:ls, rs)

