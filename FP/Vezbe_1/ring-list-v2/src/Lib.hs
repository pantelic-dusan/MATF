module Lib where

    type Ring t = [t]

    fromList:: [t] -> Ring t
    fromList = id

    toList:: Ring t -> [t]
    toList = id

    switchActive:: Ring t -> Ring t
    switchActive [] = []
    switchActive [w] = [w]
    switchActive (active:next:others) = next:active:others

    focusNext:: Ring t -> Ring t
    focusNext [] = []
    focusNext [w] = [w]
    focusNext (active:others) = others ++ [active]

    focusPrev:: Ring t -> Ring t
    focusPrev = reverse . focusNext . reverse

    closeWindow:: Ring t -> Ring t
    closeWindow = tail

    openWindow:: t -> Ring t -> Ring t
    openWindow = (:)

