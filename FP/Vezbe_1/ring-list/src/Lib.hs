module Lib where

    switchActive:: [t] -> [t]
    switchActive [] = []
    switchActive [w] = [w]
    switchActive (active:next:others) = next:active:others

    focusNext:: [t] -> [t]
    focusNext [] = []
    focusNext [w] = [w]
    focusNext (active:others) = others ++ [active]

    focusPrev:: [t] -> [t]
    focusPrev = reverse . focusNext . reverse

    closeWindow:: [t] -> [t]
    closeWindow = tail

    openWindow:: t -> [t] -> [t]
    openWindow = (:)

